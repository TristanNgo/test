package backend.demo.test.test.handleException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import org.hibernate.mapping.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler  {


	public static ResponseEntity<Object> handleMethodArgumentNotValid(BindingResult bindingResult ,
			 HttpStatus status) {
		// TODO Auto-generated method stub
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("content", "");
		body.put("timestamp", new Date());
		body.put("status", status.value());
		
		List<FieldError> fieldError = bindingResult.getFieldErrors();
		List<String> errors = new ArrayList<String>();
		for(FieldError item : fieldError) {
			errors.add(item.getDefaultMessage());
		}
		body.put("errors", errors);
			
		return new ResponseEntity<Object>(body,  status);
	}
	
	
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpStatus status, WebRequest request) {
//		// TODO Auto-generated method stub
//		return super.handleMethodArgumentNotValid(ex,  status, request);
//	}
	
//	public static ResponseEntity<Object> getResponse(Object content, HttpStatus status){
//		Map<String, Object> body = new LinkedHashMap<>();
//		body.put("timestamp", new Date());
//		body.put("status", status.value());
//		body.put("errors", "");
//		body.put("content", content);
//		
//			
//		return new ResponseEntity<Object>(body,  status);
//		
//	}
}
