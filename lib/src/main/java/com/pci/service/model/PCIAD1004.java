package com.pci.service.model;


import com.google.gson.annotations.SerializedName;
import com.pci.service.network.PCIADPayload;


public class PCIAD1004 {

    public static class Request extends PCIADPayload {


        @SerializedName("taid") String taid;


        public PCIAD1004.Request taid(String taid) {
            this.taid = taid;
            return this;
        }

    }

    public static class Response extends PCIADPayload {

    }
}
