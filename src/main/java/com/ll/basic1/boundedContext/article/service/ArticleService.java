package com.ll.basic1.boundedContext.article.service;

import com.ll.basic1.boundedContext.article.entity.Article;
import com.ll.basic1.boundedContext.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article write(String title, String body) {
        Article article = Article
                .builder()
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
