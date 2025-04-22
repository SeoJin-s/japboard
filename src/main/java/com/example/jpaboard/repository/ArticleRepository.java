package com.example.jpaboard.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpaboard.entity.Article;
import com.example.jpaboard.entity.ArticleMapping;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	//CrudRepository : insert, select one, select all, update, delete
	
	//JpaRepository  : CrudRepository (CrudRepository 자식 인터페이스) CrudRepository가 가지고 있는건 다 가지고있다. : select limit, select order by, ....
	
	// findAll() : 원하는 컬럼만 가지고 오도록 
	
	// 검색기능 
	Page<Article> findByTitleContaining(String word, Pageable pageable);
	
	@Query(nativeQuery = true,
				value = "select min(id) minId, max(id) maxId, count(*) cnt*"
						+ " from article"
						+ " where title like :word"	) 
	Map<String, Object> getMinMaxCount(String word);  // word = "a%"
}
