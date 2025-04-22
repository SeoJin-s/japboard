package com.example.jpaboard.controller;

import org.springframework.data.domain.Pageable; 
import java.lang.ProcessBuilder.Redirect;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

import com.example.jpaboard.dto.MemberForm;
import com.example.jpaboard.entity.Member;
import com.example.jpaboard.repository.ArticleRepository;
import com.example.jpaboard.repository.MemberRepository;
import com.example.jpaboard.util.SHA256Util;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class MemberController {

    private final ArticleRepository articleRepository;
	@Autowired
	MemberRepository memberRepository;

    MemberController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
	
	// 회원가입 + member_id 중복확인
	@GetMapping("/member/joinMember")
	public String joinMember() {
		return "member/joinMember";
	}
	
	@PostMapping("/member/joinMember")
	public String joinMember(MemberForm memberForm, RedirectAttributes rda) {
		// memberForm.getMemberId() DB에 존재하는지?
		
		log.debug(memberForm.toString());
		log.debug("isMemberId :"+memberRepository.existsByMemberId(memberForm.getMemberId()));
		
		if (memberRepository.existsByMemberId(memberForm.getMemberId())) {
		    rda.addAttribute("msg", memberForm.getMemberId() + " ID가 이미 존재합니다.");
		    return "redirect:/member/joinMember";
		}
		
		// false 이면 회원가입 진행
		// memberForm.getMemberPw() 값을 SHA-256 방식으로 암호화
		memberForm.setMemberPw(SHA256Util.encoding(memberForm.getMemberPw()));
		
		Member member = memberForm.toEntity();
		memberRepository.save(member);	// entity 에 저장 -> 최종 커밋 -> 테이블에 행이 추가(insert)
			return "redirect:/member/login";

	}
	
	//로그인 폼 페이지
	@GetMapping("/member/login")
	public String loginForm() {
		return "member/login";
		
	}
	
	// 로그인
	@PostMapping("/member/login")
	public String login(MemberForm form, HttpSession session, RedirectAttributes rda) {
	    log.debug("로그인 요청: " + form.toString());

	    Member member = memberRepository.findByMemberId(form.getMemberId());
	    if (member != null && member.getMemberPw().equals(SHA256Util.encoding(form.getMemberPw()))) {
	        // 로그인 성공 → 세션 저장
	        session.setAttribute("loginName", member.getMemberId());
	        return "redirect:/";
	    }
		rda.addAttribute("msg", "로그인실패");
		return "member/login";
	}
	
	// 로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
	    session.invalidate(); // 세션 초기화
	    return "redirect:/";
	}
	// 마이페이지
	@GetMapping("/member/myPage")
	public String myPage() {
	    return "member/myPage";
	}
	
	// 회원정보수정
	@GetMapping("/member/update")
	public String updateForm(HttpSession session, Model model) {
		String loginId = (String) session.getAttribute("loginName");
		if(loginId == null) {
			return "redirect:/member/login";
		}
		
		Member member = memberRepository.findByMemberId(loginId);
		model.addAttribute("member", member);
		return "member/update";
		
	}
	
	// 회원정보 수정처리
	@PostMapping("/member/update")
	public String updateMember(@RequestParam String memberPw,
							   HttpSession session,
							   RedirectAttributes rda) {
		String loginId = (String) session.getAttribute("loginName");
		if (loginId == null) {
			return "redirect:/member/login";
		}
		
		Member member = memberRepository.findByMemberId(loginId);
		if (member != null) {
			member.setMemberPw(SHA256Util.encoding(memberPw));
		    memberRepository.save(member);
		    rda.addFlashAttribute("msg", "회원 정보가 수정되었습니다.");
		   }

		    return "redirect:/member/myPage";
		}
	
	// 회원목록
	@GetMapping("/member/memberList")
	public String memberList(Model model,
	                         @RequestParam(defaultValue = "0") int page,
	                         @RequestParam(defaultValue = "") String word,
	                         HttpSession session) {
	    String loginId = (String) session.getAttribute("loginName");
	    if (loginId == null) {
	        return "redirect:/member/login";
	    }

	    Pageable pageable = PageRequest.of(page, 10, Sort.by("memberNo").descending());
	    Page<Member> memberPage = memberRepository.findByMemberIdContaining(word, pageable);

	    model.addAttribute("memberPage", memberPage);
	    model.addAttribute("word", word);
	    model.addAttribute("loginMember", loginId);

	    // ✅ Mustache에서 쓸 수 있도록 직접 계산한 값 추가
	    model.addAttribute("numberPlus", memberPage.getNumber() + 1);  // 현재 페이지 (1부터 시작)
	    model.addAttribute("numberMinus", Math.max(0, memberPage.getNumber() - 1)); // 이전 페이지 (최소 0)

	    return "member/memberList";
	}

	
	// 회원목록보기
	
	
	// 탈퇴 페이지
	@GetMapping("/member/delete")
	public String deleteForm(HttpSession session) {
	    if (session.getAttribute("loginName") == null) {
	        return "redirect:/member/login"; // 로그인 안 되어 있으면 로그인 페이지로
	    }
	    return "member/delete"; // delete.mustache 반환
	}
	
	// 회원탈퇴
	@PostMapping("/member/delete")
	public String deleteMember(HttpSession session) {
		String loginId = (String) session.getAttribute("loginName");
		if (loginId == null) {
			return "redirect:/member/login";
		}
		
		Member member = memberRepository.findByMemberId(loginId);
		if (member != null) {
			memberRepository.delete(member); //db에서 삭제
		}
		
		session.invalidate(); // 세션 무효화
		return "redirect:/";
		
	}
	
}
