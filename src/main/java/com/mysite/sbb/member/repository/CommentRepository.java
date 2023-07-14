package com.mysite.sbb.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.member.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
