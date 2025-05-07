package com.example.myapplication.ui;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Message;

public class ChatAdapter extends ListAdapter<Message, ChatAdapter.VH> {

    public ChatAdapter() {
        super(new DiffUtil.ItemCallback<Message>() {
            @Override
            public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
                return oldItem.role.equals(newItem.role)
                        && oldItem.content.equals(newItem.content);
            }
        });
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Message msg = getItem(position);
        holder.tvMessage.setText(msg.content);

        if ("user".equals(msg.role)) {
            holder.tvMessage.setBackgroundResource(R.drawable.bg_user_bubble);
            // ודאי שהבועה מיושרת לימין
            ((FrameLayout.LayoutParams)holder.tvMessage.getLayoutParams())
                    .gravity = Gravity.END;
        } else {
            holder.tvMessage.setBackgroundResource(R.drawable.bg_ai_bubble);
            ((FrameLayout.LayoutParams)holder.tvMessage.getLayoutParams())
                    .gravity = Gravity.START;
        }
    }


    public static class VH extends RecyclerView.ViewHolder {
        TextView tvMessage;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
