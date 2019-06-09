/*
 * Copyright (C) 2014 Francesco Azzola
 *  Surviving with Android (http://www.survivingwithandroid.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.davidmlee.nytimes100.mvp_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidmlee.nytimes100.R;
import com.davidmlee.nytimes100.mvp_presenter.MainController;
import com.davidmlee.nytimes100.mvp_presenter.ArticleDetailController;
import com.davidmlee.nytimes100.mvp_presenter.MyApp;
import com.davidmlee.nytimes100.mvp_model.ListSummaryEntity;
import com.davidmlee.nytimes100.util.RoundedTransformation;
import com.davidmlee.nytimes100.util.Util;

import java.util.List;

import com.squareup.picasso.Picasso;
import static com.davidmlee.nytimes100.mvp_model.Contants.BASE_IMAGE_URL;


public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private Context myActivityContext;
    private static List<ListSummaryEntity> articleEntityList;

    public ArticleListAdapter(Context activityContext, List<ListSummaryEntity> articleEntityList) {
        myActivityContext = activityContext;
        ArticleListAdapter.articleEntityList = articleEntityList;
    }

    @Override
    public int getItemCount() {
        return articleEntityList.size();
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        ListSummaryEntity fe = articleEntityList.get(position);
        holder.tvTitle.setText(fe.getTitle());
        holder.tvOverview.setText(fe.getOverview());
        // Should we fetch the next page?
        if (position == articleEntityList.size() - 1) {
            MainController.searchArticlesNextPage();
        }
        Picasso.with(myActivityContext)
                .load(BASE_IMAGE_URL + fe.getPosterPath())
                .transform(new RoundedTransformation(20, 2))
                .error(R.drawable.poster_not_found)
                .into(holder.ivPoster);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_layout, viewGroup, false);
            return new ArticleViewHolder(itemView);
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        protected String id;
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvOverview;

        ArticleViewHolder(View itemView) {
            super(itemView);
            this.ivPoster =  (ImageView)itemView.findViewById(R.id.poster_image);
            this.tvTitle =  (TextView)itemView.findViewById(R.id.tv_title);
            this.tvOverview = (TextView)itemView.findViewById(R.id.tv_overview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int view_item_osition = getLayoutPosition();
                    final int data_list_position = getAdapterPosition();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            ArticleDetailController.startDetailView(data_list_position);
                        }
                    }.start();
                }
            });
        }
    }
}
