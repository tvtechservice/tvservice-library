package com.pci.service.redux.core;



import com.pci.service.redux.state.PCIState;

public interface PCIStateChangeNotifier {

    void stateChangeNotify( PCIState prevState,  PCIState newState);
}
