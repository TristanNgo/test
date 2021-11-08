package backend.demo.test.test.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.demo.test.test.dto.UserDto;
import backend.demo.test.test.model.User;
import backend.demo.test.test.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService   {
	
	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	
	public List<User> findAll() {

		return (List<User>) repo.findAll();
	}
	
	public User findById(Integer id) {
		
		return repo.findById(id).get();
	}
	
	public User save(UserDto user) {
		// TODO Auto-generated method stub
		
		User  oldUser = new User();
		if(user.getId() !=null) {
			oldUser = repo.findById(user.getId()).get();
		}
			oldUser.setUserId(user.getUserId());
			oldUser.setName(user.getName());
			oldUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			oldUser.setEmail(user.getEmail());
			oldUser.setDelFLG(1);
		return repo.save(oldUser);
	}

	@Override
	public User getUserById(int id) {
		Optional<User> optional = repo.findById(id);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException(" User not found for id :: " + id);
		}
		return user;
		
	}

	@Override
	public User delete(int id) {
		User user = repo.findById(id).orElse(null);
		user.setDelFLG(0);
		return repo.save(user);
	}

	@Override
	public List<User> findByDelFLG() {
		List<User> listUsers = repo.findAllByDelFLG(1);
		
		return listUsers;
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		User  oldUser = new User();
		if(user.getId() !=null) {
			oldUser = repo.findById(user.getId()).get();
		}
			oldUser.setUserId(user.getUserId());
			oldUser.setName(user.getName());
			oldUser.setEmail(user.getEmail());
			
		return repo.save(oldUser);
	}
	
}
