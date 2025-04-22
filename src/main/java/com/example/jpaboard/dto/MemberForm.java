package com.example.jpaboard.dto;

import com.example.jpaboard.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class MemberForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberNo;
	@Column (name = "member_id")
	private String memberId;
	@Column (name = "member_pw")
	private String memberPw;
	
	public Member toEntity() {
		Member entity = new Member();
		entity.setMemberNo(this.memberNo);
		entity.setMemberId(this.memberId);
		entity.setMemberPw(this.memberPw);
		return entity;
	}
}
