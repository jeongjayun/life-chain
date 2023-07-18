package com.mysite.sbb.manual.controller;

import java.security.Principal;
import java.util.List;

import com.mysite.sbb.bookmark.service.BookmarkService;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.repository.MemberRepository;
import com.mysite.sbb.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mysite.sbb.manual.entity.Manual;
import com.mysite.sbb.manual.entity.ManualContent;
import com.mysite.sbb.manual.service.ManualContentService;
import com.mysite.sbb.manual.service.ManualService;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

@RequestMapping("/manual")
@RequiredArgsConstructor
@Controller
public class ManualController {

    private final ManualService manualService;
    private final ManualContentService manualContentService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    private static final Logger logger = LoggerFactory.getLogger(ManualController.class);

    @GetMapping("/list")
    public String list(Model model) {
//        public String list (Model model, Principal principal){
            List<Manual> manualList = this.manualService.getList();
//        String username = principal.getName(); // 사용자 이름 가져오기
//        Member member = memberRepository.findByMemberId(username); // 멤버를 조회합니다.
            model.addAttribute("manualList", manualList);
//        model.addAttribute("member", member);
            logger.info("manualList: " + manualList);
//        logger.info("접속 중인 멤버: " + member);
            return "/manual/manual_list";
        }


        @GetMapping("/detail/{id}")
        public String detail (Model model, @PathVariable("id") Integer id){
            Manual manual = this.manualService.getManual(id);
            model.addAttribute("manual", manual);
            return "/manual/manual_detail";
        }


        @ResponseBody
        @RequestMapping(value = "/manualContent", method = RequestMethod.GET)
        public List<ManualContent> manualList () {
            List<ManualContent> manualContentList = manualContentService.getManualContentWithManual();
            return manualContentList;
        }


        @GetMapping("/search")
        public String search () {
            return "/manual/manual_search";
        }


//	public ManualContent getMContent(Integer id) {
//	    String sql = "SELECT m.id, m.title, c.subject, c.content " +
//	                "FROM manual m" +
//	                "LEFT JOIN manual_content c " +
//	                "ON m.id = c.m_id " +
//	                "WHERE m.id = ? " +
//	                "ORDER BY c.id ASC " +
//	                "LIMIT 1;";
//	    List<ManualContent> result = JdbcTemplate.query(sql, new Object[]{id}, new MContentRowMapper());
//	    return result.isEmpty() ? null : result.get(0);
//	}


    }