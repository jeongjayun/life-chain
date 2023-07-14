package com.mysite.sbb.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
//엔티티를 DB에 적용하기 전, 이후 커스텀 콜백 요청가능한 함수
//@EntityListeners 의 인자로 커스텀 콜백을 요청할 클래스를 지정해주면 되는데,
//Auditing 을 수행할 때는 JPA 에서 제공하는 AuditingEntityListener.class 를 인자로 넘기면 된다.
@MappedSuperclass //엔티티의 공통 매핑 정보가 필요할 때 주로 사용
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updatedDate;
}
