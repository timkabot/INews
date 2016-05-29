package com.example.kolesa2;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public  Gson gson = new GsonBuilder().create();
    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.i-news.kz/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    public String appId = "Ozaa5nic5oeph7eethok";
    public String appKey ="ushoh4ahpe8Aghahreel";
    public  Link intf = retrofit.create(Link.class);
    public Category[] s;
    public All_news all_news;
    public String[] ctgs,ctgs2;
    public Object k = this;
    HashMap<String,String> keys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView Categories = (ListView) findViewById(R.id.listView);
                Call<Category[]> call = intf.getCategories();
                call.enqueue(new Callback <Category[]>() {
                    @Override
                    public void onResponse(Call<Category[]> call, retrofit2.Response< Category[] > response) {
                        int count=0;
                        if(response.isSuccessful())
                        {
                            s = response.body();
                            ctgs = new String[s.length];
                            for(int i=0;i<s.length;i++)
                            {
                                ctgs[i] = new String(s[i].title);
                            }
                            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, ctgs);
                            Categories.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<Category[]> call, Throwable t) {
                        //Do something with failure
                    }
                }
                );
        Categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Object listItem = Categories.getItemAtPosition(i);
                System.out.println(listItem);
                News intf2 = retrofit.create(News.class);
                i++;
                String q = Integer.toString(i);
                Call<All_news> call2 = intf2.getNews(q);
                call2.enqueue(new Callback<All_news>() {
                    @Override
                    public void onResponse(Call<All_news> call, retrofit2.Response<All_news> response) {
                        if(response.isSuccessful())
                        {
                            all_news = response.body();
                            ctgs2 = new String[all_news.news.length];
                            for(int i=all_news.news.length-1,q=0;i>=0;i--)
                            {
                                System.out.println(all_news.news[i].title);
                                ctgs2[q]= new String(all_news.news[i].title);
                                System.out.println(all_news.news[i].date);
                                q++;
                            }
                            setContentView(R.layout.news_activity);
                            ArrayAdapter adapter2 = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, ctgs2);
                            final ListView Categories2 = (ListView) findViewById(R.id.listView2);
                            Categories2.setAdapter(adapter2);
                            Categories2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Object listItem2 = Categories2.getItemAtPosition(i);

                                    for(int j=0;j<all_news.news.length;j++)
                                    {
                                        if(all_news.news[j].title.equals(listItem2.toString()))
                                        {
                                            System.out.println(listItem+ " $$$$$ " + all_news.news[j].title);
                                            Uri address = Uri.parse(all_news.news[i].url);
                                            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                                            startActivity(openlinkIntent);
                                        }
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<All_news> call, Throwable t) {
                        System.out.println("FAIKS");
                    }
                });
            }
        });
    }
}
