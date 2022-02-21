package br.com.leona.redeleonaSpring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HTMLEndpoints {

		@RequestMapping("/")
	    public String welcome() throws Exception {
	        return "leona.html"; //note that this says .html
	    }
		
		@RequestMapping("/login.html")
	    public String login() throws Exception {
	        return "login.html"; //note that this says .html
	    }
		
		
		@RequestMapping("/leona.html")
	    public String inicio() throws Exception {
	        return "leona.html"; //note that this says .html
	    }
		
		@RequestMapping("/painel.html")
	    public String painel() throws Exception {
	        return "painel.html"; //note that this says .html
	    }
		
		
		
}
