package com.mysite.sbb.member.entity;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public enum MemberStatus {
    ACTIVE("유효계정"),
    DORMANT("휴면계정"),
    DEACTIVE("정지계정"),
    WITHDRAWAL("탈퇴계정");

    private final String status;

    MemberStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isDormant() {
        return this == DORMANT;
    }

    public boolean isDeactive() {
        return this == DEACTIVE;
    }

    public boolean isWithdrawal() {
        return this == WITHDRAWAL;
    }
}
