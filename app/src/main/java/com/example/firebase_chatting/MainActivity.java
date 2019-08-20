package com.example.firebase_chatting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static com.example.firebase_chatting.bases.Constants.MESSAGE_TYPE_NOTICE;
import static com.example.firebase_chatting.bases.Constants.MESSAGE_TYPE_RECEIVE;
import static com.example.firebase_chatting.bases.Constants.MESSAGE_TYPE_SELF;

public class MainActivity extends AppCompatActivity {
    RecyclerView chat_recyclerview;
    ArrayList<ChatMessageItem> chatMessageItems;
    AppCompatEditText Message_EditText;
    AppCompatButton Message_Send;
    String user_name;
    DatabaseReference reference;
    ChatMessageItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chat_recyclerview = findViewById(R.id.chat_recyclerview);
        chatMessageItems = new ArrayList<>();
        Message_EditText = findViewById(R.id.Message_EditText);
        Message_Send = findViewById(R.id.Message_Send);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("message");

        reference.setValue("Hello, World!");

        user_name = "Guest " + new Random().nextInt(1000);
        getUser();

        // 메세지버튼클릭시 발생하는 이벤트
        Message_Send.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();

                String key = reference.push().getKey();
                reference.updateChildren(map);

                DatabaseReference root = reference.child(key);

                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("name", user_name);
                objectMap.put("text", Message_EditText.getText().toString());

                root.updateChildren(objectMap);
                Message_EditText.setText("");
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                chatConversation(dataSnapshot);
            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void chatConversation(DataSnapshot dataSnapshot) {
        item = new ChatMessageItem();
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            String name = (String) ((DataSnapshot) i.next()).getValue();
            String content = (String) ((DataSnapshot) i.next()).getValue();
            if (name.equals(user_name)){
                item.setMessageType(MESSAGE_TYPE_SELF);
            }else {
                item.setMessageType(MESSAGE_TYPE_RECEIVE);
            }
            item.setName(name);
            item.setContents(content);
            chatMessageItems.add(item);
            initLayout();  // 리사이클러뷰를 초기화한다.
        }
    }

    private void getUser(){
        Log.e("name", user_name);
        item = new ChatMessageItem();
        item.setMessageType(MESSAGE_TYPE_NOTICE);
        item.setNotice(user_name+"님이 입장하였습니다.");
        chatMessageItems.add(item);
        initLayout();  // 리사이클러뷰를 초기화한다.
    }

    /**
     * 채팅레이아웃 초기화
     */
    private void initLayout(){
        chat_recyclerview.setAdapter(new ChatMessageAdapter(chatMessageItems, getApplicationContext()));
        chat_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chat_recyclerview.setItemAnimator(new DefaultItemAnimator());
        chat_recyclerview.scrollToPosition(new ChatMessageAdapter(chatMessageItems, getApplicationContext()).getItemCount()-1);
    }
}
