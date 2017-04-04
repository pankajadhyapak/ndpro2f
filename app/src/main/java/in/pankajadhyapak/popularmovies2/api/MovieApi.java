package in.pankajadhyapak.popularmovies2.api;


import in.pankajadhyapak.popularmovies2.models.AllMovies;
import in.pankajadhyapak.popularmovies2.models.AllReviews;
import in.pankajadhyapak.popularmovies2.models.AllTrailers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("top_rated")
    Call<AllMovies> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") String page);

    @GET("popular")
    Call<AllMovies> getPopularMovies(@Query("api_key") String apiKey, @Query("page") String page);

    @GET("{id}/videos")
    Call<AllTrailers> getTrailers(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("{id}/reviews")
    Call<AllReviews> getReviews(@Path("id") int movieId, @Query("api_key") String apiKey);

}
