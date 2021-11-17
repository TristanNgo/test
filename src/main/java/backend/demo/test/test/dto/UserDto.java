package backend.demo.test.test.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;





public class UserDto {

	private Integer id;
	@NotEmpty	
	@Size(min = 5, max = 15, message = "User Id length shoud be in between 5 to 15")
	private String userId;
	
	@NotEmpty
	@Size(min = 8, max = 15, message = "password should be in between 4 to 10")
	private String password;
	
	@NotEmpty
	@Size(min = 2, max = 10, message = "length shoud be in between 2 to 10")
	private String name;
	
	@NotEmpty(message = "Email field should not be empty")
	@Email(regexp = "^(.+)@(.+)$", message = "Invalid email pattern")
	private String email;
	
	private int delFLG;

	public UserDto(Integer id, String userId, String password, String name, String email, int delFLG) {

		this.id = id;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.delFLG = delFLG;
	}

	public UserDto() {

	}

	public int getDelFLG() {
		return delFLG;
	}

	public void setDelFLG(int delFLG) {
		this.delFLG = delFLG;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
