package com.example.android_etpj.api;

import com.example.android_etpj.models.Feedback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.0.109/systemfeedback/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("feedback")
    Call<List<Feedback>> getFeedbacks();

    @GET("feedback/{id}")
    Call<Feedback> getFeedbackById(@Path("id") int id);

    @POST("feedback")
    Call<Boolean> addFeedback(@Body Feedback fb);

    @PUT("feedback")
    Call<Boolean> editFeedback(@Body Feedback fb);

    @DELETE("feedback/{id}")
    Call<Boolean> deleteFeedback(@Path("id") int id);

}
