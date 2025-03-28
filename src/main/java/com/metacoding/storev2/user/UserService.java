package com.metacoding.storev2.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 동일한 username이 있는지 검사
        User user = userRepository.findByUsername(joinDTO.getUsername());
        // 2. 있으면, exception
        if (user != null) {
            throw new RuntimeException("동일한 username이 존재 합니다.");
        }
        // 3. 없으면 회원가입하기
        userRepository.save(joinDTO.getUsername(), joinDTO.getPassword(), joinDTO.getFullname());
    }
}
