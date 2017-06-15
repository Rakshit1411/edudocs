package com.example.rakshitsharma.edutiate.Chatting.Commons;

import com.example.rakshitsharma.edutiate.Chatting.Holder.QBUsersHolder;
import com.quickblox.users.model.QBUser;

import java.util.List;

/**
 * Created by Rakshit Sharma on 6/4/2017.
 */

public class common {

    public static String createChatDialogName(List<Integer> qbUsers)
    {
        List<QBUser> qbUsers1 = QBUsersHolder.getInstance().getUsersByIds(qbUsers);
        StringBuilder name = new StringBuilder();
        for(QBUser user:qbUsers1)
        {
            name.append(user.getFullName()).append("   ");

        }
        if(name.length()>30)
            name = name.replace(30,name.length(),"...");
        return name.toString();
    }
}
