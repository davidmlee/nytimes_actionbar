package com.davidmlee.nytimes100.mvp_model;

//import android.icu.text.SimpleDateFormat;
import android.icu.text.SimpleDateFormat;
import android.text.format.DateFormat;

import com.davidmlee.kata.nysearch100.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

/**
 * ListSummaryEntity
 */
public class ListSummaryEntity {

    private String title;
    private String webUrl;
    private String overview;
    private String posterPath;

    private ListSummaryEntity() {
    }

    public static ListSummaryEntity populateFetchableResource(JSONObject jsonEntry) {
        ListSummaryEntity fe = new ListSummaryEntity();
        JSONObject headline = jsonEntry.optJSONObject("headline");
        String headline_main;
        if (headline != null && headline.length() > 0) {
            headline_main = headline.optString("main");
            if (headline != null) {
                fe.setTitle(headline_main);
            }
        }
        fe.setOverview(Util.getString(jsonEntry, "lead_paragraph", ""));
        fe.setWebUrl(Util.getString(jsonEntry, "web_url", ""));

        JSONArray multimediaAry = jsonEntry.optJSONArray("multimedia");
        JSONObject mediaEntry;
        String mediaType;
        String mediaImageUrl;
        if (multimediaAry != null && multimediaAry.length() > 0) {
            for (int i = 0; i < multimediaAry.length(); i++) {
                mediaEntry = multimediaAry.optJSONObject(i);
                if (mediaEntry != null && mediaEntry.length() > 0) {
                    mediaType = mediaEntry.optString("type");
                    mediaImageUrl = mediaEntry.optString("url");
                    if (mediaType != null && mediaType != "" && mediaImageUrl != null && mediaImageUrl != "") {
                        if (mediaType.equalsIgnoreCase("image")) {
                            fe.setPosterPath(mediaImageUrl);
                            break;
                        }
                    }
                }
            }
        }
        return fe;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return this.overview;
    }

    private void setOverview(String overview) {
        this.overview = overview;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    private void setWebUrl(String webUrl_in) {
        this.webUrl = webUrl_in;
    }

    public String getPosterPath() {
        return posterPath;
    }

    private void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
