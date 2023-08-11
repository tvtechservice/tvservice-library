package com.pci.service.model;

import android.util.Log;


import com.google.gson.annotations.SerializedName;
import com.pci.service.network.PCIPayload;

public class PCI3001 {

    public static class Request extends PCIPayload {

          @SerializedName("agree_yn") boolean agree;
          @SerializedName("uuid") String uuid;
          @SerializedName("mobile_num") String mobile_num;
          @SerializedName("package_name") String package_name;
          @SerializedName("adid_use_yn") boolean adid_use;
          @SerializedName("ad_push_yn") boolean ad_push;
          @SerializedName("mac") String mac;
          @SerializedName("otm_suid") String otm_suid;
          @SerializedName("package_key") String package_key;
          @SerializedName("mic_use_yn") boolean mic_use;
          @SerializedName("ble_use_yn") boolean ble_use;

        public Request agree(boolean agree) {
            this.agree = agree;
            return this;
        }

        public Request uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Request mobile_num(String mobile_num) {
            this.mobile_num = mobile_num;
            return this;
        }

        public Request package_name(String package_name) {
            this.package_name = package_name;
            return this;
        }

        public Request adid_use(boolean adid_use) {
            this.adid_use = adid_use;
            return this;
        }

        public Request ad_push(boolean ad_push) {
            this.ad_push = ad_push;
            return this;
        }

        public Request mac(String mac) {
            this.mac = mac;
            return this;
        }

        public Request otm_suid(String otm_suid) {
            this.otm_suid = otm_suid;
            return this;
        }

        public Request package_key(String package_key) {
            this.package_key = package_key;
            return this;
        }

        public Request mic_use(boolean mic_use) {
            this.mic_use = mic_use;
            return this;
        }

        public Request ble_use(boolean ble_use) {
            this.ble_use = ble_use;
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
