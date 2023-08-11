package com.pci.service.network;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import com.pci.beacon.BuildConfig;
import com.pci.service.util.PCILog;


public enum PCIADApiTarget {
    PCIAD_1002_CHECK_IN(1002, "check-in"),
    PCIAD_1004_CHECK_IN_LIST(1004, "check-in-list"),
    PCIAD_3001_DMR_CHECK_IN(3001, "dmr-check-in");


    int code;
    String token;

    PCIADApiTarget(int code, String token) {
        this.code = code;
        this.token = token;
    }


    public URL url() {
        try {
            return new URL(String.format(Locale.getDefault(),
                    "%s/%s/PCIAD-%d/%s",
                    BuildConfig.API_HOST_AD,
                    BuildConfig.API_VERSION,
                    code,
                    token
            ));
        } catch (MalformedURLException e) {
            PCILog.e(e);
            return null;
        }
    }


    public PCIADApiMethod method() {
        switch (this) {
            case PCIAD_1002_CHECK_IN:
                return PCIADApiMethod.POST;
            case PCIAD_1004_CHECK_IN_LIST:
                return PCIADApiMethod.POST;
            case PCIAD_3001_DMR_CHECK_IN:
                return PCIADApiMethod.POST;

            default:
                return PCIADApiMethod.NONE;
        }
    }
}

