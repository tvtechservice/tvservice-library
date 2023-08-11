package com.pci.service.redux.action;

import com.pci.service.redux.core.Action;

import java.util.HashMap;

public class ActionDMRCheckin extends Action {

    private static final String KEY_ADID = "KEY_ADID";
    private static final String KEY_AGE = "KEY_AGE";
    private static final String KEY_GENDER = "KEY_GENDER";
    private static final String KEY_MAID = "KEY_MAID";
    private static final String KEY_PARTNERCODE = "KEY_PARTNERCODE";
    private static final String KEY_SAID = "KEY_SAID";
    private static final String KEY_REG_DATE = "KEY_REG_DATE";

    public ActionDMRCheckin(final String adid, final String age, final String gender, final String maid, final String partnerCode,final String said,final String regdate) {
        super(ActionType.PCI_DMR_CHECKIN, new HashMap<String, Object>() {{
            this.put(KEY_ADID, adid);
            this.put(KEY_AGE, age);
            this.put(KEY_GENDER, gender);
            this.put(KEY_MAID, maid);
            this.put(KEY_PARTNERCODE, partnerCode);
            this.put(KEY_SAID, said);
            this.put(KEY_REG_DATE, regdate);
        }});
    }
    public String getAdid() {
        return (String) getPayload().get(KEY_ADID);
    }
    public String getAge() {
        return (String) getPayload().get(KEY_AGE);
    }
    public String getGender() {
        return (String) getPayload().get(KEY_GENDER);
    }
    public String getMaid() {
        return (String) getPayload().get(KEY_MAID);
    }
    public String getPartnerCode() {
        return (String) getPayload().get(KEY_PARTNERCODE);
    }
    public String getSaid() {
        return (String) getPayload().get(KEY_SAID);
    }
    public String getRegdate() {
        return (String) getPayload().get(KEY_REG_DATE);
    }

}