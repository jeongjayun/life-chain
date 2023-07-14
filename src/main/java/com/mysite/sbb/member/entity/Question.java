package com.mysite.sbb.member.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;

import com.mysite.sbb.member.entity.Answer;
import com.mysite.sbb.member.entity.Comment;
import com.mysite.sbb.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	/* 기본키(primary key) */
	@Id
	
	/* 자동증가(Auto Increment) */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	@OneToMany(mappedBy = "question")
	private List<Comment> commentList;
	
	@ManyToOne
	private Member author;
	
	private LocalDateTime modifyDate;
	
	private Integer views;
	
	@ManyToMany
	Set<Member> voter;
	// 추천 중복을 피하기 위해 Set
	
	public void setAuthor(Member author) {
        this.author = author;
        author.getQuestion().add(this);
    }
	
}


