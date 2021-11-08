package backend.demo.test.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import backend.demo.test.test.dto.UserDto;
import backend.demo.test.test.model.User;

public interface UserService {

	
	List<User> findAll();
	
	User save(UserDto user);
	User update(User user);
	User getUserById(int id);
	User delete(int id);
	List<User> findByDelFLG();
	
	
//	List<User> findAllByDelFlg();
	// t
}
