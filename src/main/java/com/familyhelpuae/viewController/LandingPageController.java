package com.familyhelpuae.viewController;

import org.springframework.web.bind.annotation.*;

@RestController
public class LandingPageController {
	
	@GetMapping({"/"})
	public String welcome() {
		return "Landing Page for unregistered visitors";
	}

}
