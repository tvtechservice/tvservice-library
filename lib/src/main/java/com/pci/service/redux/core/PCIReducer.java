package com.pci.service.redux.core;



import com.pci.service.redux.state.PCIState;

public interface PCIReducer {

    PCIState reduce( PCIState currentState,  Action action);
}
