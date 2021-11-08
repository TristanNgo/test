package backend.demo.test.test.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import backend.demo.test.test.model.User;
import backend.demo.test.test.repository.UserRepository;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserRepository repo;
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	@Override
	public boolean isAuthenticated() {
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if ( authentication == null || AnonymousAuthenticationToken.class.
		            isAssignableFrom(authentication.getClass())) {
		        		return false;			
		}	
        return authentication.isAuthenticated();
	}

	@Override
	public void autoLogin(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
		
	}
	@Transactional
	public User findUserByUser_Id(String username){
		return repo.findByUser_Id(username);
	}
	public boolean userExists(String username) {
		return findUserByUser_Id(username) != null;
	}

//	@Override
//	public User findUserByDelFlg(int delFlg) {
//		User userExits = new User();
//		if(userExits.getDelFLG() == delFlg) {
//			System.out.println("This User has been disable, please try another user account !");
//		}
//		return userExits;
//	}

}
