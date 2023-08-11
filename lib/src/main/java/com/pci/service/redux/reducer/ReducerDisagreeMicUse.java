//package com.pci.service.redux.reducer;
//
//
//import com.pci.service.network.PCIApi;
//import com.pci.service.network.PCINetworkException;
//import com.pci.service.redux.action.ActionDisagreeMicUse;
//import com.pci.service.redux.core.Action;
//import com.pci.service.redux.core.PCIReducer;
//import com.pci.service.redux.state.PCIState;
//import com.pci.service.util.PCILog;
//
//public class ReducerDisagreeMicUse implements PCIReducer {
//
//
//    @Override
//    public PCIState reduce(  PCIState currentState,   Action action) {
//
////        ActionDisagreeMicUse actionDisagreeMicUse = (ActionDisagreeMicUse) action;
//
////        if (currentState.isMicUseAgreed()) {
////            currentState.setMicUseAgreed(false);
////            try {
////                PCIApi.requestDisagreeMicUse(currentState);
////            } catch (PCINetworkException e) {
////                e.printStackTrace();
////            }
////            PCILog.d(" isMicUseAgreed: true -> false");
////        }
////        else {
////            PCILog.d(" No change: isMicUseAgreed is already false");
////        }
//        try {
//            currentState.setMicUseAgreed(false);
//            PCIApi.requestDisagreeMicUse(currentState);
//            PCILog.d(" BLUETOOTH PERMISSION : False");
//        } catch (PCINetworkException e) {
//            e.printStackTrace();
//            PCILog.d(" BLUETOOTH PERMISSION(False) Request Fail");
//        }
//
//        return currentState;
//    }
//}
//
