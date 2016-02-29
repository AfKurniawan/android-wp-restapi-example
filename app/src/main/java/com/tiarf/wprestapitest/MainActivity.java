package com.tiarf.wprestapitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FtiarService ftiarService = new RestAdapter.Builder()
                .setEndpoint(FtiarService.ENDPOINT)
                .build()
                .create(FtiarService.class);

        ftiarService.getPostsAsync(new Callback<List<Post>>() {
            @Override
            public void success(List<Post> posts, Response response) {
                System.out.println("Success");
                showPosts(posts);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("Fail");
                System.out.println(error);
            }
        });

    }

    public void showPosts(List<Post> posts) {
        Toast.makeText(this, "nombre d'articles : " + posts.size(), Toast.LENGTH_SHORT).show();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ArchivePostAdaptater adapter = new ArchivePostAdaptater( posts, getBaseContext() );
        mRecyclerView.setAdapter(adapter);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
