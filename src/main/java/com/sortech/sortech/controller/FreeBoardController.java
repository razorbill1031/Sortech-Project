package com.sortech.sortech.controller;

import com.sortech.sortech.domain.FreeBoard;
import com.sortech.sortech.service.FreeBoardService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/free-board")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    public FreeBoardController(FreeBoardService freeBoardService) {
        this.freeBoardService = freeBoardService;
    }

    @PostMapping
    public FreeBoard create(
            @RequestParam String title,
            @RequestParam String content,
            Authentication authentication) {

        String author = authentication.getName();

        return freeBoardService.create(title, content, author);
    }

    @GetMapping
    public List<FreeBoard> findAll() {
        return freeBoardService.findAll();
    }

    @GetMapping("/{id}")
    public FreeBoard findById(@PathVariable Long id) {
        return freeBoardService.findById(id);
    }

    @PutMapping("/{id}")
    public FreeBoard update(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            Authentication authentication) {

        String author = authentication.getName();

        return freeBoardService.update(id, title, content, author);
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id,
            Authentication authentication) {

        String author = authentication.getName();

        freeBoardService.delete(id, author);

        return "게시글이 삭제되었습니다.";
    }
}