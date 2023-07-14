package com.mysite.sbb.member.repository;

import com.mysite.sbb.member.entity.MemberImg;
import com.mysite.sbb.member.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberImgRepository extends JpaRepository<MemberImg, Long> {
    MemberImg findByMemberInfoId(Long id);

    void deleteAllByMemberInfo(MemberInfo memberInfo);
}
