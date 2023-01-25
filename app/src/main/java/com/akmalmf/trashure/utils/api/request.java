package com.akmalmf.trashure.utils.api;

/**
 * Created by Coder Newbie 19/06/19
 */

import com.akmalmf.trashure.json.ResponseAuth;
import com.akmalmf.trashure.json.ResponseForget;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface request {
    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseAuth> login(@Field("email") String email,
                            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("auth/register")
    Call<ResponseAuth> register(@Field("nama") String nama,
                             @Field("email") String email,
                             @Field("nohp") String nohp,
                             @Field("password") String password,
                             @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("auth/forget")
    Call<ResponseForget> forget(@Field("email") String email);


}
