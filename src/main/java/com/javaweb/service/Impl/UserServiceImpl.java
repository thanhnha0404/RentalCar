package com.javaweb.service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.beans.CustomerDTO;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.UserService;
import com.javaweb.util.EmailService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;


//	@Override
//	public void registerUser(String email, String password) {
//		// TODO Auto-generated method stub
//		 if (userRepository.findByEmail(email).isPresent()) {
//	            throw new IllegalStateException("Email đã tồn tại");
//	        }
//
//	        UserEntity user = new UserEntity();
//	        user.setEmail(email);
//	        user.setPassword(password);
//	        int otp = generateOtp();
//	        user.setOtp(otp);
//	        user.setOtpExpiration(LocalDateTime.now().plusMinutes(5));
//	        userRepository.save(user);
//
//	        sendOtpEmail(email, otp);
//		
//	}
	
	private int generateOtp() {
        return new Random().nextInt(900000) + 100000; // OTP 6 chữ số
    }

    private void sendOtpEmail(String email, int otp) {
        // Tích hợp JavaMailSender hoặc gửi email qua API bên thứ ba

    	EmailService.sendOTP(email, String.valueOf(otp));
        System.out.println("OTP đã gửi tới email " + email + ": " + otp);
    }
    

//    
//    public Optional<CustomerEntity> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

//	@Override
//	public boolean updateStatusByEmail(String email) {
//		 // Tìm user theo email
//        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isPresent()) {
//            UserEntity user = userOptional.get();
//            // Cập nhật trạng thái
//            user.setStatus(1);
//            // Lưu lại trong database
//            userRepository.save(user);
//            return true; 
//        }
//        return false; 
//	}

	@Override
	public CustomerDTO Login(String email, String password) {
		// TODO Auto-generated method stub
		Optional<CustomerEntity> userOt = userRepository.findByEmailAndPassword(email, password);
		return userOt.map(user -> {
	        CustomerDTO userDTO = new CustomerDTO();
	        userDTO.setId(user.getId());
	        userDTO.setEmail(user.getEmail());
	        userDTO.setStatus(user.getStatus());
	        return userDTO;
	    }).orElse(null); // Trả về null nếu không tìm thấy User
		
	}

	@Override
	public CustomerDTO Forgot(String email) {
		Optional<CustomerEntity> userOt = userRepository.findByEmail(email);
		return userOt.map(user -> {
	        CustomerDTO userDTO = new CustomerDTO();
	        userDTO.setId(user.getId());
	        userDTO.setEmail(user.getEmail());
	        userDTO.setStatus(user.getStatus());
	        return userDTO;
	    }).orElse(null); // Trả về null nếu không tìm thấy User
	}

	@Override
	public boolean ResetPass(String pass, String email) {
		Optional<CustomerEntity> userOt = userRepository.findByEmail(email);
		if (userOt.isPresent()) {
			CustomerEntity  cusEntity =  userOt.get();
			cusEntity.setPassword(pass);
			userRepository.save(cusEntity);
			return true;
		}
		else {
			return false;
		}
	}

//	@Override
//	public void registerUser(String email, String password) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Optional<UserEntity> findByEmail(String email) {
//		// TODO Auto-generated method stub
//		return Optional.empty();
//	}

}
