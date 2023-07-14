package com.mysite.sbb.member.mail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TempPasswordForm {
    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String memberId;
}
