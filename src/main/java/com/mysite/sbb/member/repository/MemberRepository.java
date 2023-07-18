package com.mysite.sbb.member.repository;

import java.util.List;
import java.util.Optional;

import com.mysite.sbb.common.config.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findBymemberId(String memberId);

	Member findByMemberId(String memberId);

	List<Member> findByMemberStatus(MemberStatus memberStatus);

    Member findByMemberNick(String memberNick);
}
