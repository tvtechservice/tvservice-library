package com.pci.service.redux.reducer;

import com.pci.service.network.PCIApi;
import com.pci.service.redux.action.ActionDMRCheckin;
import com.pci.service.redux.core.Action;
import com.pci.service.redux.core.PCIReducer;
import com.pci.service.redux.state.PCIState;
import com.pci.service.util.PCILog;

public class ReducerDMRCheckin implements PCIReducer {


    @Override
    public PCIState reduce(PCIState currentState, Action action) {
        ActionDMRCheckin actionDMRCheckin = (ActionDMRCheckin) action;
        currentState.setAdid(actionDMRCheckin.getAdid());
        currentState.setSaid(actionDMRCheckin.getSaid());
        currentState.setPartner_code(actionDMRCheckin.getPartnerCode());
        currentState.setMaid(actionDMRCheckin.getMaid());
        currentState.setGender(actionDMRCheckin.getGender());
        currentState.setAge(actionDMRCheckin.getAge());
        currentState.setRegdate(actionDMRCheckin.getRegdate());


        try {
            PCIApi.requestDMRCheckin(currentState);

            return currentState;
        } catch (Exception e) {
            PCILog.e(e);
            return currentState;
        }
    }
}

