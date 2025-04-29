package Get_a_Job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import Get_a_Job.command.LoginCommand;
import Get_a_Job.domain.AuthInfoDTO;
import Get_a_Job.mapper.LoginMapper;
import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {
	@Autowired
	LoginMapper loginMapper;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	public void execute(LoginCommand loginCommand, HttpSession session, BindingResult result) {
		AuthInfoDTO auth = loginMapper.LoginSelectOne(loginCommand.getUserId());
		if(auth != null) {
			if(passwordEncoder.matches(loginCommand.getUserPw(), auth.getUserPw())) {
				System.out.println("비밀번호가 일치합니다.");
				session.setAttribute("auth", auth);
			} else {
				result.rejectValue("userPw", "loginCommand.userPw", "비밀번호가 일치하지않습니다.");
				System.out.println("비밀번호가 일치하지않습니다.");
			}
			
		} else {
			result.rejectValue("userId", "loginCommand.userId", "아이디가 존재하지 않습니다.");
			System.out.println("아이디가 존재하지 않습니다.");
		}
		
		
		
	}

}
