package com.davidmlee.nytimes100.mvp_presenter;

import android.content.Intent;

import com.davidmlee.nytimes100.mvp_model.ListSummaryEntity;

import java.util.ArrayList;

/**
 * (non-Javadoc)
 */

public class ArticleDetailController {
    static private ListSummaryEntity listSummaryEntity;

    static public ListSummaryEntity getDetailEntity() {
        return ArticleDetailController.listSummaryEntity;
    }

    static private void setDetailEntity(ListSummaryEntity fde) {
        ArticleDetailController.listSummaryEntity = fde;
    }

    /**
     * @param index_article_list - index to the article list
     */
    static public void startDetailView(final int index_article_list) {
        ArrayList<ListSummaryEntity> ary = MainController.getArticleList();
        ArticleDetailController.setDetailEntity(ary.get(index_article_list));
        Intent i = new Intent(MainController.getMainActivity(), ArticleDetailActivity.class);
        MainController.getMainActivity().startActivity(i);
    }
}
