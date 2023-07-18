package com.mysite.sbb.bookmark.controller;

import com.mysite.sbb.bookmark.dto.BookmarkDto;
import com.mysite.sbb.manual.entity.Manual;
import com.mysite.sbb.manual.repository.ManualRepository;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookmarkControllerTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ManualRepository manualRepository;
    @Autowired
    BookmarkController bookmarkController;
    @Test
    @DisplayName("북마크에 메뉴얼을 등록한다.")
    public void insertTest() throws Exception {
        long memberId = 1L; // 원하는 memberId 값으로 설정
        int manualId = 3; // 원하는 manualId 값으로 설정

        BookmarkDto bookmarkDto = new BookmarkDto(memberId, manualId);

        Optional<Member> memberOptional = memberRepository.findById(memberId);
        Optional<Manual> manualOptional = manualRepository.findById(manualId);

        assertTrue(memberOptional.isPresent(), "Member가 존재하지 않습니다.");
        assertTrue(manualOptional.isPresent(), "Manual이 존재하지 않습니다.");

        ResponseEntity<?> responseEntity = bookmarkController.insert(bookmarkDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "북마크 등록에 실패했습니다.");
    }

    @Test
    @DisplayName("북마크에서 메뉴얼을 삭제한다.")
    public void deleteTest() throws Exception {
        long memberId = 1L; // 원하는 memberId 값으로 설정
        int manualId = 3; // 원하는 manualId 값으로 설정

        BookmarkDto bookmarkDto = new BookmarkDto(memberId, manualId);

        Optional<Member> memberOptional = memberRepository.findById(memberId);
        Optional<Manual> manualOptional = manualRepository.findById(manualId);

        assertTrue(memberOptional.isPresent(), "Member가 존재하지 않습니다.");
        assertTrue(manualOptional.isPresent(), "Manual이 존재하지 않습니다.");

        ResponseEntity<?> responseEntity = bookmarkController.delete(bookmarkDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "북마크 등록에 실패했습니다.");
    }


   /* @Test
    @DisplayName("북마크에 메뉴얼을 등록한다.")
    public ResponseEntity<?> insertTest() throws Exception {
        long memberId = 1L; // 원하는 memberId 값으로 설정
        long manualId = 1L; // 원하는 manualId 값으로 설정

        BookmarkDto bookmarkDto = new BookmarkDto(memberId, manualId);

        Optional<Member> tester = memberRepository.findById(memberId);
        Optional<Manual> manual = manualRepository.findById(manualId);

        bookmarkService.insert(bookmarkDto);
        return ResponseEntity.status(HttpStatus.OK).build(); // 성공 응답을 반환
    }*/

    /*@Test
    @DisplayName("북마크에 메뉴얼을 등록한다.")
    public ResponseEntity<?> insertTest(@RequestBody @Valid BookmarkDto bookmarkDto) throws Exception {
        long id = 1; // member 및 manual idx 1
        Optional<Member> tester = memberRepository.findById(id);
        Optional<Manual> manual = manualRepository.findById(id);

        bookmarkService.insert(bookmarkDto);
        return ResponseEntity.status(HttpStatus.OK).build(); // 성공 응답을 반환
    }*/

}