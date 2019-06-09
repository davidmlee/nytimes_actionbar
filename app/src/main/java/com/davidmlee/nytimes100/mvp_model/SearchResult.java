package com.davidmlee.nytimes100.mvp_model;

/**
 */
public class SearchResult {

    private int totalArticlesFetched = -1; // Keep for debug
    private int totalPagesFetched = -1;
    private int lastFetchedPageNum = -1;   // 0-based
    private int lastFetchedPage_NumArticlesInPage = -1; // either 10 or less
    private String search_text;

    public int getTotalArticlesFetched() {
        return this.totalArticlesFetched;
    }

    public void setTotalArticlesFetched(int totalArticlesFetched1) {
        this.totalArticlesFetched = totalArticlesFetched1;
    }

    public void addTotalArticlesFetched(int newNumArticlesFetched) {
        this.totalArticlesFetched += newNumArticlesFetched;
    }

    public int getTotalPagesFetched() {
        return this.totalPagesFetched;
    }

    public void setTotalPagesFetched(int totalPagesFetched1) {
        this.totalPagesFetched = totalPagesFetched1;
    }

    public void incrementTotalPagesFetched() {
        this.totalPagesFetched++;
    }

    public int getLastFetchedPageNum() {
        return this.lastFetchedPageNum;
    }

    public void setLastFetchedPageNum(int lastFetchedPageNum1) {
        this.lastFetchedPageNum = lastFetchedPageNum1;
    }

    public int getLastFetchedPage_NumArticlesInPage() {
        return this.lastFetchedPage_NumArticlesInPage;
    }

    public void setLastFetchedPage_NumArticlesInPage(int lastFetchedPage_NumArticlesInPage_in) {
        this.lastFetchedPage_NumArticlesInPage = lastFetchedPage_NumArticlesInPage_in;
    }

    public String getSearchText() {
        return this.search_text;
    }

    public void setSearchText(String search_text1) {
        this.search_text = search_text1;
    }
}
