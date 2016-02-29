package com.tiarf.wprestapitest.Services;

import com.tiarf.wprestapitest.Models.Media;
import com.tiarf.wprestapitest.Models.Post;
import com.tiarf.wprestapitest.Models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface FtiarService {

    public static final String ENDPOINT = "http://ftiar.devbea.fr/wp-json/wp/v2";

    @GET("/posts")
    void getPostsAsync( Callback<List<Post>> callback);

    @GET("/posts/{post_id}")
    void getPostAsync(@Path("post_id") int post_id, Callback<Post> callback); // Async

    @GET("/media/{media_id}")
    void getMediaAsync(@Path("media_id") int media_id, Callback<Media> callback); // Async

    @GET("/users/{user_id}")
    void getUserAsync(@Path("user_id") int user_id, Callback<User> callback); // Async

}