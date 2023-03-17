package com.ll.basic1.boundedContext.article.service;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.article.entity.Article;
import com.ll.basic1.boundedContext.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article write(String title, String body) {
        Article article = Article
                .builder()
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .title(title)
                .body(body)
                .build();

        // 위 코드는 아래 코드와 비슷한 의미이다.
        /*
        Article article2 = new Article();
        article2.setTitle(title);
        article2.setBody(body);
        */
        articleRepository.save(article);

        return article;
    }
}
