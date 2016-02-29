package com.tiarf.wprestapitest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tiarf.wprestapitest.Models.Media;
import com.tiarf.wprestapitest.Models.Post;
import com.tiarf.wprestapitest.Models.User;
import com.tiarf.wprestapitest.Services.FtiarService;

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
        final TextView post_author = (TextView) findViewById(R.id.post_author);
        final TextView post_content = (TextView) findViewById(R.id.post_content);

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
                post_title.setText(title);
                String content = Html.fromHtml(post.getContent().getRendered()).toString();
                post_content.setText(content);

                post_date.setText(post.getI18nFormatedDate(post.getDate(), "dd MMMM yyyy", Locale.FRANCE));

                ftiarService.getUserAsync(post.getAuthor(), new Callback<User>() {

                    @Override
                    public void success(User author, Response response) {
                        // Use Picasso lib to display an Image based on an URL
                        post_author.setText("Auteur : " + author.getName());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });

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
