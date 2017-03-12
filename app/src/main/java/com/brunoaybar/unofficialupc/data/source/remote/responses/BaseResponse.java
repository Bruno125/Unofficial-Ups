package com.brunoaybar.unofficialupc.data.source.remote.responses;

import android.text.TextUtils;

import com.brunoaybar.unofficialupc.utils.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by brunoaybar on 15/10/2016.
 */

public class BaseResponse {


    @SerializedName("CodError")
    @Expose
    private String errorCode;
    @SerializedName("MsgError")
    @Expose
    private String errorMessage;

    public boolean isError(){
        return  !Utils.isEmpty(getErrorMessage()) &&
                !Utils.isEmpty(getErrorCode()) &&
                !getErrorCode().equals("00000");
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
