package com.example.myapplication.api;

import android.content.Context;

import com.example.myapplication.R;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.IOException;

public class ApiClient {
    // כתובת הבסיס של OpenAI
    private static final String BASE_URL = "https://api.openai.com/";

    // שיטה שמחזירה מופע של OpenAiService עם Interceptor להוספת הכותרת
    public static OpenAiService getService(Context context) {
        // טוענים את המפתח מ-res/values/secrets.xml
        final String apiKey = context.getString(R.string.openai_api_key);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + apiKey)
                                .addHeader("Content-Type", "application/json")
                                .build();
                        return chain.proceed(req);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        return retrofit.create(OpenAiService.class);
    }
}
