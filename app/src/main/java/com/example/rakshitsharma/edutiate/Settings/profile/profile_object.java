package com.example.rakshitsharma.edutiate.Settings.profile;

/**
 * Created by Rakshit Sharma on 4/2/2017.
 */

public class profile_object {

    private String mText1;
    private String mText2;

    profile_object (String text1, String text2){
        mText1 = text1;
        mText2 = text2;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }


}
