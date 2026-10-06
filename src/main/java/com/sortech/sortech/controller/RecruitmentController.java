package com.sortech.sortech.controller;

import com.sortech.sortech.domain.Recruitment;
import com.sortech.sortech.dto.RecruitmentRequest;
import com.sortech.sortech.service.RecruitmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    public RecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @ModelAttribute RecruitmentRequest request,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasFieldErrors("title")) {
            return ResponseEntity.badRequest()
                    .body("제목을 입력해주세요.");
        }

        if (bindingResult.hasFieldErrors("content")) {
            return ResponseEntity.badRequest()
                    .body("내용을 입력해주세요.");
        }

        String author = authentication.getName();

        return ResponseEntity.ok(
                recruitmentService.create(
                        request.getTitle(),
                        request.getContent(),
                        author
                )
        );
    }

    @GetMapping
    public List<Recruitment> findAll() {
        return recruitmentService.findAll();
    }

    @GetMapping("/{id}")
    public Recruitment findById(@PathVariable Long id) {
        return recruitmentService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @ModelAttribute RecruitmentRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors("title")) {
            return ResponseEntity.badRequest()
                    .body("제목을 입력해주세요.");
        }

        if (bindingResult.hasFieldErrors("content")) {
            return ResponseEntity.badRequest()
                    .body("내용을 입력해주세요.");
        }

        return ResponseEntity.ok(
                recruitmentService.update(
                        id,
                        request.getTitle(),
                        request.getContent()
                )
        );
    }

    @DeleteMapping("/{id}")
    public String delete(Long id) {
        recruitmentService.delete(id);
        return "채용공고가 삭제되었습니다.";
    }
}