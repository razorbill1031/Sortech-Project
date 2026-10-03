package com.sortech.sortech.service;

import com.sortech.sortech.domain.FreeBoard;
import com.sortech.sortech.domain.FreeBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;

    public FreeBoardService(FreeBoardRepository freeBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
    }

    public FreeBoard create(String title, String content, String author) {
        FreeBoard freeBoard = new FreeBoard(title, content, author);
        return freeBoardRepository.save(freeBoard);
    }

    public List<FreeBoard> findAll() {
        return freeBoardRepository.findAll();
    }

    public FreeBoard findById(Long id) {
        return freeBoardRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    public FreeBoard update(
            Long id,
            String title,
            String content,
            String author) {

        FreeBoard freeBoard = findById(id);

        if (!freeBoard.getAuthor().equals(author)) {
            throw new IllegalArgumentException("게시글 작성자만 수정할 수 있습니다.");
        }

        freeBoard.update(title, content);

        return freeBoardRepository.save(freeBoard);
    }

    public void delete(Long id, String author) {

        FreeBoard freeBoard = findById(id);

        if (!freeBoard.getAuthor().equals(author)) {
            throw new IllegalArgumentException("게시글 작성자만 삭제할 수 있습니다.");
        }

        freeBoardRepository.delete(freeBoard);
    }
}