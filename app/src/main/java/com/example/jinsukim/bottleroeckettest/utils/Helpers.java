package com.example.jinsukim.bottleroeckettest.utils;

public class Helpers {
    private static Helpers mHelpers = null;
    private String mAPIUrl = "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json";

    public static Helpers getInstance(){
        if(mHelpers == null){
            mHelpers = new Helpers();
        }
        return mHelpers;
    }

    public String getAPIUrl() {
        return mAPIUrl;
    }
}
