package com.ll.basic1.boundedContext.article.repository;

import com.ll.basic1.boundedContext.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// 이 클래스에는 @Repository를 생략해도 된다.
public interface ArticleRepository extends JpaRepository<Article, Long> {//<다룰 테이블, 테이블의 메인 키 타입>


}
