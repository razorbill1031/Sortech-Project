package com.sortech.sortech.controller;

import com.sortech.sortech.domain.Notice;
import com.sortech.sortech.dto.NoticeRequest;
import com.sortech.sortech.service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @ModelAttribute NoticeRequest request,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        String author = authentication.getName();

        return ResponseEntity.ok(
                noticeService.create(
                        request.getTitle(),
                        request.getContent(),
                        author
                )
        );
    }

    @GetMapping
    public List<Notice> findAll() {
        return noticeService.findAll();
    }

    @GetMapping("/{id}")
    public Notice findById(@PathVariable Long id) {
        return noticeService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @ModelAttribute NoticeRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        return ResponseEntity.ok(
                noticeService.update(
                        id,
                        request.getTitle(),
                        request.getContent()
                )
        );
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        noticeService.delete(id);

        return "공지사항이 삭제되었습니다.";
    }
}