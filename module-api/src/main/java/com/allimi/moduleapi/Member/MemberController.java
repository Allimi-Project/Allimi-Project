package com.allimi.moduleapi.Member;

import com.allimi.modulecore.member.MemberService;
import com.allimi.modulecore.member.SignUpDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/signup")
    public String signUp(@Valid SignUpDto signUpDto) {

        memberService.save(signUpDto);

        return "redirect:/login";
    }


}
