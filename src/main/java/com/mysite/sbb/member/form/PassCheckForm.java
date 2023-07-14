package com.mysite.sbb.member.form;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassCheckForm {
	
	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String memberPw1;
	
	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String memberPw2;

}
