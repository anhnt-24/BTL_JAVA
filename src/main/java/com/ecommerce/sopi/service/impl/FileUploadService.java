package com.ecommerce.sopi.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.sopi.service.FileUploadServiceInterface;


@Service
public class FileUploadService implements FileUploadServiceInterface{
    private static final String UPLOAD_DIR = "./src/main/resources/uploads/";
    
    @Override
    public String handleFileUpload(String oldFile,MultipartFile file) {
    	
    	String originalFileName = file.getOriginalFilename();
    	
    	if (!isImageFile(originalFileName)) {
    		throw new RuntimeException();
    	}
    	
    	Path directory =Paths.get(UPLOAD_DIR);
        if (Files.exists(directory)) {
            try {
				Files.createDirectories(directory);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String customFileName = generateCustomFileName(originalFileName) + extension;
         
        try {
			InputStream inputStream= file.getInputStream();
			Path filePath= directory.resolve(customFileName);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        deleteFileByName(oldFile);
        return customFileName;
    }
    @Override
    public boolean isImageFile(String fileName) {
        String[] allowedExtensions = { ".jpg", ".jpeg", ".png", ".gif",".webp" };
        for (String ext : allowedExtensions) {
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String generateCustomFileName(String originalFileName) {
        String fileBaseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return fileBaseName + "_" + timestamp;
    }
    
    @Override
    public boolean deleteFileByName(String fileName) {
    
        String fullPath = UPLOAD_DIR+fileName;
        File file = new File(fullPath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
   
    
}
