package com.example.jpaboard.dto;

import com.example.jpaboard.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode 등을 자동 생성
@NoArgsConstructor //모든 필드를 매개변수로 받는 생성자를 자동생성
@AllArgsConstructor	// 기본 생성자를 자동 생성
public class ArticleForm {
   private long id;
   private String title;
   private String content;	

   
   // DTO -> Entity 변환 메소드
   public Article toEntity() {	// 새로운 Article 객체를 생성
	   Article entity = new Article();
	   // DTO에서 받은 데이터를 Entity 에 세팅
	   entity.setId(this.id);
	   entity.setTitle(this.title);
	   entity.setContent(this.content);
	   
	   // 완성된 ENtity 반환
	   return entity;
   }
}
