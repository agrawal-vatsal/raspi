package video.streaming.settingsactivity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitClientService {
    //    @Keep
    @GET("/data")
    Call<APIData> getData();

    @FormUrlEncoded
    @POST("/send_data")
    Call<Void> sendData(@Field("value") Integer value);

}
