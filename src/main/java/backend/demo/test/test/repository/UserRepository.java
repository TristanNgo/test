package backend.demo.test.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.demo.test.test.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(value = "select * from user where user_id = ?",nativeQuery = true)
	User findByUser_Id(String user_name);

	

	List<User> findAllByDelFLG(int DelFLG);
}
