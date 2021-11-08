package backend.demo.test.test.controller;

import java.util.Date;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;



import backend.demo.test.test.dto.UserDto;
import backend.demo.test.test.handleException.ValidationExceptionHandler;
import backend.demo.test.test.model.JwtRequest;
import backend.demo.test.test.model.JwtResponse;
import backend.demo.test.test.model.User;
//import backend.demo.test.test.repository.UserRepository;
import backend.demo.test.test.service.JwtUserDetailsService;
import backend.demo.test.test.service.SecurityServiceImpl;
import backend.demo.test.test.service.UserService;
import backend.demo.test.test.util.JwtTokenUtil;
import backend.demo.test.test.validate.UserValidator;
//import io.jsonwebtoken.Jwts;



@RestController
@CrossOrigin

public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityServiceImpl securityService;
	
	

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUserId(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUserId());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));

	}

	@PostMapping(value = "/register")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult)  {
		if(bindingResult.hasErrors()) {
			return ValidationExceptionHandler.handleMethodArgumentNotValid(bindingResult, HttpStatus.BAD_REQUEST);
		}
		if(securityService.userExists(userDto.getUserId())) {
		
			UserValidator validator = new UserValidator();
			validator.setTimestamp(new Date());
			validator.setMessage("User_Id already used");
			
			return new ResponseEntity<Object>(validator,HttpStatus.BAD_REQUEST);
	}
		
		User newUser = userService.save(userDto);
//		return new ResponseEntity<User>(newUser, HttpStatus.OK);
		return new ResponseEntity<Object>(newUser, HttpStatus.OK); 
	}

	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
