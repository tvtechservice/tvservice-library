package com.pci.service.network;

import com.pci.beacon.C;
import com.pci.beacon.PCI;
import com.pci.service.model.*;
import com.pci.service.redux.action.ActionDMRCheckin;
import com.pci.service.redux.core.Action;
import com.pci.service.redux.core.PCIStore;
import com.pci.service.redux.state.PCIState;
import com.pci.service.redux.state.PCIState2Idle;
import com.pci.service.util.PCIStorage;


import static com.pci.service.network.PCIADApiTarget.PCIAD_3001_DMR_CHECK_IN;

import android.util.Log;


@SuppressWarnings("UnnecessaryReturnStatement")
public class PCIADApi implements C {


    public static PCICheckinInfo requestDMRCheckin(String taid_, String maid_, String minor_) throws PCIADNetworkException {
//        PCIAD1002.Request requestData = new PCIAD1002.Request()
//                .taid(taid_)
//                .major("7584")
//                .minor("1004")
//                .maid("7768731f-1206-0f65-8913-a955d113afa9");
//        PCIADApiResponse<PCIAD1002.Response> response = PCIADApiCommander.getInstance().send(
//                new PCIADApiRequest(PCIAD_1002_CHECK_IN, requestData),
//                PCIAD1002.Response.class
//        );
        PCIAD3001.Request requestData = new PCIAD3001.Request()
                .taid(taid_)
                .minor(minor_)
                .maid(maid_);
        PCIADApiResponse<PCIAD3001.Response> response = PCIADApiCommander.getInstance().send(
                new PCIADApiRequest(PCIAD_3001_DMR_CHECK_IN, requestData),
                PCIAD3001.Response.class
        );

//        if (response.isSuccessful()) return;
//        else throw new PCIADNetworkException("Unsuccessful API Request", response.getCode());

        if (response.isSuccessful()) {
            if (response.getData() != null) {
                final PCICheckinInfo checkininfo = response.getData().toCheckinInfo();
                //state.setCheckinInfo(checkininfo);

//                 Action actionDMRcheckin = new ActionDMRCheckin("78400000-8cf0-11bd-b23e-10b96e40000d", "25", "M", "78400000-8cf0-11bd-b23e-10b96e40000d","3000","69016746119","20230714004111");
//                 PCIStore.getInstance().dispatch(actionDMRcheckin);

                return checkininfo;
                // Todo : app_install info 사용하기
            } else throw new PCIADNetworkException("No Data", response.getCode());
        } else throw new PCIADNetworkException("Unsuccessful API Request", response.getCode());



    }

//    public static void requestCheckinList() throws PCIADNetworkException {
//        PCIAD1004.Request requestData = new PCIAD1004.Request()
//                .taid("21226394842868");
//
//        PCIADApiResponse<PCIAD1004.Response> response = PCIADApiCommander.getInstance().send(
//                new PCIADApiRequest(PCIAD_1004_CHECK_IN_LIST, requestData),
//                PCIAD1004.Response.class
//        );
//
//        if (response.isSuccessful()) SearchStbWifiManager.getInstance(context).findStbAddr(0);
//        else throw new PCIADNetworkException("Unsuccessful API Request", response.getCode());
//
//    }

}
