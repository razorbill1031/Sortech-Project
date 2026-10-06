package com.sortech.sortech.service;

import com.sortech.sortech.domain.Recruitment;
import com.sortech.sortech.domain.RecruitmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentService(RecruitmentRepository recruitmentRepository) {
        this.recruitmentRepository = recruitmentRepository;
    }

    public Recruitment create(String title, String content, String author) {
        Recruitment recruitment = new Recruitment(title, content, author);
        return recruitmentRepository.save(recruitment);
    }

    public List<Recruitment> findAll() {
        return recruitmentRepository.findAll();
    }

    public Recruitment findById(Long id) {
        return recruitmentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("채용공고를 찾을 수 없습니다."));
    }

    public Recruitment update(Long id, String title, String content) {
        Recruitment recruitment = findById(id);
        recruitment.update(title, content);
        return recruitmentRepository.save(recruitment);
    }

    public void delete(Long id) {
        Recruitment recruitment = findById(id);
        recruitmentRepository.delete(recruitment);
    }
}