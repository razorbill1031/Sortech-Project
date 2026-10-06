package com.sortech.sortech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecruitmentPageController {

    @GetMapping("/recruitment")
    public String recruitment() {
        return "recruitment";
    }

    @GetMapping("/recruitment/write")
    public String write() {
        return "recruitment-write";
    }

    @GetMapping("/recruitment/{id}")
    public String detail() {
        return "recruitment-detail";
    }

    @GetMapping("/recruitment/{id}/edit")
    public String edit() {
        return "recruitment-edit";
    }
}