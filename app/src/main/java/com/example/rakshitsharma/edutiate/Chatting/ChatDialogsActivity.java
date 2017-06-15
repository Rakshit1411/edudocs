package com.example.rakshitsharma.edutiate.Chatting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Chatting.Adapter.ChatDialogsAdapters;
import com.example.rakshitsharma.edutiate.R;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.BaseService;
import com.quickblox.auth.session.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.BaseServiceException;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class ChatDialogsActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    ListView listChatDialogs;
    String user,password;
    @Override
    protected void onResume(){
        super.onResume();
        loadChatDialogs();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dialogs);

        user = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        Toast.makeText(getBaseContext(),"HELLO : "+user+" "+password,Toast.LENGTH_SHORT);



        createSessionForChat();


        listChatDialogs = (ListView)findViewById(R.id.listChatDialogs);


        loadChatDialogs();

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatDialogsActivity.this,ListUsersActivity.class);
                startActivity(intent);

            }
        });
    }

    private void loadChatDialogs() {
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(100);
        QBRestChatService.getChatDialogs(null,requestBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBChatDialog> qbChatDialogs, Bundle bundle) {
                ChatDialogsAdapters adapter = new ChatDialogsAdapters(getBaseContext(),qbChatDialogs);
                listChatDialogs.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("ERROR",""+e.getMessage());
            }
        });
    }

    private void createSessionForChat() {
        final ProgressDialog mDialog = new ProgressDialog(ChatDialogsActivity.this);
        mDialog.setMessage("Please wait....");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        user = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        final QBUser qbUser = new QBUser(user,password);
        QBAuth.createSession(qbUser).performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                qbUser.setId(qbSession.getUserId());
                try {
                    qbUser.setPassword(BaseService.getBaseService().getToken());
                } catch (BaseServiceException e) {
                    e.printStackTrace();
                }
                QBChatService.getInstance().login(qbUser, new QBEntityCallback() {
                    @Override
                    public void onSuccess(Object o, Bundle bundle) {
                        mDialog.dismiss();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.e("ERROR",""+e.getLocalizedMessage());
                        Toast.makeText(getBaseContext(),"ERRORRrrrrR",Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {
                Toast.makeText(getBaseContext(),"ERRORRR",Toast.LENGTH_SHORT);
            }
        });
    }
}
