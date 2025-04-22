package com.example.jpaboard.dto;

import com.example.jpaboard.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
    // 

	
    public BoardForm(Board board) {
    	 this.boardNo = board.getNo();
    	 this.boardTitle = board.getBoardTitle();
         this.boardContent = board.getBoardContent();
    }
	// DTO -> Entity 변환 메소드
	public Board toEntity() {
		Board entity = new Board();
		   	entity.setNo(this.boardNo);
	        entity.setBoardTitle(this.boardTitle);
	        entity.setBoardContent(this.boardContent);
			
			return entity;
	}
	
}
