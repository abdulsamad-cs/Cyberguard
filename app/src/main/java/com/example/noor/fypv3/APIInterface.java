package com.example.noor.fypv3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @POST("register.php")
    Call<User> performRegistration(@Query("username") String username, @Query("email") String email, @Query("password") String password);
    @POST("login.php")
    Call<User> performUserLogin(@Query("email") String email, @Query("password") String password);
    @POST("verify.php")
    Call<User>verifyChildStatus(@Query("parent_email") String parentEmail, @Query("child_email") String childEmail,@Query("fullname") String fullname,@Query("handle") String handle,@Query("age")String age);
    @POST("predict/{data}")
    Call<ResponseClass>getBullyStatus(@Path("data")String data);

    @POST("predictAll/{data}")
    Call<ResponseClass>getAllBullyStatus(@Path("data")List<String> data);

    @POST("fetchRecords.php")
    Call<List<Child>>fetchRecords(@Query("parent_email")String parent_email);

}
