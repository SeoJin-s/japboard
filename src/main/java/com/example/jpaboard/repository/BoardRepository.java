package com.example.jpaboard.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpaboard.entity.Article;
import com.example.jpaboard.entity.Board;

public  interface BoardRepository extends JpaRepository<Board, Integer > {

	// 검색기능 
	Page<Board> findByBoardTitleContaining(String word, Pageable pageable);
}

