package hu.bence.flyhigh.network;

import java.util.List;

import hu.bence.flyhigh.model.JegyAdatokModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JegyAdatokApi {

    @GET("api/Jegyadatok")
    Call<List<JegyAdatokModel>> getJegyadatok();
}
