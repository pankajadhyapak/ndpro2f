package in.pankajadhyapak.popularmovies2.api;

import java.io.IOException;

import in.pankajadhyapak.popularmovies2.App;
import in.pankajadhyapak.popularmovies2.BuildConfig;
import in.pankajadhyapak.popularmovies2.Constants;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pankaj on 04/04/17.
 */

public class RetroFit {

    private static final String TAG = "RetroFit getInstance";

    public static Retrofit getInstance() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // For logging Requests and Response ( only for Development )
        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        //Caching responses
        httpClient.cache(new Cache(App.getInstance().getCacheDir(), 5 * 1024 * 1024)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (App.hasNetwork()) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                });

        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
