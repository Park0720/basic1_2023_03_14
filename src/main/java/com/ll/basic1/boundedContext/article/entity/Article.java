package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
public class Article {
    @Id // 메인 키 설정
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String title;
    private String body;

}
