package com.example.RetrofitJava.Service;

import com.example.RetrofitJava.Endpoint.Endpoint;
import com.example.RetrofitJava.Item.SemuabukuItem;
import com.example.RetrofitJava.Response.BukuResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BukuService {
    @FormUrlEncoded
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST(Endpoint.API_CREATE_BUKU)
    Call<BukuResponse> apiCreateBuku(@Field("judul") String judul);

    @GET(Endpoint.API_READ_BUKU)
    Call<BukuResponse<List<SemuabukuItem>>> apiReadBuku();

    @FormUrlEncoded
    @POST(Endpoint.API_UPDATE_BUKU)
    Call<BukuResponse> apiUpdateBuku(
            @Field("id") String id,
            @Field("name") String name
    );

    @GET(Endpoint.API_DELETE_BUKU+"{id}")
    Call<BukuResponse> apiDeleteBuku(@Path("id") String id);
}
