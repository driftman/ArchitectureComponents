package com.driftman.fuckingandroid.log.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by abk on 30/07/2018.
 */

public interface LogAPI {

    @POST("/_bulk")
    Call<Object> sendLog(@Header("Content-Type") String contentType, @Body String bulk);

}
