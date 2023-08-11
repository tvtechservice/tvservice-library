package com.pci.service.model;





import com.google.gson.annotations.SerializedName;
import com.pci.service.network.PCIPayload;

import java.util.List;


public class PCI3003 {

    public static class Request extends PCIPayload {

          @SerializedName("p_id") String p_id;
          @SerializedName("sound_id") String sound_id;
          @SerializedName("sound_id_type") String sound_id_type;
          @SerializedName("device_token") String device_token;
          @SerializedName("wifi_yn") boolean use_wifi;
          @SerializedName("Wifi_info") String Wifi_info;
          @SerializedName("checkout_stb_id") String checkout_stb_id;

        public Request p_id(String p_id) {
            this.p_id = p_id;
            return this;
        }

        public Request sound_id(String sound_id) {
            this.sound_id = sound_id;
            return this;
        }
        public Request sound_id_type(String sound_id_type) {
            this.sound_id_type = sound_id_type;
            return this;
        }

        public Request device_token(String device_token) {
            this.device_token = device_token;
            return this;
        }

        public Request use_wifi(boolean use_wifi) {
            this.use_wifi = use_wifi;
            return this;
        }

        public Request wifi_info(String Wifi_info) {
            this.Wifi_info = Wifi_info;
            return this;
        }

        public Request checkout_stb_id(String checkout_stb_id) {
            this.checkout_stb_id = checkout_stb_id;
            return this;
        }
    }

    public static class Response extends PCIPayload {

          @SerializedName("stb_id") long stb_id;

        public long stb_id() {
            return stb_id;
        }

        public void stb_id(long stb_id) {
            this.stb_id = stb_id;
        }


    }
}
