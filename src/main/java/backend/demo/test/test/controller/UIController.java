package backend.demo.test.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import backend.demo.test.test.dto.UserDto;
import backend.demo.test.test.model.User;

import backend.demo.test.test.service.SecurityServiceImpl;
import backend.demo.test.test.service.UserService;

@CrossOrigin(origins = "http://localhost:8080", exposedHeaders = "token")
@Controller
public class UIController implements WebMvcConfigurer {

//	@Autowired
//	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private SecurityServiceImpl securityService;

	@Autowired
	private UserService userService;

	@GetMapping("/home")
	public String homePage(Model model) {
		return "home/homepage";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {

	
		if (securityService.isAuthenticated())
			return "redirect:/home";

//		if (exist == null) {
//			model.addAttribute("exist", "Invalid");
//			System.out.println(exist);
//		}

		if (error == null) {

			model.addAttribute("error", "Your username and password is invalid.");
			System.out.println(error);
		}

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");
		System.out.println(logout);

		return "auth/login";
	}

	@GetMapping("/registration")
	public String registration(Model model) {

		if (securityService.isAuthenticated()) {
			return "redirect:/home";
		}

		model.addAttribute("userForm", new User());

		return "auth/registration";
	}

	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("userForm") UserDto userForm, BindingResult bindingResult) {
//		userValidator.validate(userDto, bindingResult);

		if (bindingResult.hasErrors()) {
			return "auth/registration";
		}

		userService.save(userForm);

//		securityService.autoLogin(userDto.getUserId(), userDto.getPassword());

		return "redirect:/login";
	}

	@GetMapping("/user-dashboard")
	public String getUserList(Model model) {
		List<User> listUsers = userService.findByDelFLG();
		model.addAttribute("listUsers", listUsers);
		return "user/user_dashboard";
	}

	@GetMapping("/user-dashboard/add")
	public String getUserAdd(Model model) {
		model.addAttribute("user", new User());
		return "user/user_add";
	}

	@PostMapping("/user-dashboard/add")
	public String saveUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model) {
		if(securityService.userExists(user.getUserId())) {
			bindingResult.addError(new FieldError("user","userId","UserId already available"));
		}
		if (bindingResult.hasErrors()) {
			
			return "user/user_add";
		}
		userService.save(user);
		return "redirect:/user-dashboard";

	}

	@PostMapping("/user-dashboard/edit")
	public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if(securityService.userExists(user.getUserId())) {
			bindingResult.addError(new FieldError("user","userId","UserId already available"));
			return "user/user_update";
		}
		
			

		userService.update(user);
		return "redirect:/user-dashboard";

	}

	@GetMapping("/user-dashboard/edit/{id}")
	public String showEditProductPage(@PathVariable(value = "id") int id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);

		return "user/user_update";
	}

	@GetMapping("/user-dashboard/delete/{id}")
	public String deleteUser(@PathVariable(value = "id") int id) {
		userService.delete(id);
		return "redirect:/user-dashboard";
	}

}