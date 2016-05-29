package com.example.kolesa2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Timkabo on 30.05.2016.
 */
public interface News {
    @GET("news/search?appId=Ozaa5nic5oeph7eethok&appKey=ushoh4ahpe8Aghahreel&version=1")
    Call<All_news> getNews(@Query("query[cat_id]") String id);
}
