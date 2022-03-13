package com.evanemran.animex.manager;

import android.content.Context;

import com.evanemran.animex.listeners.ResponseListener;
import com.evanemran.animex.models.AnimeResponse;
import com.evanemran.animex.models.ApiResponse;
import com.evanemran.animex.models.EpisodesResponse;
import com.evanemran.animex.models.SongsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.aniapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getAnime(ResponseListener<AnimeResponse> listener){
        GetAnime getAnime = retrofit.create(GetAnime.class);
        Call<ApiResponse<AnimeResponse>> call = getAnime.callAnime();
        call.enqueue(new Callback<ApiResponse<AnimeResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<AnimeResponse>> call, Response<ApiResponse<AnimeResponse>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body().getData(), response.message());
            }

            @Override
            public void onFailure(Call<ApiResponse<AnimeResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getSongs(ResponseListener<SongsResponse> listener){
        GetSong getSong = retrofit.create(GetSong.class);
        Call<ApiResponse<SongsResponse>> call = getSong.callSong();
        call.enqueue(new Callback<ApiResponse<SongsResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<SongsResponse>> call, Response<ApiResponse<SongsResponse>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body().getData(), response.message());
            }

            @Override
            public void onFailure(Call<ApiResponse<SongsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getEpisodes(ResponseListener<EpisodesResponse> listener, int id){
        GetEpisodes getEpisodes = retrofit.create(GetEpisodes.class);
        Call<ApiResponse<EpisodesResponse>> call = getEpisodes.callEpisodes(id, "it", true);
        call.enqueue(new Callback<ApiResponse<EpisodesResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<EpisodesResponse>> call, Response<ApiResponse<EpisodesResponse>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body().getData(), response.message());
            }

            @Override
            public void onFailure(Call<ApiResponse<EpisodesResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


    private interface GetAnime{
        @GET("v1/anime")
        Call<ApiResponse<AnimeResponse>> callAnime();
    }

    private interface GetSong{
        @GET("v1/song")
        Call<ApiResponse<SongsResponse>> callSong();
    }

    private interface GetEpisodes{
        @GET("v1/episode")
        Call<ApiResponse<EpisodesResponse>> callEpisodes(
                @Query("anime_id ") int anime_id,
                @Query("locale") String locale,
                @Query("is_dub") boolean is_dub
        );
    }
}
