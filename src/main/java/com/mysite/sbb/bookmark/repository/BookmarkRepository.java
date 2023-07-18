package com.mysite.sbb.bookmark.repository;

import com.mysite.sbb.bookmark.entity.Bookmark;
import com.mysite.sbb.manual.entity.Manual;
import com.mysite.sbb.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    Optional<Bookmark> findByMemberAndManual(Member member, Manual manual);

}