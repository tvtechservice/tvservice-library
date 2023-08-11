package com.pci.service.redux.core;


import com.pci.service.redux.state.PCIState;

public interface State {
    void onKeep();
    void onEnter( PCIState.Type fromStateLevel);
    void onLeave( PCIState.Type toStateLevel);
    void writePersistent();
    PCIState.Type getType();
}
