package com.mysite.sbb.bookmark.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkDto {
    private Long memberId;
    private int manualId;

    public BookmarkDto(Long memberId, int manualId) {
        this.memberId = memberId;
        this.manualId = manualId;
    }
}
