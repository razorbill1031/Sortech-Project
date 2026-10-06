package com.sortech.sortech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NoticePageController {

    @GetMapping("/notice")
    public String notice() {
        return "notice";
    }

    @GetMapping("/notice/write")
    public String write() {
        return "notice-write";
    }

    @GetMapping("/notice/{id}")
    public String detail(@PathVariable Long id) {
        return "notice-detail";
    }

    @GetMapping("/notice/{id}/edit")
    public String edit(@PathVariable Long id) {
        return "notice-edit";
    }
}