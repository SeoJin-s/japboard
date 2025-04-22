package com.example.jpaboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 이 어노테이션은 아래 세가지를 포함하는 메타 어노테이션:
// @Configuration : 설정클래스임을 나타낸다
// @EnableAutoConfiguration : 스프링 부트의 자동 설정 활설화
// @ComponentScan : 현재 페키지와 그 하위 패키지를 스캔해서
//	@Component, @Controller, @Service, @Repository 등이 붙은 클래스를 빈으로 등록
public class JpaboardApplication {

	public static void main(String[] args) {
		// Spring boot 어플리케이션을 실행하는 메인 메소드
		// 내장 톰켓서버가 실행되고, 전체 스프링 컨텍스트가 초기화됨
		SpringApplication.run(JpaboardApplication.class, args);
	}

}
