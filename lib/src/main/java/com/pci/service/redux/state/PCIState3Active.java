package com.pci.service.redux.state;

import android.content.Intent;
import android.os.Handler;


import com.pci.service.redux.action.ActionDowngradeState;
import com.pci.service.redux.core.PCIStore;

import static com.pci.service.redux.state.PCIState.Type.ACTIVE;


public class PCIState3Active extends PCIState {

    public PCIState3Active(PCIState oldState) {
        super(oldState);
        this.type = ACTIVE;
    }

    @Override
    public void onKeep() {
        if (this.maxType().getValue() < this.type.getValue()) {
            PCIStore.getInstance(context).dispatchTriggeredByInternalRedux(new ActionDowngradeState(this.maxType()));
        }
    }

    @Override
    public void onEnter( PCIState.Type prevStateLevel) {
        if (this.type.getValue() < prevStateLevel.getValue()) { // downgraded
            this.setStbid(null); // check-in
            this.setSaid(null); // check-in

        } else { // upgraded
//            final boolean ret = PCIBitsoundManager.configure(context, true);
//            if (ret) {
//                new Handler(context.getMainLooper()).post(() -> {
//                    context.sendBroadcast(new Intent(context, PCIBitsoundJobService.Receiver.class));
//                });
//            }
        }
    }

    @Override
    public void onLeave( PCIState.Type nextStateLevel) {
        // disable bitsound under active state
        if (nextStateLevel.getValue() < ACTIVE.getValue()) {
//            new Handler(context.getMainLooper()).post(() -> {
//                Bitsound.with(context).stopPeriodicDetection();
//                PCIBitsoundJobService.stop(context);
//            });
        }
    }

}
