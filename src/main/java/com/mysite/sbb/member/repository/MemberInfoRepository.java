package com.mysite.sbb.member.repository;

import com.mysite.sbb.member.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
    MemberInfo findByMemberId(String memberId);
    MemberInfo findById(int i);
}
