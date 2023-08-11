package com.pci.service.redux.action;

import com.pci.service.redux.core.Action;

import java.util.HashMap;

public class ActionCheckinList extends Action {

    private static final String KEY_SAID = "SAID";

    public ActionCheckinList(String SAID_) {
        super(ActionType.PCI_CHECKIN_LIST, new HashMap<String, Object>() {{
            put(KEY_SAID, SAID_);
        }});
    }

    public String getSaid() {
        return (String) getPayload().get(KEY_SAID);
    }

}
