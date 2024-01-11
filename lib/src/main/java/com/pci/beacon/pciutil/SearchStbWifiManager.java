package com.pci.beacon.pciutil;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.pci.service.model.PCICheckinInfo;
import com.pci.service.network.PCIADApi;
import com.pci.service.redux.action.ActionDMRCheckin;
import com.pci.service.redux.core.Action;
import com.pci.service.redux.core.PCIStore;
import com.pci.service.util.PCILog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class SearchStbWifiManager {
//    private static final String         TAG                               = SearchStbWifiManager.class.getSimpleName();


    public static String[][]            STB_PREDEFINED_IP                 = {{"172.30.1.128", "172.30.1.129", "172.30.1.130", "172.30.1.131", "172.30.1.132"}};
    public static String[]              STB_HTTP_PORT                     = {"38520"};
    public static String                STB_DRM_PATH                      = "/description.xml";

    public static String                S_MAID                      = null;
    public static String                S_MINOR                      = null;


    private static SearchStbWifiManager instance                          = null;
    private static int                  MAX_SEARCHCONNECTEDSTB_TRY        = 2;

    private Context context;
    private WifiManager wifiManager;
    static ArrayList<DMR_Check_Info> DMRList_Check = new ArrayList<>();


    private String                      LOG_TAG                           = SearchStbWifiManager.class.getSimpleName();
    private ArrayList<DMR_Check_Info> result = new ArrayList<DMR_Check_Info>();
    //    private DMR_Check_Info stbInfo;
    private stb_info stb_info_ ;



    public static SearchStbWifiManager getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new SearchStbWifiManager(context);
        }

        return instance;
    }

    private SearchStbWifiManager(Context context)
    {
        this.context = context;
        this.wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
    }




    public ArrayList<DMR_Check_Info> findStbAddr(int prefixIndex, String maid_, String minor_)
    {
        Calendar check_time = Calendar.getInstance();
        Calendar current_time = Calendar.getInstance();
        S_MAID = maid_;
        S_MINOR = minor_;
        check_time.add(Calendar.MINUTE,5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        PCILog.d(" Compar To " + sdf.format(check_time.getTime()).toString() + " | " + sdf.format(current_time.getTime()).toString());
        new Thread(()-> {
            for(int n=0 ; n<DMRList_Check.size(); n++) {
                if(DMRList_Check.get(n).CHECKIN_TIME.compareTo(current_time) < 0){
                    DMRList_Check.remove(n);
                    n--;
                }
            }


            String requestURL = null;
            for (String stbCandidate : STB_PREDEFINED_IP[prefixIndex]) {
                int retryCount = 1;
                requestURL = "http://" + stbCandidate + ":" + STB_HTTP_PORT[prefixIndex] + STB_DRM_PATH;
//                if(stbCandidate.equals(SearchStbService.getAutoConnectedIp()))
                retryCount = MAX_SEARCHCONNECTEDSTB_TRY;
                for (int i = 0; i < retryCount; i++) {
                    HttpURLConnection httpUrlConnection = null;

                    try {
                        PCILog.d(String.format("Request URL: %s", requestURL));
                        URL urlm = new URL(requestURL);

                        httpUrlConnection = (HttpURLConnection) urlm.openConnection();
                        httpUrlConnection.setRequestMethod("GET");
                        httpUrlConnection.setConnectTimeout(2 * 1000); // Connection 타임아웃 설정
                        httpUrlConnection.setReadTimeout(2 * 1000); // Read 타임아웃 설정

                        httpUrlConnection.connect();

                        if (httpUrlConnection.getResponseCode() == 200) {
                            PCILog.d("STB found at " + stbCandidate + ":" + STB_HTTP_PORT[prefixIndex] + ":retry=" + i);
                            i = retryCount;
                            ArrayList stbinfo_ ;
                            String said = null;
                            String taid = null;
                            stb_info_ = parseStbInfo(httpUrlConnection.getInputStream());
//                            stbinfo_ = parseStbInfo(httpUrlConnection.getInputStream());
//                            said = stbinfo_.get(0).toString();
//                            taid = stbinfo_.get(1).toString();
                            said = stb_info_.serialNumber; said = said.trim();
                            taid = stb_info_.modelDescription; taid = taid.trim();


                            PCILog.d("said = " + said +" | " + "taid = " + taid);
                            if (!taid.equals("") && said.length() > 0 && !taid.equals("") && taid.length() < 20) {

                                if(!DMRList_Check.contains(new DMR_Check_Info( said, taid, check_time))) {  // 신규 체크인 발생 시
                                    DMRList_Check.add(new DMR_Check_Info( said, taid, check_time));
                                    PCILog.d("New DMR Check-In !!! ");
                                    PCICheckinInfo checkininfo_ = PCIADApi.requestDMRCheckin(taid, maid_, minor_);
                                    int old_ = checkininfo_.old();
                                    String old = null;
                                    switch (old_){
                                        case 1:
                                            old = "12";
                                            break;
                                        case 2:
                                            old = "13";
                                            break;
                                        case 3:
                                            old = "19";
                                            break;
                                        case 4:
                                            old = "25";
                                            break;
                                        case 5:
                                            old = "30";
                                            break;
                                        case 6:
                                            old = "35";
                                            break;
                                        case 7:
                                            old = "40";
                                            break;
                                        case 8:
                                            old = "45";
                                            break;
                                        case 9:
                                            old = "50";
                                            break;
                                        case 10:
                                            old = "55";
                                            break;
                                        case 11:
                                            old = "60";
                                            break;
                                        case 12:
                                            old = "65";
                                            break;
                                        default:
                                            old = "0";
                                            break;

                                    }
                                    //
//                                    if(old_ == 1 ) { old = "12"; }
//                                    else if(old_ == 2) { old = "13"; }
//                                    else if(old_ == 3) { old = "19"; }
//                                    else if(old_ == 4) { old = "25"; }
//                                    else if(old_ == 5) { old = "30"; }
//                                    else if(old_ == 6) { old = "35"; }
//                                    else if(old_ == 7) { old = "40"; }
//                                    else if(old_ == 8) { old = "45"; }
//                                    else if(old_ == 9) { old = "50"; }
//                                    else if(old_ == 10) { old = "55"; }
//                                    else if(old_ == 11) { old = "60"; }
//                                    else if(old_ == 12) { old = "65"; }
//                                    else { old = "0"; }
                                    //
                                    SimpleDateFormat toStr = new SimpleDateFormat("yyyyMMddHHmmss");

                                    Action actionDMRcheckin = new ActionDMRCheckin(checkininfo_.gaid(), old, checkininfo_.gender(), maid_,minor_,said, toStr.format(current_time.getTime()));
                                    PCIStore.getInstance(context).dispatch(actionDMRcheckin);


                                }else{                                                                      // 기존 체크인 발생 시
//                                    for(int n=0; n < DMRList_Check.size() ; n++){
//                                        if(said.equals(DMRList_Check.get(n).SAID))
//                                            DMRList_Check.set(n, new DMR_Check_Info( said, taid, DMRList_Check.get(n).CHECKIN_TIME));
//                                    }
                                    PCILog.d("DMR Check-In Exist !!! ");

                                }

                            }else{
                                PCILog.d("Can Not DMR Check-In !!! " + requestURL);
                            }

                        }
                        // httpUrlConnection.disconnect();
                    } catch (SocketTimeoutException e){
                        //PCILog.e(e);
                    } catch (Exception e) {
                        //PCILog.e(e);
                    } finally {
                        // client.getConnectionManager().shutdown();
                        httpUrlConnection.disconnect();

                    }
                    httpUrlConnection.disconnect();
                }

            }
            //current_date.add(Calendar.MINUTE, 5);
//            for(int n=0 ; n<DMRList_Check.size(); n++) {
//                PCILog.d("Final_Result --> " + DMRList_Check.get(n).SAID+" :: " + DMRList_Check.get(n).TAID+" :: " +DMRList_Check.get(n).CHECKIN_TIME_STR);
////                Log.e("dalkommjk", "final_result --> " +  " :: " + DMRList_Check.get(n).SAID+" :: " + DMRList_Check.get(n).TAID+" :: " +DMRList_Check.get(n).CHECKIN_TIME_STR);
////                Log.e("dalkommjk", "final_result2 --> " + DMR_SAID.get(DMRList_Check.get(n).SAID) + " :: " + DMR_Time.get(DMRList_Check.get(n).SAID) );
//            }

        }).start();

        if(result.size() == 0)
            return null;


        return result;


    }



    private stb_info parseStbInfo(InputStream is) throws XmlPullParserException, IOException
    {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(is, "UTF8");
        int eventType = xpp.getEventType();
        boolean isOllehTv = false;
        String said = null;
        String taid = null;
        ArrayList stbinfo = new ArrayList<>();

        while(eventType != XmlPullParser.END_DOCUMENT)
        {
            if(eventType == XmlPullParser.START_TAG)
            {
                String tagName = xpp.getName();

                if(tagName != null)
                {

                    if(tagName.equals("serialNumber")) {
                        while(eventType != XmlPullParser.TEXT)
                            eventType = xpp.next();

                        if (TextUtils.isEmpty(said))
                        {
                            said = xpp.getText();
                            said = said.trim();
                            stbinfo.add(said);
                        }
                        PCILog.d("SAID - xpp.getText() =" + xpp.getText());
                    }
                    else if(tagName.equals("modelDescription")) {
                        while(eventType != XmlPullParser.TEXT)
                            eventType = xpp.next();

                        if (TextUtils.isEmpty(taid))
                        {
                            taid = xpp.getText();
                            taid = taid.trim();
                            stbinfo.add(taid);
                        }
                        PCILog.d("TAID - xpp.getText() =" + xpp.getText());

                    }
                    stb_info_ = new stb_info(said,taid);
                }
            }
            eventType = xpp.next();
        }
        if (!taid.equals("") && said.length() > 0 && !taid.equals("") && taid.length() < 20)
            return stb_info_;

        return null;
    }

    class stb_info{
        public String serialNumber;
        public String modelDescription;

        public stb_info(String serialNumber_, String modelDescription) {
            this.serialNumber = serialNumber_;
            this.modelDescription = modelDescription;
        }
    }


}
