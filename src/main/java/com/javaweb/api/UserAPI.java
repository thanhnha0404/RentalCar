package com.javaweb.api;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.beans.ResultDTO;
import com.javaweb.beans.CustomerDTO;
import com.javaweb.customeExceptions.FiledRequiredException;
import com.javaweb.entity.UserEntity;
import com.javaweb.service.UserService;
import com.javaweb.util.EmailService;
import com.javaweb.util.OTPGenerate;


@RestController


@RequestMapping("/api/auth")
public class UserAPI {
    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        String password = request.get("password");
//        userService.registerUser(email, password);
//        return ResponseEntity.ok("Đăng ký thành công. Vui lòng kiểm tra email để lấy mã OTP.");
//    }

//    @PostMapping("/verify-otp")
//    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        int otp = Integer.parseInt(request.get("otp"));
//
//        Optional<UserEntity> userOptional = userService.findByEmail(email);
//        if (!userOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại.");
//        }
//
//        UserEntity user = userOptional.get();
//        if (user.getOtp() != otp || user.getOtpExpiration().isBefore(LocalDateTime.now())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không hợp lệ hoặc đã hết hạn.");
//        }
//        userService.updateStatusByEmail(email);
//        return ResponseEntity.ok("Xác thực OTP thành công.");
//    }
    
    @PostMapping(value = "/login")
    public Object login(@RequestBody Map<String, String> loginRequest) {
    	
    	validateLogin(loginRequest);
    	
    	String email = loginRequest.get("email");
    	String password = loginRequest.get("password");
    	CustomerDTO u = userService.Login(email,password) ;
    	
    	ResultDTO<CustomerDTO> resultLogin = new ResultDTO<CustomerDTO>();   
    	if (u == null) {
    		resultLogin.setStatus(false);
    		resultLogin.setMessage("Tài khoản hoặc mật khẩu không chính xác!");
    	}
    	else if (u.getStatus() != 1) {
    			resultLogin.setStatus(false);
        		resultLogin.setMessage("Tài khoản chưa được kích hoạt!");
    	}	
    	else {
    			resultLogin.setStatus(true);
        		resultLogin.setMessage("Đăng nhập thành công!");
        		resultLogin.setData(u);
    	}
        return resultLogin; 
    }
    
    @PostMapping(value = "/forgot")
   	public Object forgot(@RequestBody Map<String, String> forgotRequest,  HttpServletResponse response) {
   	    	
       		validateFogot(forgotRequest);
   	    	String email = forgotRequest.get("email");
   	    	CustomerDTO u = userService.Forgot(email);
   	    	
   	    	ResultDTO<CustomerDTO> result = new ResultDTO<CustomerDTO>();  
   	    	
   	    	if (u == null) {
   	    		result.setStatus(false);
   	    		result.setMessage("Bạn chưa có tài khoản trong hệ thống");
   	    	}
   	    	else if (u.getStatus() != 1) {
   	    		result.setStatus(false);
   	    		result.setMessage("Tài khoản của bạn chưa được kích hoạt");
   	    	}
   	    	else {
   	    		String otp = OTPGenerate.generateOTP();
   	    		if (!EmailService.sendOTP(u.getEmail(), otp)) {
   	    			result.setStatus(false);
   	   	    		result.setMessage("Không thể gửi email vui lòng thử lại");
   	    		}
   	    		else {
   	    			result.setStatus(true);
   	   	    		result.setMessage("Đã tìm thấy tài khoản");
   	   	    		result.setData(u);
   	   	    		
   	   	    		Cookie otpCookie = new Cookie("otp",otp);
   	   	    		otpCookie.setHttpOnly(false);
   	   	    		otpCookie.setMaxAge(5*60);
   	   	    		otpCookie.setPath("/");
   	   	    		response.addCookie(otpCookie);
   	    		}
   	    		
   	    	} 	
   	        return result; 
   	    }
    
    @PostMapping(value="/otp")
    public Object OTPCheck (@RequestBody Map<String,String> verifyRequest, HttpServletRequest request, HttpServletResponse response) {
    	// Lấy OTP từ cookie
    	String enteredOtp = verifyRequest.get("otp");
    	if (enteredOtp == null || enteredOtp.equals("") ) {
    		throw new FiledRequiredException("OTP không được trống");
    	}
    	
    	String otpFromCookie = getOTPFromCookies("otp",request);
    	ResultDTO result = new ResultDTO();
    	
        if (otpFromCookie != null && otpFromCookie.equals(enteredOtp) ) {
        	result.setStatus(true);
        	result.setMessage("OTP thành công");
        	
        	  // xoa cookie
            Cookie otpCookie = new Cookie("otp", "");
            otpCookie.setMaxAge(0); // Đặt thời gian sống của cookie về 0 để xóa
            otpCookie.setPath("/"); // Đảm bảo path giống với lúc tạo cookie
            response.addCookie(otpCookie); 
        }
        else {
        	result.setStatus(false);
        	result.setMessage("OTP sai hoặc hết hạn");
        }
        
          
        return result;
    }
    
    @PostMapping(value = "/reset")
    public Object ResetPass (@RequestBody Map<String,String> resetRequest) {
    	String passMain = resetRequest.get("passMain");
    	String passCheck = resetRequest.get("passCheck");
    	String email = resetRequest.get("email");
    	
    	if (passMain == null || passMain.isEmpty() || passCheck == null || passCheck.isEmpty()) {
    		throw new FiledRequiredException("Vui lòng mật khẩu và xác nhận mật khẩu");
    	}
    	
    	if (email == null || email.isEmpty()) {
    		throw new FiledRequiredException("Không thể tìm thấy tài khoản");
    	}
    	
    	ResultDTO result = new ResultDTO();
    	if(!passMain.equals(passCheck)) {
    		result.setStatus(false);
    		result.setMessage("Mật khẩu không khớp");
    	}
    	else {
    		if (userService.ResetPass(passMain, email)) {
    			result.setStatus(true);
        		result.setMessage("Cập nhật thành công mật khẩu");
    		}
    		else {
    			result.setStatus(false);
        		result.setMessage("Lỗi không thể cập nhật mật khẩu");
    		}
    	}
    	return result;
    }
    
    
    public String getOTPFromCookies (String key, HttpServletRequest request) {
    	String optCookie = null;
    	Cookie[] cookies = request.getCookies();
    	if (cookies != null) {
    		for (Cookie cookie : cookies) {
        		if (key.equals(cookie.getName())) {
        			optCookie = cookie.getValue();
        			break;
        		}
        	}
    	}		
    	return optCookie;
    }
    public void validateLogin (Map<String, String> loginRequest) {
    	String name = loginRequest.get("email");
    	String pass = loginRequest.get("password");
    	if (name == null || name.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
    		throw new FiledRequiredException("Tài khoản và mật khẩu không được trống");
    	}
    }
    
    public void validateFogot (Map<String, String> forgotRequest) {
    	String name = forgotRequest.get("email");
    	if (name == null || name.trim().isEmpty()) {
    		throw new FiledRequiredException("Vui lòng nhập email xác thực");
    	}
    }
    
}

