package com.sortech.sortech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FreeBoardPageController {

    @GetMapping("/free-board")
    public String freeBoard() {
        return "free-board";
    }

    @GetMapping("/free-board/write")
    public String write() {
        return "free-board-write";
    }

    @GetMapping("/free-board/{id}")
    public String detail() {
        return "free-board-detail";
    }

    @GetMapping("/free-board/{id}/edit")
    public String edit() {
        return "free-board-edit";
    }
}