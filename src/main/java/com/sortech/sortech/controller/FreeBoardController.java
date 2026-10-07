package com.sortech.sortech.controller;

import com.sortech.sortech.domain.FreeBoard;
import com.sortech.sortech.dto.FreeBoardRequest;
import com.sortech.sortech.service.FreeBoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> create(
            @Valid @ModelAttribute FreeBoardRequest request,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        String author = authentication.getName();

        return ResponseEntity.ok(
                freeBoardService.create(
                        request.getTitle(),
                        request.getContent(),
                        author
                )
        );
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
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @ModelAttribute FreeBoardRequest request,
            BindingResult bindingResult,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        String author = authentication.getName();

        return ResponseEntity.ok(
                freeBoardService.update(
                        id,
                        request.getTitle(),
                        request.getContent(),
                        author
                )
        );
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id,
            Authentication authentication) {

        String author = authentication.getName();

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN")
                );

        freeBoardService.delete(id, author, isAdmin);

        return "게시글이 삭제되었습니다.";
    }
}