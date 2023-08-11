package com.pci.beacon.pciutil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DMR_Check_Info {
    public String SAID;
    public String TAID;
    public Calendar CHECKIN_TIME;
    public String CHECKIN_TIME_STR;


    public DMR_Check_Info( String SAID_, String TAID_ ,Calendar CHECKIN_TIME_) {
        this.SAID = SAID_;
        this.TAID = TAID_;
        this.CHECKIN_TIME = CHECKIN_TIME_;
        this.CHECKIN_TIME_STR = getCHECKIN_TIME_STR(CHECKIN_TIME_);
    }

//    public String getSAID() {
//        return SAID;
//    }
//    public String getTAID() {
//        return TAID;
//    }
//    public Calendar getCHECKIN_TIME() {return CHECKIN_TIME;}
    public String getCHECKIN_TIME_STR(Calendar Time_) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        CHECKIN_TIME_STR = sdf.format(Time_.getTime());
        return CHECKIN_TIME_STR;
    }

//    public void setSAID(String _SAID) {
//        this.SAID = _SAID;
//    }
//    public void setTAID(String _TAID) {
//        this.TAID = _TAID;
//    }
//    public void setCHECKIN_TIME(Calendar _CHECKIN_TIME) {
//        this.CHECKIN_TIME = _CHECKIN_TIME;
//    }

    @Override
    public boolean equals(Object object) {
        DMR_Check_Info check = (DMR_Check_Info) object;
        if (check.SAID.equals(this.SAID) && check.TAID.equals(this.TAID)) {
            return true;
        }
        return false;
    }
}


/**
 *
 * public class BeaconCheckData {
 *     String maid;
 *     String Minor;
 *     String checkintime;
 *     public BeaconCheckData(String maid, String Minor, String checkintime){
 *         this.maid = maid;
 *         this.Minor = Minor;
 *         this.checkintime = checkintime;
 *     }
 *
 *     @Override
 *     public boolean equals(Object object) {
 *         BeaconCheckData check = (BeaconCheckData) object;
 *         if (check.maid.equals(this.maid)) {
 *             return true;
 *         }
 *         return false;
 *     }
 *
 * }
 */
