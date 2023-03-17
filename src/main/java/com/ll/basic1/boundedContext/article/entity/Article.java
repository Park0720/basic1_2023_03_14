package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Article {
    @Id // 메인 키 설정
    private long id;
    private String title;
    private String body;

}
