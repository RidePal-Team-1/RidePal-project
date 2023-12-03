package com.example.ridepal.controllers.mvc;

import com.example.ridepal.exceptions.DuplicateEntityException;
import com.example.ridepal.mappers.UserMapper;
import com.example.ridepal.models.User;
import com.example.ridepal.models.dtos.UserDto;
import com.example.ridepal.services.contracts.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserMapper mapper;

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("user") UserDto dto, BindingResult bindingResult) {

       if (bindingResult.hasErrors()) {
           return "register";
       }


       if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
           bindingResult.rejectValue("passwordConfirm", "password_error", "Passwords must match!");
           return "register";
       }

       try {
           User user = mapper.fromDto(dto);
           userService.create(user);
           return "redirect:/auth/login";
       } catch (DuplicateEntityException e) {
           if (e.getMessage().contains("email")) {
               bindingResult.rejectValue("email", "email_error",e.getMessage());
           } else {
                bindingResult.rejectValue("username", "username_error", e.getMessage());
           }
           return "register";
       }
    }
}
