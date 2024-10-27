package com.ecommerce.sopi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.DTO.request.AuthenticateRequest;
import com.ecommerce.sopi.DTO.response.MessageResponse;
import com.ecommerce.sopi.service.impl.AuthenticateService;
import com.ecommerce.sopi.service.impl.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthenticateController {
	
	@Autowired
	private AuthenticateService authenticateService;
	
	@GetMapping("/authenticate/forget/password")
	public String forgetPassword(HttpSession session,Model model) {
		ArrayList<AuthenticateRequest> list=new ArrayList<>();
		String title="Quên mật khẩu";
		AuthenticateRequest authenticateRequest1=new AuthenticateRequest("username","Tên đăng nhập","Tên đăng nhập của bạn:");
		list.add(authenticateRequest1);
		model.addAttribute("title", title);
		model.addAttribute("listInput", list);
		model.addAttribute("next","/authenticate/forget/password");
		return "client/authenticate";
	}
	  
	@PostMapping("/authenticate/forget/password")
	public String forgetPasswordPOST(HttpSession session,RedirectAttributes redirectAttributes,@RequestParam(name="username") String username) {
		  MessageResponse messageResponse=authenticateService.forgetPassword(session, username, "/authenticate/forget/password");
		  redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
		  if(messageResponse.status.compareTo("error")==0) {
			  return "redirect:/authenticate/forget/password";
		  }
		  return "redirect:/authenticate/confirmation";
	}
	
	@GetMapping("/authenticate/forget/username")
	public String forgetUsername(HttpSession session,RedirectAttributes redirectAttributes,Model model) {
		ArrayList<AuthenticateRequest> list=new ArrayList<>();
		String title="Quên tài khoản";
		AuthenticateRequest authenticateRequest1=new AuthenticateRequest("email","Email","Vui lòng nhập Email đã đăng kí:");
		list.add(authenticateRequest1);
		model.addAttribute("title", title);
		model.addAttribute("listInput", list);
		model.addAttribute("next","/authenticate/forget/username");
		 return "client/authenticate";
	}
	
	@PostMapping("/authenticate/forget/username")
	public String forgetUsernamePOST(HttpSession session,RedirectAttributes redirectAttributes,Model model,@RequestParam("email") String email) {
		  MessageResponse messageResponse=authenticateService.forgetUsername(session, email, "/authenticate/forget/username");
		  redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
		  if(messageResponse.status.compareTo("error")==0) {
			  return "redirect:/authenticate/forget/username";
		  }
		  return "redirect:/authenticate/confirmation";
	}
	
	@RequestMapping("/authenticate/send/change/password")
	public String sendEmailChangePassword(HttpSession session,RedirectAttributes redirectAttributes,Model model) {
		  MessageResponse messageResponse=authenticateService.sendChangePassword(session, "/authenticate/send/change/password");
		  if(messageResponse.getStatus().compareTo("info")==0) {
			  redirectAttributes.addFlashAttribute("info",messageResponse.getMessage());
			  return "redirect:/";
		  }
		  redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
		  return "redirect:/authenticate/confirmation";
	}
	
	@RequestMapping("/authenticate/send/change/email")
	public String sendEmailChangeEmail(HttpSession session,RedirectAttributes redirectAttributes,Model model) {
		  MessageResponse messageResponse=authenticateService.sendChangeEmail(session, "/authenticate/send/change/email");
		  if(messageResponse.getStatus().compareTo("info")==0) {
			  redirectAttributes.addFlashAttribute("info",messageResponse.getMessage());
			  return "redirect:/";
		  }
		  redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
		  return "redirect:/authenticate/confirmation";
	}
	
	@RequestMapping("/authenticate/send/change/phone")
	public String sendEmailChangePhone(HttpSession session,RedirectAttributes redirectAttributes,Model model) {
		  MessageResponse messageResponse=authenticateService.sendChangePhone(session, "/authenticate/send/change/phone");
		  if(messageResponse.getStatus().compareTo("info")==0) {
			  redirectAttributes.addFlashAttribute("info",messageResponse.getMessage());
			  return "redirect:/";
		  }
		  redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
		  return "redirect:/authenticate/confirmation";
	}
	
	@GetMapping("/authenticate/confirmation")
	public String confirmOTP(HttpSession session,RedirectAttributes redirectAttributes,Model model) {
		ArrayList<AuthenticateRequest> list=new ArrayList<>();
		String title="Xác minh";
		AuthenticateRequest authenticateRequest1=new AuthenticateRequest("otp","Mã xác minh","Mã xác minh:");
		list.add(authenticateRequest1);
		model.addAttribute("title", title);
		model.addAttribute("listInput", list);
		model.addAttribute("next","/authenticate/confirmation");
		return "client/authenticate";
	}
	
	@PostMapping("/authenticate/confirmation")
	public String confirmOTPPOST(HttpSession session,RedirectAttributes redirectAttributes,Model model,@RequestParam("otp") String otp) {
		 MessageResponse messageResponse=authenticateService.comfirmOTP(session, otp);
		 redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
		  if(messageResponse.status.compareTo("error")==0) {
			  return "redirect:/authenticate/confirmation";
		  }
		  String next=(String) session.getAttribute("next");
		  return "redirect:"+next;
	}
	
	@GetMapping("/authenticate/change/password/{token}")
	public String changePassword(HttpSession session,RedirectAttributes redirectAttributes,Model model,@PathVariable("token") String token) {
			String TOKEN=(String) session.getAttribute("token");
			if(TOKEN==null || token.compareTo(TOKEN)!=0) {
				MessageResponse messageResponse=new MessageResponse("error", "Không được phép.");
				redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
				return "redirect:/";
			}
			ArrayList<AuthenticateRequest> list=new ArrayList<>();
			String title="Đổi mật khẩu";
			AuthenticateRequest authenticateRequest1=new AuthenticateRequest("password","Mật khẩu","Mật khẩu mới:");
			AuthenticateRequest authenticateRequest2=new AuthenticateRequest("confirmpassword","Xác nhận mật khẩu","Xác nhận mật khẩu mới:");
			list.add(authenticateRequest1);
			list.add(authenticateRequest2);
			model.addAttribute("title", title);
			model.addAttribute("listInput", list);
			model.addAttribute("next",(String) session.getAttribute("next"));
		  return "client/authenticate";
	}
	
	@PostMapping("/authenticate/change/password/{token}")
	public String changePasswordPOST(HttpSession session,RedirectAttributes redirectAttributes,Model model,@RequestParam("password") String password,@PathVariable("token") String token) {
		MessageResponse messageResponse=authenticateService.changePassword(session,password,token);
		redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
		String next=(String) session.getAttribute("next");
		  if(messageResponse.status.compareTo("error")==0) {
			  return "redirect:"+next;
		  }
		redirectAttributes.addFlashAttribute("message","Thành công");
		return "redirect:/";
	}
	
	@GetMapping("/authenticate/change/email/{token}")
	public String changeEmail(HttpSession session,RedirectAttributes redirectAttributes,Model model,@PathVariable("token") String token) {
			String TOKEN=(String) session.getAttribute("token");
			if(token.compareTo(TOKEN)!=0) {
				MessageResponse messageResponse=new MessageResponse("error", "Không được phép.");
				redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
				return "redirect:/";
			}
			ArrayList<AuthenticateRequest> list=new ArrayList<>();
			String title="Thay đổi Email";
			AuthenticateRequest authenticateRequest1=new AuthenticateRequest("email","Email","Email mới:");
			list.add(authenticateRequest1);
			model.addAttribute("title", title);
			model.addAttribute("listInput", list);
			model.addAttribute("next",(String) session.getAttribute("next"));
		  return "client/authenticate";
	}
	
	@PostMapping("/authenticate/change/email/{token}")
	public String changeEmailPOST(HttpSession session,RedirectAttributes redirectAttributes,Model model,@PathVariable("token") String token,@RequestParam("email") String email) {
		MessageResponse messageResponse=authenticateService.changeEmail(session,email,token);
		redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
		String next=(String) session.getAttribute("next");
		  if(messageResponse.status.compareTo("error")==0) {
			  return "redirect:"+next;
		  }  
		redirectAttributes.addFlashAttribute("message","Thành công");
		return "redirect:/my-info";
	}
	
	@GetMapping("/authenticate/change/phone/{token}")
	public String changePhone(HttpSession session,RedirectAttributes redirectAttributes,Model model,@PathVariable("token") String token) {
			String TOKEN=(String) session.getAttribute("token");
			if(token.compareTo(TOKEN)!=0) {
				MessageResponse messageResponse=new MessageResponse("error", "Không được phép.");
				redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
				return "redirect:/login";
			}
			ArrayList<AuthenticateRequest> list=new ArrayList<>();
			String title="Thay đổi số điện thoại";
			AuthenticateRequest authenticateRequest1=new AuthenticateRequest("phone","Số điện thoại","Số điện thoại mới:");
			list.add(authenticateRequest1);
			model.addAttribute("title", title);
			model.addAttribute("listInput", list);
			model.addAttribute("next",(String) session.getAttribute("next"));
			
		  return "client/authenticate";
	}
	
	@PostMapping("/authenticate/change/phone/{token}")
	public String changePhonePOST(HttpSession session,RedirectAttributes redirectAttributes,Model model,@PathVariable("token") String token,@RequestParam("phone") String phone) {
		MessageResponse messageResponse=authenticateService.changePassword(session,phone,token);
		redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
		String next=(String) session.getAttribute("next");
		  if(messageResponse.status.compareTo("error")==0) {
			  return "redirect:"+next;
		  }
		  redirectAttributes.addFlashAttribute("message","Thành công");
		  return "redirect:/my-info";
	}
	
	
	
	
	
	
	
	
	
}
