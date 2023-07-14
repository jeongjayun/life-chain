package com.mysite.sbb.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.member.entity.Member;

public interface AccountRepository extends JpaRepository<Member, Long> {



}
