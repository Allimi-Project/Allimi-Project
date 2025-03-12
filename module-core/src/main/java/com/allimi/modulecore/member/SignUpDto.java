package com.allimi.modulecore.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

    @NotEmpty(message = "이름을 입력주세요")
    private String username;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.(com|net)$", message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{4,}$",
            message = "비밀번호는 4자리 이상 영문 소문자와 숫자의 조합이어야 합니다."
    )
    private String password;

}
