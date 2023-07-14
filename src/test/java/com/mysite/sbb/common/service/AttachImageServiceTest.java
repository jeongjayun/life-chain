package com.mysite.sbb.common.service;

import com.mysite.sbb.member.entity.MemberImg;
import com.mysite.sbb.member.entity.MemberInfo;
import com.mysite.sbb.member.repository.MemberImgRepository;
import com.mysite.sbb.member.repository.MemberInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AttachImageServiceTest {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AttachImageServiceTest.class);

    @Autowired
    MemberImgRepository attachImageRepository;

    @Autowired
    MemberInfoRepository memberInfoRepository;

    @Test
    @DisplayName("이미지를 리스트에 담아 memberInfo와 연결한다.")
    public void saveAttachImageTest() {
        MemberInfo memberInfo=memberInfoRepository.findByMemberId("admin@example.com");

        //이미지 추가
        List<MemberImg> list = new ArrayList<>();
        MemberImg attachImage = new MemberImg();
        attachImage.setUploadPath("/Users/jeongjayun/workspace/upload/lifechain");
        attachImage.setUuid("uuid_test");
        attachImage.setFileName("test_code_test1.png");
        attachImage.setMemberInfo(memberInfo);
        list.add(attachImage);

        MemberImg attachImage2 = new MemberImg();
        attachImage2.setUploadPath("/Users/jeongjayun/workspace/upload/lifechain");
        attachImage2.setUuid("uuid_test");
        attachImage2.setFileName("test_code_test2.png");
        attachImage2.setMemberInfo(memberInfo);
        list.add(attachImage2);

        logger.info("attachImage : " + attachImage);
        logger.info("attachImage2 : " + attachImage2);
        logger.info(list.toString());

        // 이미지 저장
        attachImageRepository.saveAll(list);
    }

}