package com.example.android_etpj.api;

import com.example.android_etpj.models.*;
import com.example.android_etpj.models.Class;
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

    Gson gson = new GsonBuilder()
            //.setDateFormat("yyyy-MM-dd")
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.12/try/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    //Feedback
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

    //Assigment
    @GET("assignment")
    Call<List<Assignment>> getAssignments();

    @GET("assignment")
    Call<List<Assignment>> searchAssignments(@Query("stringSearch") String stringSearch);

    @POST("assignment")
    Call<Boolean> addAssignment(@Body Assignment a);

    @DELETE("assignment")
    Call<Boolean> deleteAssignment(@Query("idClass") int idClass,
                                   @Query("idModule") int idModule);

    @PUT("assignment")
    Call<Boolean> editAssignment(@Body Assignment a);

    //Class
    @GET("class")
    Call<List<Class>> getClasses();

    //Trainer
    @GET("trainer")
    Call<List<Trainer>> getTrainers();

    //Enrollment
    @GET("enrollment")
    Call<List<Enrollment>> getEnrollment();

    @GET("enrollment")
    Call<List<Enrollment>> searchEnrollment(@Query("classId") int classId);

    @POST("enrollment")
    Call<Boolean> addEnrollment(@Query("classId") int classId,
                                @Query("username") String username);

    @PUT("enrollment")
    Call<Boolean> editEnrollment(@Query("classIdOld") int classIdOld,
                                 @Query("classIdNew") int classIdNew,
                                 @Query("username") String username);
    @DELETE("enrollment")
    Call<Boolean> deleteEnrollment(@Query("classId") int classId,
                                   @Query("username") String username);

    //Trainee
    @GET("trainee")
    Call<List<Trainee>> getTrainee();

    //CommentResult
    @GET("traineecomment")
    Call<List<CommentResult>> getCommentResult(@Query("classId") int classId,
                                           @Query("moduleId") int moduleId);
}