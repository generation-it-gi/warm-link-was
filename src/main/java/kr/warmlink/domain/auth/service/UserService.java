package kr.warmlink.domain.auth.service;

import kr.warmlink.application.auth.dto.SignInDto;
import kr.warmlink.domain.auth.entity.User;
import kr.warmlink.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void create(User user) {
        userRepository.save(user);
    }

    @Transactional
    public String register(SignInDto.Register dto) {
        String message = "회원정보 등록에 성공하였습니다.";
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .interestField(dto.getInterestField())
                .birth(dto.getBirth())
                .region(dto.getRegion())
                .job(dto.getJob())
                .build();

        create(user);
        return message;
    }

}
