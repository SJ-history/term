package com.sj.termproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StartController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String vueTest() {
		return "/front/index.html";
	}
}