package com.pci.service.model;


import com.google.gson.annotations.SerializedName;
import com.pci.service.network.PCIADPayload;

public class PCIAD3001 {
    public static class Request extends PCIADPayload {


        @SerializedName("taid") String taid;
        @SerializedName("minor") String minor;
        @SerializedName("maid") String maid;


        public Request taid(String taid) {
            this.taid = taid;
            return this;
        }
        public Request minor(String minor) {
            this.minor = minor;
            return this;
        }
        public Request maid(String maid) {
            this.maid = maid;
            return this;
        }

    }

    public static class Response extends PCIADPayload {
        @SerializedName("pairing") String pairing;
        @SerializedName("gaid") String gaid;
        @SerializedName("gender") String gender;
        @SerializedName("old") int old;
        @SerializedName("check_in_type") String check_in_type;
        @SerializedName("personal_basic_time") int personal_basic_time;

        public String pairing() {
            return pairing;
        }
        public String gaid() {
            return gaid;
        }
        public String gender() {
            return gender;
        }
        public int old() {return old;}
        public String check_in_type() {
            return check_in_type;
        }
        public int personal_basic_time() {return personal_basic_time;}

        public void pairing(String pairing) {
            this.pairing = pairing;
        }
        public void gaid(String gaid) {
            this.gaid = gaid;
        }
        public void gender(String gender) {
            this.gender = gender;
        }
        public void old(int old) {this.old = old;}
        public void check_in_type(String check_in_type) {
            this.check_in_type = check_in_type;
        }
        public void personal_basic_time(int personal_basic_time) { this.personal_basic_time = personal_basic_time;}

        public PCICheckinInfo toCheckinInfo() {
            PCICheckinInfo info = new PCICheckinInfo();
            info.pairing = this.pairing;
            info.gaid = this.gaid;
            info.gender = this.gender;
            info.old = this.old;
            info.check_in_type = this.check_in_type;
            info.personal_basic_time = this.personal_basic_time;


            return info;
        }
    }
}
