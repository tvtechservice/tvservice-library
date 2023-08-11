package com.pci.service.network;

import com.pci.beacon.C;

public class PCIADApiResponse<DATA> implements C {

    private int code;
    private String message;
    private DATA data;

    PCIADApiResponse() {}

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
        this.data = data;
    }

}
