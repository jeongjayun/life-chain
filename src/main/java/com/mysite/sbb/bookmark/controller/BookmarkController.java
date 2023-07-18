package com.mysite.sbb.bookmark.controller;

import com.mysite.sbb.bookmark.dto.BookmarkDto;
import com.mysite.sbb.member.repository.MemberRepository;
import com.mysite.sbb.bookmark.service.BookmarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.mysite.sbb.bookmark.repository.BookmarkRepository;
import com.mysite.sbb.manual.service.ManualService;
import com.mysite.sbb.member.service.MemberService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/bookmark")
@RequiredArgsConstructor
@Controller
public class BookmarkController {
    private final MemberService memberService;
    private final ManualService manualService;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkService bookmarkService;

    private static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);

    @PostMapping("/add/{id}")
    public ResponseEntity<?> insert(@PathVariable("id") Integer id, @PathVariable("memberId") Long memberId, @RequestBody @Valid BookmarkDto bookmarkDto) throws Exception {
        bookmarkDto.setMemberId(memberId); // 실제 멤버의 식별자 값을 할당
        bookmarkDto.setManualId(id); // 실제 매뉴얼의 식별자 값을 할당

        logger.info("bookmarkDto MemberId : " + bookmarkDto.getMemberId());
        logger.info("bookmarkDto ManualId : " + bookmarkDto.getManualId());

        bookmarkService.insert(bookmarkDto);
        return ResponseEntity.status(HttpStatus.OK).build(); // 성공 응답을 반환
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestBody @Valid BookmarkDto bookmarkDto) throws Exception {
        bookmarkDto.setManualId(id);
        bookmarkService.delete(bookmarkDto);
        return ResponseEntity.status(HttpStatus.OK).build(); // 성공 응답을 반환
    }

   /* @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}") // 변경된 URL
    public String bookmark(Principal principal, @PathVariable("id") Integer id) {
        Manual manual = this.manualService.getManual(id);
        Member member = this.memberService.getMember(principal.getName());
        Bookmark bookmark = new Bookmark();
        bookmark.setManual(manual);
        bookmark.setAuthor(member);
        return "/manual/list";
    }

    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> bookmarkLike(Principal principal, @PathVariable("id") Integer id) {

        Manual manual = manualService.getManual(id);
        Member member = memberService.getMember(principal.getName());

        Bookmark bookmark = new Bookmark();
        bookmark.setManual(manual);
        bookmark.setAuthor(member);
        bookmarkRepository.save(bookmark);

        return ResponseEntity.ok("북마크가 추가되었습니다.");
    }

    @DeleteMapping("/{manualId}/{memberId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> unbookmarkLike(Principal principal, @PathVariable("manualId") Integer manualId, @PathVariable("memberId") Integer memberId) {

        bookmarkRepository.deleteByAuthorAndManual(manualId, memberId);

        return ResponseEntity.ok("북마크가 해제되었습니다.");
    }*/
}