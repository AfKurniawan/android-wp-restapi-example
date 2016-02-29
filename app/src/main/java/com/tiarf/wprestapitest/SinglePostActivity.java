package com.tiarf.wprestapitest;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SinglePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageView post_image = (ImageView) findViewById(R.id.post_photo);
        final TextView post_title = (TextView) findViewById(R.id.post_title);
        final TextView post_date = (TextView) findViewById(R.id.post_date);

        Intent lastIntent = getIntent();
        int post_id = lastIntent.getIntExtra("post_id", 0);

        // Get Media Object with a call to the REST API
        final FtiarService ftiarService = new RestAdapter.Builder()
                .setEndpoint(FtiarService.ENDPOINT)
                .build()
                .create(FtiarService.class);

        ftiarService.getPostAsync(post_id, new Callback<Post>() {

            @Override
            public void success(Post post, Response response) {
                // Use Picasso lib to display an Image based on an URL

                String title = post.getTitle().getRendered();
                setTitle(title);
                post_title.setText( title );

                post_date.setText(post.getI18nFormatedDate(post.getDate(), "dd MMMM yyyy", Locale.FRANCE));

                ftiarService.getMediaAsync(post.getFeatured_media(), new Callback<Media>() {

                    @Override
                    public void success(Media media, Response response) {
                        // Use Picasso lib to display an Image based on an URL
                        Picasso.with(getBaseContext()).load(media.getMedia_details().getSizes().getLarge().getSource_url())
                                .into(post_image);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });


    }

}
