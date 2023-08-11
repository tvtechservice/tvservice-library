package com.pci.service.redux.state;


import android.content.Context;


import com.google.gson.JsonArray;
import com.pci.service.model.PCICheckinInfo;
import com.pci.service.model.PCICheckinList;
import com.pci.service.model.PCIPolicy;
import com.pci.service.redux.core.State;
import com.pci.service.util.*;

import static com.pci.service.redux.state.PCIState.Type.*;

public class PCIState implements State {

    /* Android Context and ... */
      protected transient Context context;
      protected Type type = DEFAULT;

    /* 각종 ID */
      private String pid = null;         // PCI 플랫폼 개인화 ID : 약관 동의 시 서버로 부터 할당 받음
      private String stbId = null;       // PCI 플랫폼 셋탑 ID : 체크인 시 알게됨
      private String said = null;        // 암호화된 셋탑 SAID : 체크인 시 알게됨
      private String uuid = null;        // UUID : Device 정보를 조합하여 구성함
      private String adid = null;        // ADID : Google AD ID
      private String phoneNumber = null; // 마스킹된 전화번호
      private String fcmToken = null;    // FCM Token
      private String mac = null;         // Mac Address
      private String otmSuid = null;     // OTM SUID
      private String packageKey = null;  // Package Key

      private String maid = null;
      private String partnerCode = null;
      private String age = null;
      private String gender = null;
      private String regdate = null;



    /* 각종 동의 여부 */
    private boolean isTermAgreed = false;
    private boolean isAdidUseAgreed = false;
    private boolean isAdPushAgreed = false;
    private boolean isMicUseAgreed = false;
    private boolean isBleUseAgreed = false;
    private boolean isOptIn = false;

    /* PCI 정책 */
      private PCIPolicy policy = null;
      private PCICheckinList checkinlist = null;

    /* 체크인 정보 */
    private PCICheckinInfo checkininfo = null;
    private long checkinSoundId = 0;
    private long checkinSubmitTime = 0;
    private int soundDetectionFailCount = 0;

    protected PCIState(  PCIState src) {
        this.context = src.context;
        this.type = src.type;

        this.pid = src.pid;
        this.stbId = src.stbId;
        this.age = src.age;
        this.maid = src.maid;
        this.gender = src.gender;
        this.partnerCode = src.partnerCode;
        this.said = src.said;
        this.regdate = src.regdate;

//        this.uuid = src.uuid;
        this.adid = src.adid;
        this.phoneNumber = src.phoneNumber;
        this.fcmToken = src.fcmToken;
        this.mac = src.mac;
        this.otmSuid = src.otmSuid;
        this.packageKey = src.packageKey;

        this.isTermAgreed = src.isTermAgreed;
        this.isAdidUseAgreed = src.isAdidUseAgreed;
        this.isAdPushAgreed = src.isAdPushAgreed;
        this.isMicUseAgreed = src.isMicUseAgreed;
        this.isBleUseAgreed = src.isBleUseAgreed;
        this.isOptIn = src.isOptIn;

        this.policy = src.policy;
        this.checkinlist = src.checkinlist;
        this.checkininfo = src.checkininfo;

        this.checkinSoundId = src.checkinSoundId;
        this.checkinSubmitTime = src.checkinSubmitTime;
        this.soundDetectionFailCount = src.soundDetectionFailCount;
    }

    private PCIState(  Context context) {
        this.context = context;
    }

     
    public static PCIState from(  Context context) {
        PCIState state = PCIStorage.loadGson(context, PCIStorageKey.STATE).as(PCIState.class);
        if (state == null) return new PCIState(context);
        else {
            state.context = context;
            switch (state.type) {
                case DEFAULT:
                    return new PCIState1Default(state);
                case IDLE:
                    return new PCIState2Idle(state);
                case ACTIVE:
                    return new PCIState3Active(state);
            }
        }
        return state;
    }

     
    public static PCIState from(  PCIState src) {
        PCIState state = new PCIState(src);
        switch (state.type) {
            case DEFAULT:
                return new PCIState1Default(state);
            case IDLE:
                return new PCIState2Idle(state);
            case ACTIVE:
                return new PCIState3Active(state);
            default:
                throw new IllegalStateException("Invalid State Type");
        }
    }

    @SuppressWarnings("PointlessBooleanExpression")
     
    public Type maxType() {
        switch (this.type) {
            case DEFAULT:
                return DEFAULT;
            case IDLE:
                if (pid == null
                ) return DEFAULT;
                else return IDLE;
            case ACTIVE:
                if (pid == null
                ) return DEFAULT;
                else if (policy == null
                ) return IDLE;
                else return ACTIVE;
            default:
                return DEFAULT;
        }
    }

    /* Android Context */
     
    public Context getContext() {
        return context;
    }
    public void setContext(  Context context) {
        this.context = context;
    }
    public void writePersistent() {
        PCIStorage.saveGson(context, PCIStorageKey.STATE, this);
    }
    public void clearPreference() {
        PCIStorage.remove(context, PCIStorageKey.STATE);
    }
     
    public String getPackageName() {
        return context.getPackageName();
    }
    /* 각종 ID */
     
    public String getPid() {
        return pid;
    }
    public void setPid(  String pid) {
        this.pid = PCIFormatter.nullIfEmpty(pid);
    }

     
    public String getStbid() {
        return stbId;
    }
    public void setStbid(  String stbid) {
        this.stbId = PCIFormatter.nullIfEmpty(stbid);
    }
     
    public String getSaid() {return said;}
    public void setSaid(  String said) {
        this.said = PCIFormatter.nullIfEmpty(said);
    }
    public String getMaid() {return maid;}
    public void setMaid(  String maid) {
        this.maid = PCIFormatter.nullIfEmpty(maid);
    }
    public String getPartner_code() {return partnerCode;}
    public void setPartner_code( String partnerCode) { this.partnerCode = PCIFormatter.nullIfEmpty(partnerCode);    }
    public String getGender() {return gender;}
    public void setGender(  String gender) {        this.gender = PCIFormatter.nullIfEmpty(gender);    }
    public String getAge() {return age;}
    public void setAge(  String age) {        this.age = PCIFormatter.nullIfEmpty(age);    }
    public String getRegdate() {return regdate;}
    public void setRegdate(  String regdate) {        this.regdate = PCIFormatter.nullIfEmpty(regdate);    }





     
    public String getUuid() {
        return uuid;
    }
    public void setUuid(  String uuid) {
        this.uuid = PCIFormatter.nullIfEmpty(uuid);
    }

     
    public String getAdid() {
        return adid;
    }
    public void setAdid(  String adid) {
        this.adid = PCIFormatter.nullIfEmpty(adid);
    }

     
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(  String phoneNumber) {
        this.phoneNumber = PCIFormatter.maskPhoneNumber(phoneNumber);
    }

     
    public String getFcmToken() {
        return fcmToken;
    }
    public void setFcmToken(  String fcmToken) {
        this.fcmToken = PCIFormatter.nullIfEmpty(fcmToken);
    }

     
    public String getMac() {
        return mac;
    }
    public void setMac(  String mac) {
        this.mac = PCIFormatter.nullIfEmpty(mac);
    }

     
    public String getOtmSuid() {
        return otmSuid;
    }
    public void setOtmSuid(  String otmSuid) {
        this.otmSuid = PCIFormatter.nullIfEmpty(otmSuid);
    }

     
    public String getPackageKey() {
        return packageKey;
    }
    public void setPackageKey(  String packageKey) {
        this.packageKey = PCIFormatter.nullIfEmpty(packageKey);
    }


    /* 각종 동의 여부 */
    public boolean isTermAgreed() {
        return isTermAgreed;
    }

    public void setTermAgreed(boolean termAgreed) {
        isTermAgreed = termAgreed;
    }

    public boolean isAdidUseAgreed() {
        return isAdidUseAgreed;
    }

    public void setAdidUseAgreed(boolean adidUseAgreed) {
        isAdidUseAgreed = adidUseAgreed;
    }

    public boolean isAdPushAgreed() {
        return isAdPushAgreed;
    }

    public void setAdPushAgreed(boolean adPushAgreed) {
        isAdPushAgreed = adPushAgreed;
    }

    public boolean isMicUseAgreed() {
        return isMicUseAgreed;
    }

    public void setMicUseAgreed(boolean micUseAgreed) {
        isMicUseAgreed = micUseAgreed;
    }

    public boolean isBleUseAgreed() {
        return isBleUseAgreed;
    }

    public void setBleUseAgreed(boolean bleUseAgreed) {
        isBleUseAgreed = bleUseAgreed;
    }

    public boolean isOptIn() {
        return isOptIn;
    }

    public void setOptIn(boolean optIn) {
        isOptIn = optIn;
    }


    /* PCI 정책 */
     
    public PCIPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(  PCIPolicy policy) {
        this.policy = policy;
    }
    /* 체크인 정보 */

    public PCICheckinInfo getCheckinInfo() {
        return checkininfo;
    }

    public void setCheckinInfo(  PCICheckinInfo checkininfo) {
        this.checkininfo = checkininfo;
    }

     
    public PCICheckinList getCheckinlist() {
        return checkinlist;
    }

    public void setCheckinlist(  PCICheckinList checkinlist) {
        this.checkinlist = checkinlist;
    }


    /* 체크인 정보 */
    public long getCheckinSoundId() {
        return checkinSoundId;
    }

    public long getCheckinSubmitTime() {
        return checkinSubmitTime;
    }




    public void increaseSoundDetectionFailCount() {
        this.soundDetectionFailCount++;
    }

    public int getSoundDetectionFailCount() {
        return this.soundDetectionFailCount;
    }

    /* Type */
    @Override
     
    public final Type getType() {
        return type;
    }

    public enum Type {
        DEFAULT(1),
        IDLE(2),
        ACTIVE(3);

        private int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    /* other overrides */
    @Override
    public void onKeep() {
        // Todo : override this
    }

    @Override
    public void onEnter(  PCIState.Type prevStateLevel) {
        // Todo : override this
    }

    @Override
    public void onLeave(  PCIState.Type nextStateLevel) {
        // Todo : override this
    }
}
