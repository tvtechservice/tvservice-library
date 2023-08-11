package com.pci.service.redux.core;


import com.pci.service.redux.state.PCIState;

public interface PCINotifier {

    void notify( PCIState state);
}
