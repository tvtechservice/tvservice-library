package com.pci.service.redux.action;

import com.pci.service.redux.core.Action;

import java.util.HashMap;

public class ActionDisagreeMicUse extends Action {

    public ActionDisagreeMicUse() {
        super(ActionType.PCI_DISAGREE_MIC_USE, new HashMap<String, Object>() {{
        }});
    }
}
