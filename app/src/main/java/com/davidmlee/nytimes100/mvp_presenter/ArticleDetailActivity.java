package com.davidmlee.nytimes100.mvp_presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.davidmlee.nytimes100.R;
//import com.davidmlee.nytimes100.mvp_presenter.MyApp;
import com.davidmlee.nytimes100.util.RoundedTransformation;
import com.davidmlee.nytimes100.util.Util;
import com.squareup.picasso.Picasso;

import static com.davidmlee.nytimes100.mvp_model.Contants.BASE_IMAGE_URL;

/**
 * MainActivity of the app
 */
public class ArticleDetailActivity extends AppCompatActivity {
    private static final String TAG = ArticleDetailActivity.class.getSimpleName();

    private ScrollView detailScrollView;
    private ImageView imageView;
    private TextView tvOverviewDetail;

    @Override
    @SuppressWarnings({"deprecation", "NewApi"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        getSupportActionBar().hide();
        imageView = (ImageView)findViewById(R.id.poster_image);
        Picasso.with(this)
                .load(BASE_IMAGE_URL + ArticleDetailController.getDetailEntity().getPosterPath())
                .transform(new RoundedTransformation(20, 2))
                .error(R.drawable.poster_not_found)
                .into(imageView);
        this.tvOverviewDetail = (TextView)findViewById(R.id.tv_overview_detail);
        this.tvOverviewDetail.setText(ArticleDetailController.getDetailEntity().getOverview());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToShare = "Share with you:"
                        + "\n\"" + ArticleDetailController.getDetailEntity().getTitle() + "\""
                        + "\n" + ArticleDetailController.getDetailEntity().getWebUrl();

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/html");
                myIntent.putExtra(Intent.EXTRA_SUBJECT, ArticleDetailController.getDetailEntity().getTitle());
                myIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
                startActivity(Intent.createChooser(myIntent, MyApp.getStrRes(R.string.label_share)));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(MyApp.getStrRes(R.string.app_name));
            alertDialogBuilder
                    .setMessage(MyApp.getStrRes(R.string.label_version) + "  " + Util.getAppVersionString())
                    .setCancelable(true)
                    .setPositiveButton(MyApp.getStrRes(R.string.label_done),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
