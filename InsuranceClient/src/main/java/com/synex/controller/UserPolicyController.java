package com.synex.controller;

import java.security.Principal;

//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synex.domain.User;
import com.synex.service.RoleService;
import com.synex.service.UserService;
//import com.synergisticit.validation.UserValidator;



@RestController
public class UserPolicyController {
	 @Autowired
	    private UserService userService;
	    
	    
	    @PostMapping("/findUserId")
	    public ResponseEntity<Long> findUserId(@RequestBody String username) { 
	    	System.out.println("username" + username);
	        User user = userService.findUserByUsername(username);
	        
	        return ResponseEntity.ok(user.getUserId());
	    }
	

}
