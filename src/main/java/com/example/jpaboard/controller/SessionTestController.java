/*
 * package com.example.jpaboard.controller;
 * 
 * import org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.GetMapping;
 * 
 * import jakarta.servlet.http.HttpSession; import lombok.extern.slf4j.Slf4j;
 * 
 * @Slf4j
 * 
 * @Controller public class SessionTestController {
 * 
 * // 세션 값 수동 설정 (로그인처럼 가정)
 * 
 * @GetMapping("/newSession") public String newSession(HttpSession session) {
 * session.setAttribute("loginame", "서진"); // 실제 로그인 이름처럼 저장
 * log.debug("세션 생성: loginame = 서진"); return "redirect:/"; // 홈으로 이동 }
 * 
 * // 세션 값 삭제 (로그아웃처럼)
 * 
 * @GetMapping("/dropSession") public String dropSession(HttpSession session) {
 * session.invalidate(); // 세션 전체 삭제 log.debug("세션 삭제됨"); return "redirect:/";
 * // 홈으로 이동 } }
 */