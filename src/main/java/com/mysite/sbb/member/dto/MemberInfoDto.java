package com.mysite.sbb.member.dto;

import com.mysite.sbb.common.vo.AttachImageVO;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.entity.MemberInfo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor //생성자 자동추가
public class MemberInfoDto {
    private String memberId;
    private String zipcode;
    private String address1;
    private String address2; //주소 api
    private String memberIntroduce; //자기소개, 한마디
    private String memberTel;
    private Member member;
    private List<AttachImageVO> imageList = new ArrayList<>();

    public List<AttachImageVO> getImageList() {
        return imageList;
    }

    public void setImageList(List<AttachImageVO> imageList) {
        this.imageList = imageList;
    }


    public MemberInfo toEntity() {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(this.memberId);
        memberInfo.setZipcode(this.zipcode);
        memberInfo.setAddress1(this.address1);
        memberInfo.setAddress2(this.address2);
        memberInfo.setMemberIntroduce(this.memberIntroduce);
        memberInfo.setMemberTel(this.memberTel);
        return memberInfo;
    }
}