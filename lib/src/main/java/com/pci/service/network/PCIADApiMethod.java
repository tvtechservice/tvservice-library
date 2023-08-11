package com.pci.service.network;



public enum PCIADApiMethod {
    GET("GET"),
    POST("POST"),
    NONE(""),;


    String value;

    PCIADApiMethod( String value) {
        this.value = value;
    }
}
