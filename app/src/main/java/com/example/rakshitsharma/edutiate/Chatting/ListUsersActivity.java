package com.example.rakshitsharma.edutiate.Chatting;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Chatting.Adapter.ListUsersAdapter;
import com.example.rakshitsharma.edutiate.Chatting.Commons.common;
import com.example.rakshitsharma.edutiate.Chatting.Holder.QBUsersHolder;
import com.example.rakshitsharma.edutiate.R;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class ListUsersActivity extends AppCompatActivity {
    ListView listUsers;
    Button btnCreateChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        retrieveAllUser();
        listUsers = (ListView)findViewById(R.id.listUsers);
        listUsers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        btnCreateChat = (Button)findViewById(R.id.btn_createChat);
        btnCreateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int countChoice = listUsers.getCount();
                if(listUsers.getCheckedItemPositions().size() == 1)
                {
                    createPrivateChat(listUsers.getCheckedItemPosition());
                }
                else if(listUsers.getCheckedItemPositions().size()>1)
                    createGroupChat(listUsers.getCheckedItemPosition());
                else
                    Toast.makeText(getApplicationContext(),"Please select friend to chat",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void createGroupChat(int checkedItemPosition) {
        final ProgressDialog mDialog = new ProgressDialog(ListUsersActivity.this);
        mDialog.setMessage("Please wait....");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        int countChoice = listUsers.getCount();
        ArrayList<Integer> occupantIdsList = new ArrayList<>();
        for(int i=0;i<countChoice;i++)
        {
            if(listUsers.getCheckedItemPositions().get(i))
            {
                QBUser user = (QBUser)listUsers.getItemAtPosition(i);
                occupantIdsList.add(user.getId());
            }
        }

        //Create chat

        QBChatDialog dialog = new QBChatDialog();
        dialog.setName(common.createChatDialogName(occupantIdsList));
        dialog.setType(QBDialogType.GROUP);
        dialog.setOccupantsIds(occupantIdsList);
        QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                mDialog.dismiss();
                Toast.makeText(getBaseContext(),"Chat dialog created successfully",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("ERROR",e.getMessage());
            }
        });
    }
    private void createPrivateChat(int checkedItemPosition) {
        final ProgressDialog mDialog = new ProgressDialog(ListUsersActivity.this);
        mDialog.setMessage("Please wait....");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        int countChoice = listUsers.getCount();

        for(int i=0;i<countChoice;i++)
        {
            if(listUsers.getCheckedItemPositions().get(i))
            {
                QBUser user = (QBUser)listUsers.getItemAtPosition(i);
                QBChatDialog dialog = DialogUtils.buildPrivateDialog(user.getId());

                QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                        mDialog.dismiss();
                        Toast.makeText(getBaseContext(),"Chat private dialog created successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.e("ERROR",e.getMessage());
                    }
                });
            }
        }

    }
    private void retrieveAllUser() {

        QBUsers.getUsers(null).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {

                QBUsersHolder.getInstance().putUsers(qbUsers);

                ArrayList<QBUser> qbUserWithoutCurrent = new ArrayList<QBUser>();

                for(QBUser user:qbUsers)
                {
                    if(!user.getLogin().equals(QBChatService.getInstance().getUser().getLogin()))
                        qbUserWithoutCurrent.add(user);
                }
                ListUsersAdapter adapter = new ListUsersAdapter(getBaseContext(),qbUserWithoutCurrent);
                listUsers.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("ERROR",e.getMessage());
            }
        });
    }
}
