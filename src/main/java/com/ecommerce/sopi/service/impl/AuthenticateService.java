package com.ecommerce.sopi.service.impl;

import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.response.MessageResponse;
import com.ecommerce.sopi.entity.UserEntity;
import com.ecommerce.sopi.globalvar.DomainService;
import com.ecommerce.sopi.service.AuthenticateServiceInterface;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthenticateService implements AuthenticateServiceInterface {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private DomainService domainService;
	
	private static final String NUMBERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private SecureRandom random = new SecureRandom();

    public String generateOtp() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(NUMBERS.length());
            otp.append(NUMBERS.charAt(index));
        }
        return otp.toString();
        
    }
    public String generateToken() {
        return UUID.randomUUID().toString();
    }
    public String formatEmail(String email) {
		 if(email!=null) {
		        int atIndex = email.indexOf('@');
		        if (atIndex <= 1) {
		            return email; // If the email is too short, return it as is
		        }
	
		        String visibleStart = email.substring(0, 1);  // First character
		        String visibleEnd = email.substring(atIndex - 1, atIndex);  // Last character before '@'
		        String maskedMiddle = "*".repeat(atIndex - 2);  // Mask the middle part
	
		        return visibleStart + maskedMiddle + visibleEnd + email.substring(atIndex);
		 }
		 return "";
	}
    @Override
    public String setSomeAttr(HttpSession session,UserEntity userEntity,String link,String next) {
    	String otp=generateOtp();
		String token=generateToken();
		session.setAttribute("otp", otp);
		session.setAttribute("token",token);
		session.setAttribute("user",userEntity);
		session.setMaxInactiveInterval(5 * 60);
		session.setAttribute("linkSendAgain",link+token);
		session.setAttribute("next", next+token);
		return otp;
    }
    
    @Override
    public String sendEmailChange(String otp,String linkAuthenticate) {
    	String htmlContent = "<p>Xin chào,</p><br>"
                + "<p>Bạn đã yêu cầu mã xác minh cho tài khoản của mình.</p>"
                + "<p>Mã xác minh của bạn là: <strong>" + otp + "</strong></p>"
                + "<p>Vui lòng không chia sẻ mã này cho bất kì ai.</p><br>"
                + "<p>Hoặc bạn có thể nhấp vào đường dẫn sau đây để đi đến trang thay đổi:</p>"
                + "<p><a style='color: blue; text-decoration: underline;' href='" + linkAuthenticate + "'>" + linkAuthenticate + "</a><p><br>"
                + "<h2>FROM SOPI WITH LOVE <span style='color:red;'>&#10084;</span></h2>";
    	return htmlContent;
    }
   
    @Override
    public String sendEmailGetUserName(String otp,String username,String linkAuthenticate) {
    	String htmlContent= "<p>Xin chào,</p>" +
                "<br>" +
                "<p>Bạn đã yêu cầu khôi phục lại tài khoản của mình:</p>" +
                "<span>Tài khoản:</span> <strong>" + username + "</strong><br>" +
                "<p>Mã xác minh của bạn là: <strong>" + otp + "</strong></p>" +
                "<p>Hãy dùng mã xác minh này để đặt lại mật khẩu mới.</p>" +
                "<p>Vui lòng không chia sẻ mã này cho bất kì ai.</p>" +
                "<br>" +
                "<p>Hoặc bạn có thể nhấp vào đường dẫn sau đây để đi đến trang thay đổi:</p>" +
                "<p><a href='" + linkAuthenticate + "'>" + linkAuthenticate + "</a></p>" +
                "<br>" +
                "<h2>FROM SOPI WITH LOVE <span style='color: red;'>&#10084;</span></h2>";
    	return htmlContent;
    }
	
	@Override
	public MessageResponse forgetPassword(HttpSession session,String username,String link) {
		// TODO Auto-generated method stub
		UserEntity userEntity=userService.getUserByUserName(username);
		if(userEntity==null) {
			return new MessageResponse("error", "Tên tài khoản không tồn tại.");
		}
		
		String toLink="/authenticate/change/password/";
		String otp=setSomeAttr(session,userEntity,link,toLink);
		String linkAuthenticate=domainService.getDomain()+toLink+session.getAttribute("token");
		String to=userEntity.getEmail();
		String subject="Quên mật khẩu";
		String htmlContent = sendEmailChange(otp, linkAuthenticate);
		emailService.sendHtmlMessage(to, subject, htmlContent);
		
		return new MessageResponse("success", "Chúng tôi đã gửi mã xác minh đến "+formatEmail(to)+", vui lòng kiểm tra.");
	}

	
	@Override
	public MessageResponse forgetUsername(HttpSession session, String email,String link) {
		// TODO Auto-generated method stub
		UserEntity userEntity=userService.getUserByEmail(email);
		if(userEntity==null) {
			return new MessageResponse("error", "Có vẻ như Email này chưa được đăng kí.");
		}
		
		String toLink="/authenticate/change/password/";
		String otp=setSomeAttr(session,userEntity,link,toLink);
		String linkAuthenticate=domainService.getDomain()+toLink+session.getAttribute("token");
		String to=userEntity.getEmail();
		String subject="Quên tài khoản";
		String htmlContent = sendEmailGetUserName(otp,userEntity.getUsername(),linkAuthenticate);
		emailService.sendHtmlMessage(to, subject, htmlContent);
		return new MessageResponse("success", "Chúng tôi đã gửi tên tài khoản và mã xác minh đến "+formatEmail(to)+", vui lòng kiểm tra.");
	}
	@Override
	public MessageResponse comfirmOTP(HttpSession session, String OTP) {
		// TODO Auto-generated method stub
		String otp=(String) session.getAttribute("otp");
		if(otp.compareTo(OTP)==0) {
			return new MessageResponse("success", "Xác thực thành công");
		}
		return new MessageResponse("error", "Mã xác minh không đúng, vui lòng thử lại.");
	}


	@Override
	public MessageResponse sendChangePassword(HttpSession session, String link) {
		UserEntity userEntity=userService.getAthenticatedUser();
		String toLink="/authenticate/change/password/";
		String to=userEntity.getEmail();
		if(userEntity.getEmail()==null) {
			return new MessageResponse("info", "Tài khoản này chưa liên kết với bất kì Email nào.");
		}
		String otp=setSomeAttr(session,userEntity,link,toLink);
		String linkAuthenticate=domainService.getDomain()+toLink+session.getAttribute("token");
		String subject="Đổi mật khẩu";
		String htmlContent = sendEmailChange(otp, linkAuthenticate);
		emailService.sendHtmlMessage(to, subject, htmlContent);
		// TODO Auto-generated method stub
		return new MessageResponse("success", "Chúng tôi đã gửi mã xác minh đến "+formatEmail(to)+", vui lòng kiểm tra.");
	}
	
	@Override
	public MessageResponse sendChangeEmail(HttpSession session, String link) {
		// TODO Auto-generated method stub
		UserEntity userEntity=userService.getAthenticatedUser();
		if(userEntity.getEmail()==null) {
			return new MessageResponse("info", "Tài khoản này chưa liên kết với bất kì Email nào.");
		}
		String toLink="/authenticate/change/email/";
		String otp=setSomeAttr(session,userEntity,link,toLink);
		String linkAuthenticate=domainService.getDomain()+toLink+session.getAttribute("token");
		String to=userEntity.getEmail();
		String subject="Đổi Email";
		String htmlContent = sendEmailChange(otp, linkAuthenticate);
		emailService.sendHtmlMessage(to, subject, htmlContent);
		return new MessageResponse("success", "Chúng tôi đã gửi mã xác minh đến "+formatEmail(to)+", vui lòng kiểm tra.");
	}
	
	@Override
	public MessageResponse sendChangePhone(HttpSession session, String link) {
		// TODO Auto-generated method stub
		UserEntity userEntity=userService.getAthenticatedUser();
		String toLink="/authenticate/change/phone";
		if(userEntity.getEmail()==null) {
			return new MessageResponse("info", "Tài khoản này chưa liên kết với bất kì Email nào.");
		}
		String otp=setSomeAttr(session,userEntity,link,toLink);
		String linkAuthenticate=domainService.getDomain()+toLink+session.getAttribute("token");
		String to=userEntity.getEmail();
		String subject="Đổi số điện thoại";
		String htmlContent = sendEmailChange(otp, linkAuthenticate);
		emailService.sendHtmlMessage(to, subject, htmlContent);
		return new MessageResponse("success", "Chúng tôi đã gửi mã xác minh đến "+formatEmail(to)+", vui lòng kiểm tra.");
	}

	@Override
	public MessageResponse changePassword(HttpSession session, String password,String token) {
		String TOKEN=(String) session.getAttribute("token");
		if(token.compareTo(TOKEN)!=0) {
			return new MessageResponse("error", "Không được phép.");
		}
		UserEntity userEntity=(UserEntity) session.getAttribute("user");
		userService.setPassword(userEntity, password);
		return new MessageResponse("success", "Đổi mật khẩu thành công.");
	}
	
	@Override
	public MessageResponse changeEmail(HttpSession session, String email,String token) {
		// TODO Auto-generated method stub
		String TOKEN=(String) session.getAttribute("token");
		if(token.compareTo(TOKEN)!=0) {
			return new MessageResponse("error", "Không được phép.");
		}
		if(userService.getUserByEmail(email)!=null) {
			return new MessageResponse("error", "Tài khoản Email đã tồn tại.");
		}
		UserEntity userEntity=(UserEntity) session.getAttribute("user");
		userEntity.setEmail(email);
		userService.saveUser(userEntity);
		return new MessageResponse("success", "Đổi Email thành công.");
	}
	
	@Override
	public MessageResponse changePhone(HttpSession session, String phone,String token) {
		// TODO Auto-generated method stub
		String TOKEN=(String) session.getAttribute("token");
		if(token.compareTo(TOKEN)!=0) {
			return new MessageResponse("error", "Không được phép.");
		}
		if(userService.getUserByPhone(phone)!=null) {
			return new MessageResponse("error", "Số điện thoại đã tồn tại.");
		}
		UserEntity userEntity=(UserEntity) session.getAttribute("user");
		userEntity.setPhone(phone);
		userService.saveUser(userEntity);
		return new MessageResponse("error", "Đổi số điện thoại thành công.");
	}

}
