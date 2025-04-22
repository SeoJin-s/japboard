package com.example.jpaboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor // 기본 생성자 자동 생성
@Data              // Getter, Setter, toString, equals, hashCode 자동 생성
@AllArgsConstructor // 모든 필드 초기화 생성자 자동 생성
@Entity // 이 클래스가 JPA 엔티티임을 명시 (DB 테이블과 매핑됨)
@Table(name="article")
// 이 엔티티가 매핑될 테이블 이름을 지정
public class Article {
	@Id	// 기본키 지정 ( primary Key)
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// 기본키 자동생성
	private long id;
	@Column(name="title")	// DB 컬럼 이름지정 ( 명시적 매핑)
	private String title;
	@Column(name="content")	// EB 컬림 이름이정
	private String content;

}
