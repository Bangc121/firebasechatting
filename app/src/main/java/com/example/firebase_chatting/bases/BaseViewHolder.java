package com.example.firebase_chatting.bases;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
