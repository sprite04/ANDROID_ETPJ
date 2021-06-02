package com.example.android_etpj.api;

import com.example.android_etpj.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    //Trainee
    @GET("trainee")
    Call<Trainee> getTraineeByUsername(@Query("username") String username);

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

    /*@POST("answer")
    Call<Boolean> addAnswers(@Body Review review);*/


    @POST("answer")
    Call<Boolean> addAnswers(@Body List<Answer> answers);

    @POST("answer")
    Call<Boolean> addAnswer(@Body Answer answer);






    //TopicAnswers
    @GET("topicanswers")
    Call<List<TopicAnswers>>getTopicAnswersByClassModule(@Query("idClass")int idClass, @Query("idModule") int idModule);

    //TraineeComment
    @POST("traineecomment")
    Call<Boolean> addTraineeComment(@Body Trainee_Comment traineeComment);

    @GET("traineecomment")
    Call<Boolean> checkCommentUsed(@Query("idClass") int idClass, @Query("idModule") int idModule, @Query("idTrainee") String idTrainee);


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

    //Enrollment
    @GET("enrollment")
    Call<List<Enrollment>> getEnrollmentByIdClass(@Query("idClass") int idClass);

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
}
