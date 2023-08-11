package com.pci.service.model;

import com.google.gson.annotations.SerializedName;
import com.pci.beacon.C;
import com.pci.service.network.PCIPayload;

public class PCICheckinInfo extends PCIPayload implements C {
    //pairing=N, gaid=acdbf531-52fd-425d-913b-a510d1e7203e, gender=M, old=6.0, check_in_type=S, personal_basic_time=0.0
    @SerializedName("pairing") String pairing;
    @SerializedName("gaid") String gaid;
    @SerializedName("gender") String gender;
    @SerializedName("old") int old;
    @SerializedName("check_in_type") String check_in_type;
    @SerializedName("personal_basic_time") int personal_basic_time;

    public PCICheckinInfo() {}
    public String pairing() {
        return pairing;
    }
    public String gaid() {
        return gaid;
    }
    public String gender() {
        return gender;
    }
    public int old() {
        return old;
    }
    public String check_in_type() {
        return check_in_type;
    }
    public int personal_basic_time() {
        return personal_basic_time;
    }

}
