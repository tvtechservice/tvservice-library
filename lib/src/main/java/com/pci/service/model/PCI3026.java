package com.pci.service.model;

import com.google.gson.annotations.SerializedName;
import com.pci.service.network.PCIPayload;

public class PCI3026 {

    public static class Request extends PCIPayload {

        @SerializedName("said") String said;
        @SerializedName("maid") String maid;
        @SerializedName("gaid") String gaid;
        @SerializedName("partner_code") String partner_code;
        @SerializedName("gender") String gender;
        @SerializedName("age") String age;
        @SerializedName("reg_date") String reg_date;

        public Request said(String said) {
            this.said = said;
            return this;
        }

        public Request maid(String maid) {
            this.maid = maid;
            return this;
        }

        public Request gaid(String gaid) {
            this.gaid = gaid;
            return this;
        }

        public Request partner_code(String partner_code) {
            this.partner_code = partner_code;
            return this;
        }

        public Request gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Request age(String age) {
            this.age = age;
            return this;
        }
        public Request reg_date(String reg_date) {
            this.reg_date = reg_date;
            return this;
        }
    }

    public static class Response extends PCIPayload {

        @SerializedName("p_id") String p_id;

        public String p_id() {
            return p_id;
        }
        public void p_id(String p_id) {
            this.p_id = p_id;
        }

    }
}
