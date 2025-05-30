package com.example.demo.Service;

import com.example.demo.entity.Diary;
import com.example.demo.entity.DiaryTag;
import com.example.demo.entity.Tag;
import com.example.demo.repository.DiaryTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryTagService {

    private final DiaryTagRepository diaryTagRepository;

    // 전체 DiaryTag 조회
    public List<DiaryTag> findAllDiaryTags() {
        return diaryTagRepository.findAll();
    }

    // ID로 DiaryTag 조회
    public Optional<DiaryTag> findDiaryTagById(Long dtid) {
        return diaryTagRepository.findById(dtid);
    }

    // 특정 태그에 연결된 DiaryTag 조회
    public List<DiaryTag> findDiaryTagsByTag(Tag tag) {
        return diaryTagRepository.findByTag(tag);
    }

    // 새 DiaryTag 저장
    public DiaryTag saveDiaryTag(DiaryTag diaryTag) {
        return diaryTagRepository.save(diaryTag);
    }

    // DiaryTag 삭제
    public void deleteDiaryTag(Long dtid) {
        diaryTagRepository.deleteById(dtid);
    }

    // 다이어리에 태그 연결하는 핵심 메서드
    public void connectTagsToDiary(Diary diary, List<Tag> tags) {
        List<DiaryTag> diaryTags = tags.stream()
                .map(tag -> DiaryTag.builder()
                        .diary(diary)
                        .tag(tag)
                        .build())
                .toList();

        diary.getDiaryTags().clear();
        diary.getDiaryTags().addAll(diaryTags);
    }
}
