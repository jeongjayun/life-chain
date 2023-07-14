package com.mysite.sbb.member.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterForm {
	
	@NotEmpty(message = "이메일은 필수항목입니다.")
	@Email
	private String memberId;
	
	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String memberPw1;
	
	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String memberPw2;
	
	private String memberTel;

	@NotEmpty(message = "닉네임 확인은 필수항목입니다.")
	private String memberNick;

}
