package com.example.android_etpj.api;

import com.example.android_etpj.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.Class;
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
            .baseUrl("http://192.168.0.103/systemfeedback/api/")
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

    //Class
    @GET("class")
    Call<List<com.example.android_etpj.models.Class>> getClasses();

    @GET("class/{id}")
    Call<com.example.android_etpj.models.Class> getClassById(@Path("id") int id);

    @GET("class")
    Call<List<com.example.android_etpj.models.Class>> getClassesByTrainer(@Query("idTrainer") String idTrainer);

    @GET("class")
    Call<List<com.example.android_etpj.models.Class>> getClassesByTrainee(@Query("idTrainee") String idTrainee);

    @POST("class")
    Call<Boolean> addClass(@Body com.example.android_etpj.models.Class cl);

    @PUT("class")
    Call<Boolean> editClass(@Body com.example.android_etpj.models.Class cl);

    @DELETE("class/{id}")
    Call<Boolean> deleteClass(@Path("id") int id);


    //Question
    @GET("question")
    Call<List<Question>> getQuestions();

    @GET("question")
    Call<List<Question>> getQuestionByIdTopic(@Query("idTopic") int idTopic);

    @GET("question/{id}")
    Call<Question> getQuestionById(@Path("id") int id);

    @POST("question")
    Call<Boolean> addQuestion(@Body Question qs);

    @PUT("question")
    Call<Boolean> editQuestion(@Body Question qs);

    @DELETE("question/{id}")
    Call<Boolean> deleteQuestion(@Path("id") int id);

    @GET("question")
    Call<Integer> checkQuestionUsed(@Query("idUsed") int idUsed);

    //Topic

    @GET("topic")
    Call<List<Topic>> getTopics();

    @GET("topic/{id}")
    Call<Topic> getTopicById(@Path("id") int id);


    //Answer
    @GET("answer")
    Call<List<Answer>> getAnswersByClassModule(@Query("idClass") int idClass,@Query("idModule") int idModule);

    @GET("answer")
    Call<List<Answer>> getAnswersByIdQuestion(@Query("idQuestion") int idQuestion);

    @GET("answer")
    Call<List<Answer>> getAnswersByClassModuleTopic(@Query("idTopic") int idTopic, @Query("idClass") int idClass,@Query("idModule") int idModule);

    //TopicAnswers
    @GET("topicanswers")
    Call<List<TopicAnswers>> getTopicAnswersByClassModule(@Query("idClass") int idClass,@Query("idModule") int idModule);

    //Enrollment
    @GET("enrollment")
    Call<List<Enrollment>> getEnrollmentByIdClass(@Query("idClass") int idClass);

    //Trainee
    @GET("trainee")
    Call<Trainee> getTraineeByUsername(@Query("username") String username);
}
