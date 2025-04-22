package com.example.jpaboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller	// 이 클래스가 spring MVC의 컨트롤러임을 나타내는것
public class HomeController {

	@GetMapping("/")
	public String home() {
	    return "home"; // 세션 loginName은 Mustache에서 직접 사용 가능
	}
}	