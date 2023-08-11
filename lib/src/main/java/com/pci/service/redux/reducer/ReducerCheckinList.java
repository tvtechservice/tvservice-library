package com.pci.service.redux.reducer;


import com.pci.service.network.PCIApi;
import com.pci.service.network.PCINetworkException;
import com.pci.service.redux.action.ActionCheckinList;
import com.pci.service.redux.core.Action;
import com.pci.service.redux.core.PCIReducer;
import com.pci.service.redux.state.PCIState;
import com.pci.service.util.PCILog;

public class ReducerCheckinList  implements PCIReducer {

     
    @Override
    public PCIState reduce(  PCIState currentState,   Action action) {

        ActionCheckinList actionCheckinlist = (ActionCheckinList) action;
        currentState.setSaid(actionCheckinlist.getSaid());


        try {
            // request checkin
            PCIApi.requestCheckinlist(currentState);


            return currentState;
        } catch (PCINetworkException e) {
            PCILog.e(e);
            return currentState;
        }
    }
}
