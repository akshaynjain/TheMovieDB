package jain.akshay.themoviedb.Service;

import java.util.LinkedHashMap;

import jain.akshay.themoviedb.Model.RootObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by akshay on 16/03/18.
 */

public interface ApiInterface {
    @GET("search/multi")
    Call<RootObject> getMovies(@QueryMap LinkedHashMap<String, String> params);
}
