package com.pci.service.model;


import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.pci.beacon.C;
import com.pci.service.network.PCIPayload;


public class PCICheckinList extends PCIPayload implements C {

    @SerializedName("pidlist") JsonArray pidlist;
    public PCICheckinList() {    }
    public JsonArray pidlist() {
        return pidlist;
    }


}
