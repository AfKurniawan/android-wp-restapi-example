package com.tiarf.wprestapitest;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface FtiarService {

    public static final String ENDPOINT = "http://ftiar.devbea.fr/wp-json/wp/v2";

    @GET("/posts")
    void getPostsAsync( Callback<List<Post>> callback); // Async

    @GET("/media/{media_id}")
    void getMediaAsync(@Path("media_id") int media_id, Callback<Media> callback); // Async

}