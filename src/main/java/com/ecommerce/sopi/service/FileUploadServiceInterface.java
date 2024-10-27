package com.ecommerce.sopi.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadServiceInterface {
	String handleFileUpload(String oldFile,MultipartFile file); // Hàm xử lí chính thực hiện các công việc đến xử lí đường dẫn, thêm mới file và xóa file cũ
	
	boolean isImageFile(String fileName); // Kiểm tra file upload có phải định dạng Image
	
	String generateCustomFileName(String originalFileName); // Tạo tên file mới upload có sử dụng uuid giúp các file có tên khác nhau
	
	boolean deleteFileByName(String fileName) ; // Xóa file cũ bằng tên của file đó
}
