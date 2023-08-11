package com.pci.service.network;

import android.util.Log;

import com.pci.beacon.C;

public class PCIApiResponse<DATA> implements C {

    private int code;
    private String message;
    private DATA data;
    private String status;

    PCIApiResponse() {}

    public boolean isSuccessful() {
        return code / 100 == 2;
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

    public DATA getData() {
        return data;
    }
    public void setData(DATA data) {
        this.data = data;}

    public String getStatus() {
        return status;
    }
    public void setStatus(String status_) {
        this.status = status_;}
}
