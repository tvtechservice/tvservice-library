package com.pci.service.network;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import com.pci.beacon.BuildConfig;
import com.pci.service.util.PCILog;


public enum PCIApiTarget {
    PCI_3001_UPLOAD_TERM_AGREEMENTS(3001, "set_terms_agree"),
    PCI_3002_FETCH_POLICY(3002, "get_policy"),
    PCI_3018_UPLOAD_MIC_PERMISSION_AGREEMENTS(3018, "set_mic_agree"),
    PCI_3026_DMR_CHECK_IN(3026,"dmr_check_in"),
    PCI_3003_CHECK_IN(3003, "checkin"),
    PCI_3024_CHECK_IN_LIST(3024, "checkin_list");


    int code;
    String token;

    PCIApiTarget(int code,  String token) {
        this.code = code;
        this.token = token;
    }

    public URL url() {
        try {
            return new URL(String.format(Locale.getDefault(),
                "%s/%s/PCI_%d/%s",
                BuildConfig.API_HOST,
                BuildConfig.API_VERSION,
                code,
                token
            ));
        } catch (MalformedURLException e) {
            PCILog.e(e);
            return null;
        }
    }

    public PCIApiMethod method() {
        switch (this) {
            case PCI_3001_UPLOAD_TERM_AGREEMENTS:
                return PCIApiMethod.POST;
            case PCI_3002_FETCH_POLICY:
                return PCIApiMethod.GET;
            case PCI_3018_UPLOAD_MIC_PERMISSION_AGREEMENTS:
                return PCIApiMethod.POST;
            case PCI_3026_DMR_CHECK_IN:
                return PCIApiMethod.POST;
            case PCI_3003_CHECK_IN:
                return PCIApiMethod.POST;
            case PCI_3024_CHECK_IN_LIST:
                return PCIApiMethod.POST;

            default:
                return PCIApiMethod.NONE;
        }
    }
}

