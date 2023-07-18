package com.mysite.sbb.bookmark.service;

import com.mysite.sbb.bookmark.controller.BookmarkController;
import com.mysite.sbb.bookmark.dto.BookmarkDto;
import com.mysite.sbb.bookmark.entity.Bookmark;
import com.mysite.sbb.manual.entity.Manual;
import com.mysite.sbb.manual.repository.ManualRepository;
import com.mysite.sbb.bookmark.repository.BookmarkRepository;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.repository.MemberRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {
    private final ManualRepository manualRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookmarkService.class);

    public void insert(BookmarkDto bookmarkDto) throws Exception {
        logger.info("insert 실행 --------------------");

        Member member = memberRepository.findById(bookmarkDto.getMemberId())
                .orElseThrow(() -> new NotFoundException("Could not find member with id: " + bookmarkDto.getMemberId()));

        logger.info("bookmarkDto.getMemberId() 확인 : " + bookmarkDto.getMemberId());

        Manual manual = manualRepository.findById(bookmarkDto.getManualId())
                .orElseThrow(() -> new NotFoundException("Could not find manual with id: " + bookmarkDto.getManualId()));

        logger.info("bookmarkDto.getManualId() 확인 : " + bookmarkDto.getManualId());

        if (bookmarkRepository.findByMemberAndManual(member, manual).isPresent()) {
            throw new Exception("Bookmark already exists for member: " + bookmarkDto.getMemberId() + " and manual: " + bookmarkDto.getManualId());
        }

        Bookmark bookmark = Bookmark.builder()
                .manual(manual)
                .member(member)
                .build();

        logger.info("Bookmark.builder() 확인 : " + bookmark);
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void delete(BookmarkDto bookmarkDto) throws Exception {
        logger.info("delete 실행 --------------------");
        Member member = memberRepository.findById(bookmarkDto.getMemberId())
                .orElseThrow(() -> new NotFoundException("Could not find member with id: " + bookmarkDto.getMemberId()));

        logger.info("bookmarkDto.getMemberId() 확인 : " + bookmarkDto.getMemberId());

        Manual manual = manualRepository.findById(bookmarkDto.getManualId())
                .orElseThrow(() -> new NotFoundException("Could not find manual with id: " + bookmarkDto.getManualId()));

        logger.info("bookmarkDto.getManualId() 확인 : " + bookmarkDto.getManualId());

        Bookmark bookmark = bookmarkRepository.findByMemberAndManual(member, manual)
                .orElseThrow(() -> new NotFoundException("Bookmark not found"));

        logger.info("bookmark 확인 : " + bookmark);
        bookmarkRepository.delete(bookmark);
    }
}