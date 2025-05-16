package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.ChatAdapter;
import com.example.myapplication.viewmodel.ChatViewModel;
import com.google.android.material.textfield.TextInputEditText;

import androidx.activity.EdgeToEdge;

public class AItBot extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // שלב 3: התאמת המסך לגובה המקלדת (לפני setContentView)
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        );
        ImageView backntn2=findViewById(R.id.ReturnBtn2);

        // אפשרות להמשיך להשתמש ב-EdgeToEdge אם רוצים
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_ait_bot);

        // קישור רכיבי ה-UI
        RecyclerView rvChat = findViewById(R.id.rvChat);
        TextInputEditText etMessage = findViewById(R.id.etMessage);
        ImageButton btnSend = findViewById(R.id.btnSend);

        // הגדרת ה-Adapter וה-LayoutManager
        ChatAdapter adapter = new ChatAdapter();
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(adapter);

        // השגת ה-ViewModel
        ChatViewModel vm = new ViewModelProvider(this).get(ChatViewModel.class);

        // מאזינים לשינויים ברשימת ההודעות
        vm.messages.observe(this, messages -> {
            adapter.submitList(messages);
            rvChat.scrollToPosition(messages.size() - 1);
        });
        

        // לחצן השליחה שולח את הטקסט ל-ViewModel
        btnSend.setOnClickListener(v -> {
            String text = etMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                vm.sendMessage(text, AItBot.this);
                etMessage.setText("");
            }
        });

    }
}
