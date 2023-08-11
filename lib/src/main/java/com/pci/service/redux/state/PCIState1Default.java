package com.pci.service.redux.state;

import static com.pci.service.redux.state.PCIState.Type.DEFAULT;

import com.pci.beacon.PCIAdvertise;

public class PCIState1Default extends PCIState {

    public PCIState1Default(PCIState oldState) {
        super(oldState);
        this.type = DEFAULT;
    }

    @Override
    public void onKeep() {


    }

    @Override
    public void onEnter( PCIState.Type prevStateLevel) {
        if (this.type.getValue() < prevStateLevel.getValue()) { // downgraded
            this.setPid(null); // agree-terms
            this.setStbid(null); // check-in
            this.setSaid(null); // check-in
            this.setUuid(null); // agree-terms
            this.setAdid(null); // agree-terms
            this.setPhoneNumber(null); // agree-terms
            this.setFcmToken(null); // agree-terms
            this.setMac(null); // agree-terms
            this.setOtmSuid(null); // agree-terms
            this.setPackageKey(null); // agree-terms
            this.setTermAgreed(false); // agree-terms
            this.setAdidUseAgreed(false); // agree-terms
            this.setAdPushAgreed(false); // agree-terms
//            this.setMicUseAgreed(false); // opt-in
            this.setBleUseAgreed(false); // agree-terms
            this.setOptIn(false); // opt-in




        } else { // upgraded
            throw new IllegalStateException("Must not reach upgrade block in default");
        }
    }

    @Override
    public void onLeave( PCIState.Type nextStateLevel) {

    }
}
