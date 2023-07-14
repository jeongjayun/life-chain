package com.mysite.sbb.member.entity;

import com.mysite.sbb.common.entity.BaseEntity;
import com.mysite.sbb.common.vo.AttachImageVO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MemberInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "info_member_id")
    private String memberId; // memberId 필드 이름 변경

    private String zipcode;
    private String address1;
    private String address2; //주소 api 연결해서 쓰고
    private String memberIntroduce; //자기소개, 한마디
    @Column(unique = true) //전화번호 중복 방지
    private String memberTel;

    @ElementCollection
    private List<AttachImageVO> imageList = new ArrayList<>();

    public List<AttachImageVO> getImageList() {
        return imageList;
    }

    public void setImageList(List<AttachImageVO> imageList) {
        this.imageList = imageList;
    }

    @Override
    public String toString() {
        return "MemberInfo{" +
                "id=" + id +
                ", member=" + member +
                ", memberId='" + memberId + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", memberIntroduce='" + memberIntroduce + '\'' +
                ", memberTel='" + memberTel + '\'' +
                ", imageList=" + imageList +
                '}';
    }
}
