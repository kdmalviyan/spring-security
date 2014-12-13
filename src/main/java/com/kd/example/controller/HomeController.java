/**
 * 
 */
package com.kd.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kd
 *
 */
@Controller
public class HomeController {

	/**
	 * This method will simple redirect home.jsp page
	 * @param modelmap
	 * @return
	 */
	@RequestMapping(value={"/", "/home"})
	public String homePage(ModelMap modelmap) {
		return "home";
	}

}
