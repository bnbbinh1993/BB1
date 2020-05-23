package com.bnb.binh.skyintertainment.notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAXLqRJOU:APA91bH_Dog_aScay27Y5kXI1Ulbgzp9uZU2DsxouETYHVLSUhxVd9g-oLMSXdFthvADCRBXxGPQoQpeLM9653u44FGY_KPoDoasOnesW0NeKpzIWB4oWCJsAXsvrWx5iRSARZw2zRfR"
    })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
