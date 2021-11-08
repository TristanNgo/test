package backend.demo.test.test.validate;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class UserValidator  {
	public Date timestamp;
	public String message;
	
	public UserValidator() {
		
	}
	public UserValidator(Date timestamp, String message ) {
		super();
		this.timestamp = timestamp;		
		this.message = message;
		
		
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
