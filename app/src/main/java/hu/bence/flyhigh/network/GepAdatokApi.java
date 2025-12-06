package hu.bence.flyhigh.network;

import java.util.List;

import hu.bence.flyhigh.model.GepAdatokModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GepAdatokApi {

    @GET("api/Gepadatok")
    Call<List<GepAdatokModel>> getOsszesGep();
}
