package com.pci.service.redux.action;

import com.pci.service.redux.core.Action;

import java.util.HashMap;

public class ActionAgreeMicUse extends Action {

    public ActionAgreeMicUse() {
        super(ActionType.PCI_AGREE_MIC_USE, new HashMap<String, Object>() {{
        }});
    }
}
