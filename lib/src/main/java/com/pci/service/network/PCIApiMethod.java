package com.pci.service.network;


public enum PCIApiMethod {
    GET("GET"),
    POST("POST"),
    NONE(""),;

    String value;

    PCIApiMethod( String value) {
        this.value = value;
    }
}
