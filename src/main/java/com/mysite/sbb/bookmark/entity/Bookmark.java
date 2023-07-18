package com.mysite.sbb.bookmark.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import antlr.collections.impl.BitSet;
import com.mysite.sbb.manual.entity.Manual;
import com.mysite.sbb.member.entity.Member;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manual_idx")
    private Manual manual;

    @CreationTimestamp
    private LocalDateTime createDate;

    @Builder
    public Bookmark(Integer id, Member member, Manual manual, LocalDateTime createDate) {
        this.id = id;
        this.member = member;
        this.manual = manual;
        this.createDate = createDate;
    }
}
