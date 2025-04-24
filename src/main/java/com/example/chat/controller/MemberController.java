package com.example.chat.controller;

import com.example.chat.service.MemberService;
import com.example.chat.service.request.MemberCreateReqDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
       Long memberId =  memberService.create(reqDto);
       return new ResponseEntity<>(memberId, HttpStatus.CREATED);
    }

}
