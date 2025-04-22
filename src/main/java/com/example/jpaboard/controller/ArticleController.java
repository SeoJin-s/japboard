package com.example.jpaboard.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jpaboard.JpaboardApplication;
import com.example.jpaboard.dto.ArticleForm;
import com.example.jpaboard.entity.Article;
import com.example.jpaboard.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ArticleController {
	@Autowired // 의존성주입
	private ArticleRepository articleRepository;
	
	@GetMapping("/articles/sqlTest")
	public String sqlTest(Model model) {
		Map<String, Object> map = articleRepository.getMinMaxCount("a%");
		log.debug(map.toString());
		model.addAttribute("map", map);
		return "articles/sqlTest";
		
	}
	
	@GetMapping("/articles/delete")
	public String delete(@RequestParam Long id, RedirectAttributes rda) {
		Article article = articleRepository.findById(id).orElse(null);
	
		if(article == null) {
			rda.addAttribute("msg", "삭제실패");	// redirect 의 뷰의 모델에서 자동으로 출력가능
			return "redirect:/articles/show?id="+id;
		}
		articleRepository.delete(article);
		
		rda.addAttribute("msg", "삭제성공");
		return "redirect:/articles/index";
	}
	
	@PostMapping("/articles/update")
	public String update(ArticleForm articleForm) {
		Article article = articleForm.toEntity();	// 저장하면 새로운 행에 저장x // 원래 키값을 수정
		// entity 가 키 값을 가지고 있으면 새로운 행을 추가 하는게 아니라
		// 존재하는 키값의 행을 수정하게 된다( update )
		articleRepository.save(article);
		return "redirect:/articles/show?id=" + articleForm.getId();
				
	}
	
	@GetMapping("/articles/edit") // doGet()
	public String edit(Model model, @RequestParam Long id) {
		Article article = articleRepository.findById(id).orElse(null);
		model.addAttribute("article", article);
		return"articles/edit";	// forward
	}
	
	@GetMapping("/articles/show") // doGet()
	public String show(Model model, @RequestParam Long id) {
		Article article = articleRepository.findById(id).orElse(null);
		model.addAttribute("article", article);
		return"articles/show";	// forward
	}
	
	@PostMapping("/articles/create") // doPost
	public String createArticle(ArticleForm form) {	//@RequestParma, DTO(커맨드객체)
		System.out.println(form.toString());
	    // DTO → Entity
	    Article entity = form.toEntity();
	    articleRepository.save(entity);	// Repository 를 호출할때는 Entity 가 필요하다.
	    return "redirect:/articles/index"; // GET 방식으로 호출 redirect:/articles/list
	}
	
	@GetMapping("/articles/index")
	public String articleList(Model model
							, @RequestParam(value = "currentPage", defaultValue = "0") int currentPage
							, @RequestParam(value = "rowPerPage", defaultValue = "10") int rowPerPage
							, @RequestParam(value = "word", defaultValue = "") String word) {
		
		
		Sort s1 = Sort.by("id").ascending();
		Sort s2 = Sort.by("content").descending();
		Sort sort = s1.and(s2);
		
		PageRequest pageable = PageRequest.of(currentPage, rowPerPage, sort); // 0 ~ 10 page	
		Page<Article> list = articleRepository.findByTitleContaining(word, pageable);
		
		// Page의 추가속성
		log.debug("list.getTotalElements():"+list.getTotalElements()); // list.size 전체 페이지 사이즈
		log.debug("list.getTotalPages():"+list.getTotalPages());	// 전체페이지 lastPage
		log.debug("list.getNumber():"+list.getNumber());	// currentPage
		log.debug("list.getSize():"+list.getSize());	// rowPerPage
		log.debug("list.isFirst():"+list.isFirst());	// 1페이지인지 : 이전링크 유무
		log.debug("list.hasNext():"+list.hasNext());	// 다음이 있는지 : 다음링크유무
		
		model.addAttribute("list", list);
		model.addAttribute("prePage" , list.getNumber()-1);
		model.addAttribute("nextPage" , list.getNumber()+1);
		model.addAttribute("word" , word);
		// redirect로 호출되었다면...
		return "articles/index";
	}
}
