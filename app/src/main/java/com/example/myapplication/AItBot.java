package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.ChatAdapter;
import com.example.myapplication.viewmodel.ChatViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AItBot extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ait_bot);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
