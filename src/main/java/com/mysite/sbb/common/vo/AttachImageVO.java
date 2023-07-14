package com.mysite.sbb.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AttachImageVO {
    private String uploadPath; // 경로
    private String uuid; // UUID
    private String fileName; // 파일 이름

    @Override
    public String toString() {
        return "AttachImageVO{" +
                "uploadPath='" + uploadPath + '\'' +
                ", uuid='" + uuid + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
