package hu.bence.flyhigh.network;

import java.util.List;

import hu.bence.flyhigh.model.LoginRequest;
import hu.bence.flyhigh.model.LoginTokenResponse;
import hu.bence.flyhigh.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UseradatokApi {

    @POST("api/Useradatok/login")
    Call<LoginTokenResponse> login(@Body LoginRequest request);

}
