package com.allimi.modulecore.member;

import lombok.Data;


import java.io.Serializable;

@Data
public class MemberDto implements Serializable { // 직렬화 가능하도록 Serializable 구현

    private static final long serialVersionUID = 1L; // 직렬화 버전 명시

    private Long id;
    private String email;

    private String password;


    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
    }

}
