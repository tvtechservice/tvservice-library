package com.pci.service.network;

import com.pci.beacon.C;
import com.pci.service.model.*;
import com.pci.service.redux.state.PCIState;
import com.pci.service.util.PCILog;

import static com.pci.service.network.PCIApiTarget.*;

import android.text.TextUtils;
import android.util.Log;

@SuppressWarnings("UnnecessaryReturnStatement")
public class PCIApi implements C {

    public static void requestDMRCheckin(final PCIState state) throws PCINetworkException {
        PCI3026.Request requestData = new PCI3026.Request()
                .said(state.getSaid())
                .maid(state.getMaid())
                .gaid(state.getAdid())
                .partner_code(state.getPartner_code())
                .gender(state.getGender())
                .age(state.getAge())
                .reg_date(state.getRegdate());


        PCIApiResponse<PCI3026.Response> response = PCIApiCommander.getInstance().send(
                new PCIApiRequest(PCIApiTarget.PCI_3026_DMR_CHECK_IN, requestData, state),
                PCI3026.Response.class
        );

        if (response.isSuccessful()) {
            if (response.getData() != null) {

            } else throw new PCINetworkException("No Data", response.getCode());
        } else throw new PCINetworkException("Unsuccessful API Request", response.getCode());
    }


    public static String requestCheckinlist(final PCIState state) throws PCINetworkException {
        PCI3024.Request requestData = new PCI3024.Request()
                .said(state.getSaid());


        PCIApiResponse<PCI3024.Response> response = PCIApiCommander.getInstance().send(
                new PCIApiRequest(PCIApiTarget.PCI_3024_CHECK_IN_LIST, requestData, state),
                PCI3024.Response.class
        );

        if (response.isSuccessful()) {
            if (response.getData() != null) {
                final PCICheckinList checkinList = response.getData().toCheckinlist();
                state.setCheckinlist(checkinList);

//                state.setPidlist(String.valueOf(response.getData().pidlist())); //pidlist 조회결과
                return String.valueOf(response.getData().pidlist());


            } else throw new PCINetworkException("No Data", response.getCode());
        } else throw new PCINetworkException("Unsuccessful API Request", response.getCode());
    }



}
