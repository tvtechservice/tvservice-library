package com.pci.service.model;



import com.google.gson.annotations.SerializedName;
import com.pci.service.network.PCIPayload;

public class PCI3018{

    public static class Request extends PCIPayload {

          @SerializedName("package_name") String package_name;
          @SerializedName("p_id") String p_id;
          @SerializedName("agree_yn") boolean agree;

        public Request package_name(String package_name) {
            this.package_name = package_name;
            return this;
        }

        public Request p_id(String p_id) {
            this.p_id = p_id;
            return this;
        }

        public Request agree(boolean agree) {
            this.agree = agree;
            return this;
        }
    }

    public static class Response extends PCIPayload {

    }
}
