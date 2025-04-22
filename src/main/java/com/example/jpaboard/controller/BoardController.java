package com.example.jpaboard.controller;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jpaboard.dto.BoardForm;
import com.example.jpaboard.entity.Article;
import com.example.jpaboard.entity.Board;
import com.example.jpaboard.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class BoardController {
	@Autowired // 의존성주입
    private  BoardRepository boardRepository;
	
	//상세페이지
	@GetMapping("/board/{no}")
	public String showBoardOne(@PathVariable("no") int no, Model model) {
	    Board board = boardRepository.findById(no)
	            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 번호: " + no));

	    model.addAttribute("board", board);
	    return "board/boardOne";
	}
	
	// 🚀 웹 시작시 boardList로 리디렉션
    //@GetMapping("/board/boardList")
    //public String redirectToBoardList() {
     //   return "redirect:/board/boardList";
    //}
    // 글쓰기로 가는 리디렉션
    @GetMapping("/board/addBoard")
    public String showAddBoardForm() {
        return "board/addBoard"; // templates/board/addBoard.mustache 렌더링
    }
	
    // 새로글쓰기
    @PostMapping("/board/create")
    public String createBoard(BoardForm form) {
        log.debug("게시글 작성 요청: {}", form);
        
        // DTO -> Entity
        Board entity = form.toEntity();

        boardRepository.save(entity);

        return "redirect:/board/boardList"; // 작성 후 목록으로 이동
    }
    
    // list 페이징 첫화면
    @GetMapping("/board/boardList")
    public String boardList(Model model,
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "10") int rowPerPage,
            @RequestParam(defaultValue = "") String word) {

    	Sort sort = Sort.by("id").descending();
        PageRequest pageable = PageRequest.of(currentPage, rowPerPage, sort);

        Page<Board> entityPage = boardRepository.findByBoardTitleContaining(word, pageable);
        Page<BoardForm> dtoPage = entityPage.map(BoardForm::new);

        model.addAttribute("list", dtoPage);
        model.addAttribute("prePage", dtoPage.getNumber() - 1);
        model.addAttribute("nextPage", dtoPage.getNumber() + 1);
        model.addAttribute("word", word);

        return "board/boardList";
    }

    

    
    // 게시글 수정 처리
    @PostMapping("/board/update")
    public String updateBoard(BoardForm form, RedirectAttributes rda) {
        Board board = boardRepository.findById(form.getBoardNo())
                .orElseThrow(() -> new IllegalArgumentException("수정 대상 게시글 없음"));

        board.setBoardTitle(form.getBoardTitle());
        board.setBoardContent(form.getBoardContent());
        boardRepository.save(board);

        rda.addFlashAttribute("msg", "수정 완료"); // 💬 선택사항

        return "redirect:/board/boardList"; // ✅ 리스트 페이지로 이동
    }
    @PostMapping("/board/delete/{boardNo}")
    public String delete(@PathVariable("boardNo") int boardNo, RedirectAttributes rda) {
        Board board = boardRepository.findById(boardNo).orElse(null);

        if (board == null) {
            rda.addAttribute("msg", "삭제 실패");
            return "redirect:/board/modify/" + boardNo;
        }

        boardRepository.delete(board);
        rda.addAttribute("msg", "삭제 성공");
        return "redirect:/board/boardList"; 
    }
 
	@GetMapping("/board/modify/{boardNo}")
	public String showModifyForm(@PathVariable("boardNo") int boardNo, Model model) {
	    Board board = boardRepository.findById(boardNo)
	            .orElseThrow(() -> new IllegalArgumentException("수정 대상 게시글이 존재하지 않습니다."));
	    model.addAttribute("board", board);
	    return "board/modifyBoard"; // ✅ 이 템플릿도 이미 있음!
 }
}