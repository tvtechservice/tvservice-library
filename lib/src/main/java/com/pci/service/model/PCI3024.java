package com.pci.service.model;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;



import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.pci.service.network.PCIPayload;
import javax.security.auth.callback.Callback;

public class PCI3024 {

    public static class Request extends PCIPayload {

          @SerializedName("said") String said;


        public PCI3024.Request said(String said) {
            this.said = said;
            return this;
        }

    }

    public static class Response extends PCIPayload {

          @SerializedName("pidlist") JsonArray pidlist;

        public JsonArray pidlist() {
            return pidlist;
        }

        public void pidlist(JsonArray pidlist) {
            this.pidlist = pidlist;
        }

        public PCICheckinList toCheckinlist() {
            PCICheckinList checkinlist = new PCICheckinList();
            checkinlist.pidlist = this.pidlist;

            return checkinlist;
        }



    }
}
