package stu.ljx.o2o.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/useradmin")
public class UserAdminController {
	
	@RequestMapping(value="/localauth/login", method=RequestMethod.GET)
	public String localAuthLogin() {
		return "user/localauthlogin";
	}
	
	@RequestMapping(value="/localauth/register", method=RequestMethod.GET)
	public String localAuthRegister() {
		return "user/localauthregister";
	}
	
}
