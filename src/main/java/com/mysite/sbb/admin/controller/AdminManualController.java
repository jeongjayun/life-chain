package com.mysite.sbb.admin.controller;

import com.mysite.sbb.manual.entity.Manual;
import com.mysite.sbb.manual.service.ManualService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/manual")
public class AdminManualController {
    @Autowired
    ManualService manualService;
    private static final Logger logger = LoggerFactory.getLogger(AdminManualController.class);
    @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;

    // 회원목록
    @GetMapping("/list")
    public String Manuallist(Model model) {
        List<Manual> manualList = this.manualService.getList();
        model.addAttribute("manualList", manualList);
        logger.info("manualList: " + manualList);
        return "/admin/templates/pages/manual/list";
    }
}
