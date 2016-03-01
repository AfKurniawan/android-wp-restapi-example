package com.tiarf.wprestapitest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
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

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewCompat.setTransitionName(findViewById(R.id.appBarLayout), "Name");

        // get view widgets
        final ImageView post_image = (ImageView) findViewById(R.id.post_photo);
        final TextView post_title = (TextView) findViewById(R.id.post_title);
        final TextView post_date = (TextView) findViewById(R.id.post_date);
        final TextView post_author = (TextView) findViewById(R.id.post_author);
        final TextView post_content = (TextView) findViewById(R.id.post_content);

        // Get the post_id
        Intent lastIntent = getIntent();
        int post_id = lastIntent.getIntExtra("post_id", 0);

        final FtiarService ftiarService = new RestAdapter.Builder()
                .setEndpoint(FtiarService.ENDPOINT)
                .build()
                .create(FtiarService.class);

        // Retreive the post by passing the post_id
        ftiarService.getPostAsync(post_id, new Callback<Post>() {

            @Override
            public void success(Post post, Response response) {

                String title = post.getTitle().getRendered();

                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                collapsingToolbarLayout.setTitle(title); // Set Toolbar title : Post title
                collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

                setTitle(title);
                post_title.setText(title);

                // Create share provider intent
                setShareIntent(createShareIntent(title, post.getLink() ));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_post, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // Return true to display menu
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private Intent createShareIntent( String post_title, String post_url ) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String sharedText = "Lisez l'article \"" + post_title + "\" à l'adresse suivante : " + post_url;
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                sharedText);
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
