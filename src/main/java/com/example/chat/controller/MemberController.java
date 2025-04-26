package com.example.chat.controller;

import com.example.chat.common.res.success.CommonResDto;
import com.example.chat.common.res.SuccessCode;
import com.example.chat.service.MemberService;
import com.example.chat.service.request.MemberCreateReqDto;
import com.example.chat.service.request.MemberLoginReqDto;
import com.example.chat.service.response.MemberResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<?> memberCreate(@RequestBody @Valid MemberCreateReqDto reqDto) {
        memberService.create(reqDto);
        SuccessCode code = SuccessCode.CREATED;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(CommonResDto.of(code));
    }

    @PostMapping("/doLogin")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLoginReqDto reqDto) {

        // email, password 검증 및 access token 발생
        MemberResDto resDto = memberService.login(reqDto);

        SuccessCode code = SuccessCode.SUCCESS;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(CommonResDto.of(code, resDto));
    }
}
