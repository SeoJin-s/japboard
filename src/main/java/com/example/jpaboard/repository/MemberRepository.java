package com.example.jpaboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpaboard.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	// member_id 중복검사
	
	// 회원가입 중복 확인용
	boolean existsByMemberId(String memberId);
	
	// 회원탈퇴
	Member findByMemberId(String memberId);
	void delete(Member member);
	
	//회원 목록
	Page<Member> findByMemberIdContaining(String memberId, Pageable pageable);
	
}
