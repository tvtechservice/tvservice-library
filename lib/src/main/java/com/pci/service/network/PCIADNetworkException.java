package com.pci.service.network;

import java.io.IOException;

public class PCIADNetworkException extends IOException {
    private int code;

    PCIADNetworkException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
