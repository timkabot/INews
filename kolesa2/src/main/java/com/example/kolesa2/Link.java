package com.example.kolesa2;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;

/**
 * Created by Timkabo on 29.05.2016.
 */
public interface Link {
    @GET("category/get-all?appId=Ozaa5nic5oeph7eethok&appKey=ushoh4ahpe8Aghahreel&version=1")
    Call<Category[]>  getCategories();
}
