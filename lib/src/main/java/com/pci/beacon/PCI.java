package com.pci.beacon;


import static android.content.Context.WIFI_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.google.gson.JsonArray;
import com.pci.beacon.pciutil.DMR_Check_Info;
import com.pci.beacon.pciutil.PCILog;
import com.pci.beacon.pciutil.SearchStbWifiManager;
import com.pci.service.model.PCIAD1002;
import com.pci.service.network.PCIADApi;
import com.pci.service.network.PCIApi;
import com.pci.service.redux.action.ActionAgreeTerms;
import com.pci.service.redux.action.ActionCheckinList;
import com.pci.service.redux.action.ActionDMRCheckin;
import com.pci.service.redux.core.Action;
import com.pci.service.redux.core.PCIStore;
import com.pci.service.redux.core.State;
import com.pci.service.redux.state.PCIState;
import com.pci.service.util.PCIStorage;
import com.pci.service.util.PCIStorageKey;
//import com.pci.service.redux.state.PCIState;

import java.security.Policy;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class PCI {

    //    private @interface PCICode {}
//    public static final int SUCCESS = 0x00000000;
//    public static final int INVALID_ARGUMENTS = 0x00002000;
//    public static final int NO_MATCHED_NOTIFIER_NAME = 0x00002002;
//    public static final int PERMISSIONS_REQUEST = 0x0000001;
//    public static boolean runable = true;
    public boolean checPermission = false;

    /* Internal usage */
    public static final String VERSION = BuildConfig.VERSION_NAME;

    @SuppressLint("StaticFieldLeak")
    private static volatile PCI singleton;


    private Context context;

    private PCI(Context context) {
        this.context = context;
    }
    public static PCI with( Context context) {
        if (context == null) throw new NullPointerException("Context is null");
        else if (singleton == null) {
            synchronized (PCI.class) {
                if (singleton == null) {
                    singleton = new PCI(context);
                }
            }
        }
        return singleton;
    }

    public void beaconStart(String AdId, String parterCode) {
        PCILog.d("beaconStart()");
        if(AdId.length() != 36 || parterCode.length() !=4) {
            PCILog.d("[Beacon] ADID or PartnerCode Error !!");
            return;
        }

        if(onCheckPermission(context)) {
            if (!PCIAdvertise.getInstance().isStarted()) {
                try {
                    PCIAdvertise.getInstance().start(context, "start", AdId, parterCode);

                }catch (Exception e){
                    PCILog.d("Beacon Advertising Start error!! ( Bluetooth Advertise Permission, ete ... )");
                }
                Timer timer = new Timer(true);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        beaconStop();
                    }
                };
                timer.schedule(timerTask, 3 * 60 * 1000);
            } else {
                PCILog.d("Already Beacon Advertising ... ");
            }
        }
    }

    public void beaconStop() {
        try {
            PCIAdvertise.getInstance().finish();
            PCILog.d(" Beacon Advertising Stop !!" );
        }catch (Exception e){ PCILog.d("Beacon Advertising Stop error!!"); }
    }

    public void DMRStart(String AdId, String parterCode) {   // DMR체크인 추가 by dalkommjk | v5.4.3 | 2023-08-11
        PCILog.d("DMR Start !!");
        if(AdId.length() != 36 || parterCode.length() !=4) {
            PCILog.d("[DMR] ADID or PartnerCode Error !!");
            return;
        }else {
            int patner = Integer.parseInt(parterCode) + 100;
            parterCode = Integer.toString(patner);
        }

//        sysrcv = new SystemRcv();
//        /** 리시버 등록 */
//        try {
//            context.unregisterReceiver(sysrcv);
//        } catch (Exception e) {
//            com.pci.service.util.PCILog.d("PCI_Error - 008 ::: Broadcast UnRegister Error !!");
//        } finally {
//            IntentFilter wifi_filter = new IntentFilter(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION); //android.net.wifi.supplicant.CONNECTION_CHANGE
//            context.registerReceiver(sysrcv, wifi_filter);
//        }
        /** Wifi 상태조회  */
        try {
//            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            if(wifiManager.isWifiEnabled() == true) {
                SearchStbWifiManager.getInstance(context).findStbAddr(0, AdId, parterCode);
//            }else{
//                PCILog.d("WiFi-State Disable !!");
//            }
        } catch (Exception e) {
            PCILog.d("PCI_Error - 009 ::: WiFi State Get Error !! ");
        }

    }

    public boolean onCheckPermission(Context context){
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        if (Build.VERSION.SDK_INT >= 31) {
            if (packageManager.checkPermission(Manifest.permission.BLUETOOTH_ADVERTISE, packageName) != PackageManager.PERMISSION_GRANTED) {
                PCILog.d("Need to Bluetooth Permission!" );
                return false;
            } else {
                PCILog.d("Bluetooth Permission is OK!");
                return true;
            }
        }else{
            if ((packageManager.checkPermission(Manifest.permission.BLUETOOTH, packageName) != PackageManager.PERMISSION_GRANTED) || (packageManager.checkPermission(Manifest.permission.BLUETOOTH_ADMIN, packageName) != PackageManager.PERMISSION_GRANTED) ){
                PCILog.d("Need to Bluetooth Permission!" );
                return false;
            } else{
                PCILog.d("Bluetooth Permission is OK!");
                return true;
            }
        }
    }



}
