package com.ecommerce.sopi.globalvar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DomainService {

    private final HttpServletRequest request;

    @Autowired
    public DomainService(HttpServletRequest request) {
        this.request = request;
    }
    
    // Lấy ra tên miền hiện tại vd: http://localhost:8080
    public String getDomain() {
        String scheme = request.getScheme(); // http hoặc https
        String serverName = request.getServerName(); // Tên miền
        int serverPort = request.getServerPort(); // Cổng

        String portPart = (serverPort == 80 || serverPort == 443) ? "" : ":" + serverPort;
        return scheme + "://" + serverName + portPart; // Tạo domain đầy đủ
    }
}