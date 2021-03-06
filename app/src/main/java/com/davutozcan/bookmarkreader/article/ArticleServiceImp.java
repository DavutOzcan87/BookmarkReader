package com.davutozcan.bookmarkreader.article;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.exception.DomainException;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.myApp;

/**
 * Created by davut on 9/2/2017.
 */

public class ArticleServiceImp implements IArticleService {
    private final IArticleRepository repository;

    public ArticleServiceImp(IArticleRepository articleRepository) {
        this.repository = articleRepository;
    }

    @Override
    public ArticleDetail getArticle(String url)
    {
        ArticleDetail result =  repository.getArticle(url);
        if(result == null)
            throw new DomainException(myApp().getResources().getString(R.string.article_load_error));
        return result;
    }

}
