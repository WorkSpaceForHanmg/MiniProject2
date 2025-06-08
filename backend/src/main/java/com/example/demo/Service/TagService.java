package com.example.demo.Service;

import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    // 현재 인증된 사용자 가져오기
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Tag> findAllTags() {
        User currentUser = getCurrentUser();
        return tagRepository.findByUser(currentUser);
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findTagById(Long tid) {
        User currentUser = getCurrentUser();
        Optional<Tag> tag = tagRepository.findById(tid);
        
        // 태그 소유자 검증
        if (tag.isPresent() && !tag.get().getUser().equals(currentUser)) {
            return Optional.empty(); // 권한이 없으면 빈 결과 반환
        }
        
        return tag;
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findTagByName(String name) {
        User currentUser = getCurrentUser();
        return tagRepository.findByUserAndName(currentUser, name);
    }

    @Transactional
    public Tag saveTag(Tag tag) {
        User currentUser = getCurrentUser();
        tag.setUser(currentUser); // 현재 사용자로 설정
        return tagRepository.save(tag);
    }

    @Transactional
    public void deleteTag(Long tid) {
        User currentUser = getCurrentUser();
        Optional<Tag> tag = tagRepository.findById(tid);
        
        if (tag.isEmpty()) {
            throw new IllegalArgumentException("태그를 찾을 수 없습니다.");
        }
        
        // 태그 소유자 검증
        if (!tag.get().getUser().equals(currentUser)) {
            throw new IllegalArgumentException("해당 태그를 삭제할 권한이 없습니다.");
        }
        
        tagRepository.deleteById(tid);
    }

    // 태그 ID 리스트로 해당 사용자의 태그들 조회
    @Transactional(readOnly = true)
    public List<Tag> getTagsByIds(List<Long> tagIds, User user) {
        return tagIds.stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .filter(tag -> tag.getUser().equals(user)) // 사용자 소유 태그만 필터링
                        .orElseThrow(() -> new IllegalArgumentException("태그를 찾을 수 없거나 접근 권한이 없습니다. ID: " + tagId))
                )
                .toList();
    }

    // 태그 이름 리스트 → 존재하면 가져오고, 없으면 생성 (사용자별)
    @Transactional
    public List<Tag> getOrCreateTagsByName(List<String> tagNames) {
        User currentUser = getCurrentUser();
        return tagNames.stream()
                .map(name -> tagRepository.findByUserAndName(currentUser, name)
                        .orElseGet(() -> tagRepository.save(Tag.builder()
                                .name(name)
                                .user(currentUser)
                                .build()))
                )
                .toList();
    }
}
