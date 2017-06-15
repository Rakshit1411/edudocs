package com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards;

/**
 * Created by Rakshit Sharma on 4/4/2017.
 */

public class my_batches_in_home_object {


    private String mText1;
    private String mText2;
    private String mText3;
    private String mText4;


    my_batches_in_home_object (String text1,String text2,String text3,String text4){
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
        mText4 = text4;


    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public String getmText3() {
        return mText3;
    }

    public String getmText4() {
        return mText4;
    }
    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }


}
