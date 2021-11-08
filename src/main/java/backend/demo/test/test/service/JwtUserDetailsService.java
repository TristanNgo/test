package backend.demo.test.test.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.demo.test.test.model.User;
import backend.demo.test.test.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String user_Id) throws UsernameNotFoundException {
		User user = userRepo.findByUser_Id(user_Id);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + user_Id);
		}else if(user != null && user.getDelFLG() == 1) {
			return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(),
					new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User not active with username" + user_Id);
		}
			
	}

}
