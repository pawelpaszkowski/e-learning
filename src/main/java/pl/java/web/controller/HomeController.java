package pl.java.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
public class HomeController {
 
    @RequestMapping("/")
    public String index() {
        return "index";
    }
    
    @RequestMapping("/user/home")
	public String home() {
		return "user/home";
	}
     
}