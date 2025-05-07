package com.example.myapplication.api;
import static android.provider.Settings.System.getString;

import com.example.myapplication.BuildConfig;

import com.example.myapplication.R;
import com.example.myapplication.model.ChatRequest;
import com.example.myapplication.model.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface OpenAiService {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    Call<ChatResponse> createChat(@Body ChatRequest body);
}
