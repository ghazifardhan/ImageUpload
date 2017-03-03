package com.app.ghazi.imagepicker;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Ghazi on 02/03/2017.
 */

public interface ApiService {

    @Multipart
    @POST("/productsmedia")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("product_id") RequestBody product_id, @Part("title") RequestBody title, @Part("main_position") RequestBody main_position, @Part("status") RequestBody status);
}
