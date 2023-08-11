package com.pci.service.network;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.pci.beacon.C;

public class PCIADApiResponseHolder implements C {

    @SerializedName("res_code") private int code;
    @SerializedName("res_msg") private String message;
    @SerializedName("data") private Map<String, Object> data = new HashMap<>();
//    @SerializedName("data_list") private ArrayList datalist;

    public PCIADApiResponseHolder() {}

    public boolean isSuccessful() {
        return Integer.valueOf(code) / 100 == 2;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public <T> T getData(final Class<T> responseDataClass) {
        if (data != null) {
            return gson.fromJson(gson.toJson(data), responseDataClass);
        } else {
            return null;
        }
    }

}
