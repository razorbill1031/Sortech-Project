package com.sortech.sortech.service;

import com.sortech.sortech.domain.Notice;
import com.sortech.sortech.domain.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public Notice create(String title, String content, String author) {
        Notice notice = new Notice(title, content, author);
        return noticeRepository.save(notice);
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public Notice findById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("공지사항을 찾을 수 없습니다."));
    }

    public Notice update(Long id, String title, String content) {
        Notice notice = findById(id);
        notice.update(title, content);
        return noticeRepository.save(notice);
    }

    public void delete(Long id) {
        Notice notice = findById(id);
        noticeRepository.delete(notice);
    }
}