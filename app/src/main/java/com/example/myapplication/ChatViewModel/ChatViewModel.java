package com.example.myapplication.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.OpenAiService;
import com.example.myapplication.model.ChatRequest;
import com.example.myapplication.model.ChatResponse;
import com.example.myapplication.model.Message;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<List<Message>> _messages = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Message>> messages = _messages;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public LiveData<Boolean> isLoading = _isLoading;

    /**
     * שולח הודעת משתמש ומטפל בתשובת ה-AI
     *
     * @param userText הטקסט שהמשתמש הקליד
     * @param ctx      הקשר (Context) לטעינת ApiClient
     */
    public void sendMessage(String userText, Context ctx) {
        // 1. התחלת טעינה
        _isLoading.setValue(true);

        // 2. הוספת הודעת המשתמש לרשימה
        List<Message> history = new ArrayList<>(_messages.getValue());
        history.add(new Message("user", userText));
        _messages.setValue(history);

        // 3. הכנת גוף הבקשה ל-OpenAI
        ChatRequest req = new ChatRequest(history);

        // 4. שליחת הבקשה ל-OpenAI
        OpenAiService svc = ApiClient.getService(ctx);
        svc.createChat(req).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                // 5. סיום טעינה
                _isLoading.postValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    Message aiMessage = response.body().choices.get(0).message;
                    List<Message> updated = new ArrayList<>(_messages.getValue());
                    updated.add(aiMessage);
                    _messages.postValue(updated);
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                // 5. סיום טעינה גם בשגיאה
                _isLoading.postValue(false);
                // כאן אפשר להוסיף טיפול בשגיאות או הודעה למשתמש
            }
        });
    }
}
