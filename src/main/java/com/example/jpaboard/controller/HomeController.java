package com.example.jpaboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller	// 이 클래스가 spring MVC의 컨트롤러임을 나타내는것
public class HomeController {
   @GetMapping("/")	// 루트경로 (localhost) 로 get 요청이 들어오면 실행
   public String home(Model model) {
      System.out.println("home controller");
      // view 템플릿에 전달할 데이터 추가 ( 변수명 : loginName, 값 : 구디)
	   model.addAttribute("loginName", "서진");
	   // System.out.println(model.getAttribute("loginName"));
	   // log 프레임워크 사용
       // 반환 값은 view 이름 
	   log.trace("loginName: "+model.getAttribute("loginName"));
	   log.debug("loginName: "+model.getAttribute("loginName"));
	   log.info("loginName: "+model.getAttribute("loginName"));
	   return "home"; // resources/templates/home.html을 의미한다
   }
}