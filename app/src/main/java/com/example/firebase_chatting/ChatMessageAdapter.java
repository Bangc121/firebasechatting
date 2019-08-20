package com.example.firebase_chatting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chatting.bases.BaseViewHolder;
import com.example.firebase_chatting.bases.Constants;

import java.util.ArrayList;


/**
 * Created by KimJeongHwan on 2018-11-19.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
    private static final int NOTICE_VIEW = 0;
    private static final int SELF_VIEW = 1;
    private static final int RECEIVED_VIEW = 2;

    private ArrayList<ChatMessageItem> chatMessageItems;
    private Context mContext;

    ChatMessageAdapter(ArrayList<ChatMessageItem> chatMessageItems, Context mContext){
        this.chatMessageItems = chatMessageItems;
        this.mContext = mContext;

        notifyDataSetChanged();
    }

    class ViewHolder extends BaseViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class NoticeHolder extends ViewHolder {
        private TextView chatNotice;
        NoticeHolder(View itemView) {
            super(itemView);
            chatNotice = itemView.findViewById(R.id.chatNotice);
        }
    }

    public class SelfHolder extends ViewHolder {
        private TextView selfName;
        private TextView selfContents;
        SelfHolder(View itemView) {
            super(itemView);
            selfName = itemView.findViewById(R.id.selfName);
            selfContents = itemView.findViewById(R.id.selfContents);
        }
    }

    public class ReceiveHolder extends ViewHolder {
        private TextView receiveName;
        private TextView receiveContents;
        ReceiveHolder(View itemView) {
            super(itemView);
            receiveName = itemView.findViewById(R.id.receiveName);
            receiveContents = itemView.findViewById(R.id.receiveContents);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageItem chatMessageItem = chatMessageItems.get(position);
        String messageType = chatMessageItem.getMessageType();
        if (messageType.equals(Constants.MESSAGE_TYPE_NOTICE)) {
            return NOTICE_VIEW;
        } else if (messageType.equals(Constants.MESSAGE_TYPE_SELF)) {
            return SELF_VIEW;
        } else if (messageType.equals(Constants.MESSAGE_TYPE_RECEIVE)) {
            return RECEIVED_VIEW;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case NOTICE_VIEW:
                view = inflater.inflate(R.layout.recyclerview_chat_notice, parent, false);
                return new NoticeHolder(view);
            case SELF_VIEW:
                view = inflater.inflate(R.layout.recyclerview_chat_self, parent, false);
                return new SelfHolder(view);
            case RECEIVED_VIEW:
                view = inflater.inflate(R.layout.recyclerview_chat_receive, parent, false);
                return new ReceiveHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageAdapter.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Log.d("ChatMessageAdapter", "onBindViewHolder position(" + position + ")");
        if (viewType == NOTICE_VIEW) {
            NoticeHolder noticeHolder = (NoticeHolder) holder;
            noticeHolder.chatNotice.setText(chatMessageItems.get(position).getNotice());
        }
        else if (viewType == SELF_VIEW) {
            SelfHolder selfHolder = (SelfHolder) holder;
            selfHolder.selfName.setText(chatMessageItems.get(position).getName());
            selfHolder.selfContents.setText(chatMessageItems.get(position).getContents());
        } else if (viewType == RECEIVED_VIEW) {
            ReceiveHolder receiveHolder = (ReceiveHolder) holder;
            receiveHolder.receiveName.setText(chatMessageItems.get(position).getName());
            receiveHolder.receiveContents.setText(chatMessageItems.get(position).getContents());
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageItems.size();
    }

}
