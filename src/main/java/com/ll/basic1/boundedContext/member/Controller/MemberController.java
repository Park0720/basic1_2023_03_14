package com.ll.basic1.boundedContext.member.Controller;

import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            return RsData.of("F-3", "username(을)를 입력해주세요.");
        }
        if (password == null || password.trim().length() == 0) {
            return RsData.of("F-4", "password(을)를 입력해주세요.");
        }
        RsData rsData = memberService.tryLogin(username, password);

        if ( rsData.isSuccess()){
            //쿠키 만들기
            Member member = (Member) rsData.getData();
            rq.setCookie("loginedMemberId", member.getId());
        }
        return rsData;
    }
    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout() {
        boolean cookieRemoved = rq.removeCookie("loginedMemberId");

        if (cookieRemoved == false) {
            return RsData.of("S-2", "이미 로그아웃 상태입니다.");
        }
        return RsData.of("S-1","로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe() {
        long loginedMemberId = rq.getCookieAsLong("loginedMemberId", 0);

        boolean isLogined = loginedMemberId > 0;

        if (!isLogined) {
            return RsData.of("F-1", "로그인 후 이용해주세요.");
        }
        Member member = memberService.findById(loginedMemberId);

        return RsData.of("S-1", "당신의 username은 %s입니다.".formatted(member.getUsername()));
    }
}
