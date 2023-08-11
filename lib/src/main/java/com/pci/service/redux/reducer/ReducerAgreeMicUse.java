//package com.pci.service.redux.reducer;
//
//
//
//
//import com.pci.service.network.PCIApi;
//import com.pci.service.network.PCINetworkException;
//import com.pci.service.redux.action.ActionAgreeMicUse;
//import com.pci.service.redux.core.Action;
//import com.pci.service.redux.core.PCIReducer;
//import com.pci.service.redux.state.PCIState;
//import com.pci.service.util.PCILog;
//
//public class ReducerAgreeMicUse implements PCIReducer {
//
//
//    @Override
//    public PCIState reduce(  PCIState currentState,   Action action) {
//
////        ActionAgreeMicUse actionAgreeMicUse = (ActionAgreeMicUse) action;
//
////        if (!currentState.isMicUseAgreed()) {
////            currentState.setMicUseAgreed(true);
////            try {
////                PCIApi.requestAgreeMicUse(currentState);
////            } catch (PCINetworkException e) {
////                e.printStackTrace();
////            }
////            PCILog.d(" isMicUseAgreed: false -> true");
////        }
////        else {
////            PCILog.d(" No change: isMicUseAgreed is already true");
////        }
//
//        try {
//            currentState.setMicUseAgreed(true);
//            PCIApi.requestAgreeMicUse(currentState);
//            PCILog.d(" BLUETOOTH PERMISSION : true");
//        } catch (PCINetworkException e) {
//            e.printStackTrace();
//            PCILog.d(" BLUETOOTH PERMISSION(True) Request Fail");
//        }
//
//        return currentState;
//    }
//}
