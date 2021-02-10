package com.example.shivaniprac_hardyinfotech.API;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiServiceCreator {

    public static ApiInterface createService(String orderBy) {
        OkHttpClient okHttpClient = createOkHttpClient(orderBy);
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstants.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build();

        return retrofit.create(ApiInterface.class);
    }

    @NonNull
    private static Gson createGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    private static OkHttpClient createOkHttpClient(String type) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        HttpUrl url = chain.request().url().newBuilder()
                                .build();
                        Request newRequest = chain.request().newBuilder().url(url).build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS).build();
    }
}
