package backend.demo.test.test.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backend.demo.test.test.dto.UserDto;
import backend.demo.test.test.model.User;
import backend.demo.test.test.service.UserServiceImpl;

@RestController

public class UserController {

	@Autowired
	private UserServiceImpl service;
	

	@GetMapping("/users")
	public List<User> findAll() {
		return service.findAll();
	}

	@GetMapping("/users/{id}")
	public User findById(@PathVariable Integer id) {
		return service.findById(id);
	}

	@PostMapping("/user")
	public User add(@Valid @RequestBody UserDto userdto, BindingResult err) throws Exception {
		
		  
		return service.save(userdto);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> update(@RequestBody User user, @PathVariable Integer id) {
		
//		user.setId(id);
//		User user2 = service.save(user);
		try {
			
			user.setId(id);
			service.update(user);	
     		return new ResponseEntity<User>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
//		return new ResponseEntity<User>(HttpStatus.OK);

	}

	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<User> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

}
