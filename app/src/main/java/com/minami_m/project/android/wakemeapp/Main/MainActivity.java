package com.minami_m.project.android.wakemeapp.Main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.minami_m.project.android.wakemeapp.ActivityChangeListener;
import com.minami_m.project.android.wakemeapp.ChatRoom.ChatRoomActivity;
import com.minami_m.project.android.wakemeapp.ChatRoomCardClickListener;
import com.minami_m.project.android.wakemeapp.FirebaseRealtimeDatabaseHelper;
import com.minami_m.project.android.wakemeapp.Model.ChatRoomCard;
import com.minami_m.project.android.wakemeapp.Model.User;
import com.minami_m.project.android.wakemeapp.R;
import com.minami_m.project.android.wakemeapp.SearchFriend.SearchFriendActivity;
import com.minami_m.project.android.wakemeapp.SignIn.SignInActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityChangeListener, ChatRoomCardClickListener {
    public static final String TAG = "---- MainActivity ----";
    public static final String CHAT_ROOM_ID = "ChatRoomID";
    public static final String RECEIVER_ICON = "ReceiverIcon";
    private ImageButton button;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private List<ChatRoomCard> chatRoomCards;
    private RecyclerView recyclerView;
    private CardRecyclerAdapter adapter;
    private TextView currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: 123456789 ------- HEY! -------");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUserName = findViewById(R.id.current_user_name);
        currentUserName.setText(String.format("%s!",currentUser.getDisplayName()));
        chatRoomCards = new ArrayList<>();
        button = findViewById(R.id.semicircle_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(SearchFriendActivity.class);
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Log.i(TAG, "onCreate: 123456789 ID: " + currentUser.getUid());

        adapter = new CardRecyclerAdapter(chatRoomCards, this);
        recyclerView.setAdapter(adapter);

        FirebaseRealtimeDatabaseHelper.CHAT_ROOM_ID_LIST_REF.child(currentUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        chatRoomCards.clear();
                        Log.i(TAG, "onDataChange: reading? 123456");
                        for (DataSnapshot chatRoomIdSnapshot: dataSnapshot.getChildren()) {
                            User receiver = chatRoomIdSnapshot.getValue(User.class);
                            FirebaseRealtimeDatabaseHelper.updateStatusWithLoginTime(receiver.getId(), receiver.getLastLogin());
                            Log.i(TAG, "onDataChange: 123456789\n" + receiver);
                            ChatRoomCard roomCard = new ChatRoomCard(chatRoomIdSnapshot.getKey(), receiver);
                            chatRoomCards.add(roomCard);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.i(TAG, "onCancelled: " + databaseError.getMessage());
                    }
                });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null) {
            launchActivity(SignInActivity.class);
        }
    }

    @Override
    public void launchActivity(Class nextActivity) {
        Intent intent = new Intent(this, nextActivity);
        startActivity(intent);
    }

    @Override
    public void onChatRoomCardClicked(View v, int position) {
        ChatRoomCard roomCard = chatRoomCards.get(position);
        Log.i(TAG, "onChatRoomCardClicked: CLICKED!\n" +
                "Position: " + position + "\n" +
                roomCard.getReceiver().getName());
        Intent intent = new Intent(this, ChatRoomActivity.class);
        Bundle data = new Bundle();
        data.putString(CHAT_ROOM_ID, roomCard.getChatRoomId());
        data.putString(RECEIVER_ICON, roomCard.getReceiver().getIcon());
        intent.putExtras(data);
        startActivity(intent);
//        finish();
    }
}