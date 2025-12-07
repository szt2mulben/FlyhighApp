package hu.bence.flyhigh.network;

import java.util.List;

import hu.bence.flyhigh.model.JegyModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JegykezelesApi {


    @GET("api/Jegykezeles")
    Call<List<JegyModel>> getJegyek();
}
