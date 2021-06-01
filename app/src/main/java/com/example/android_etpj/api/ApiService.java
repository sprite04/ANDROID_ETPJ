package com.example.android_etpj.api;

import com.example.android_etpj.models.*;
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
import retrofit2.http.Query;

public interface ApiService {

    Gson gson=new GsonBuilder()
            //.setDateFormat("yyyy-MM-dd")
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.5.12/systemfeedback/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    //Feedback
    @GET("feedback")
    Call<List<Feedback>> getFeedbacks();

    @GET("feedback/{id}")
    Call<Feedback> getFeedbackById(@Path("id") int id);

    @POST("feedback")
    Call<Boolean> addFeedback(@Body FeedbackQuestion feedbackQuestion);

    @PUT("feedback")
    Call<Boolean> editFeedback(@Body FeedbackQuestion feedbackQuestion);

    @DELETE("feedback/{id}")
    Call<Boolean> deleteFeedback(@Path("id") int id);

    @GET("feedback")
    Call<Integer> checkFeedbackUsed(@Query("idUsed") int idUsed);

    //Module
    @GET("module")
    Call<List<Module>> getModules();

    @GET("module")
    Call<Boolean> checkModuleUsed(@Query("idUsed") int idUsed);

    @DELETE("module/{id}")
    Call<Boolean> deleteModule(@Path("id") int id);

    @POST("module")
    Call<Boolean> addModule(@Body Module m);

    @PUT("module")
    Call<Boolean> editModule(@Body Module m);


    //Admin
    @GET("admin")
    Call<List<Admin>> getAdmins();

    //Assignment
    @GET("assignment")
    Call<List<Assignment>> getAssignmentsByTrainee(@Query("idTrainee") String idTrainee);

    //Topic
    @GET("topic")
    Call<List<Topic>> getTopics();

    @GET("topic")
    Call<List<Topic>> getTopicsByFeedback(@Query("idFeedback") int idFeedback);


    //TypeFeedback
    @GET("typefeedback")
    Call<List<TypeFeedback>> getTypeFeedbacks();

    //Answer
    @GET("answer")
    Call<Boolean> checkAnswerUsed(@Query("idClass") int idClass, @Query("idModule") int idModule, @Query("idTrainee") String idTrainee);

    @POST("answer")
    Call<Boolean> addAnswers(@Body List<Answer> answers);


    //TopicAnswers
    @GET("topicanswers")
    Call<List<TopicAnswers>>getTopicAnswersByClassModule(@Query("idClass")int idClass, @Query("idModule") int idModule);

    //TraineeComment
    @POST("traineecomment")
    Call<Boolean> addTraineeComment(@Body Trainee_Comment traineeComment);

}
