package com.pci.beacon;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.pci.beacon.pciutil.PCILog;

import java.util.Timer;
import java.util.TimerTask;



public class PCI {

//    private @interface PCICode {}
//    public static final int SUCCESS = 0x00000000;
//    public static final int INVALID_ARGUMENTS = 0x00002000;
//    public static final int NO_MATCHED_NOTIFIER_NAME = 0x00002002;
//    public static final int PERMISSIONS_REQUEST = 0x0000001;
//    public static boolean runable = true;
    public boolean checPermission = false;



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
        String tAdid = AdId;
        String pCode = parterCode;

        if(onCheckPermission(context)) {
            if (!PCIAdvertise.getInstance().isStarted()) {
                try {
                    if (checPermission) {
                        PCIAdvertise.getInstance().start(context, "start", tAdid, pCode);
                    } else {
                        PCILog.d("This devices is not supported. ( Bluetooth Advertise Permission Error !! )");
                    }
                }catch (Exception e){
                    PCILog.d("Beacon Advertising Start error!!");
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

    public boolean onCheckPermission(Context context){
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        if (Build.VERSION.SDK_INT >= 31) {
            if (packageManager.checkPermission(Manifest.permission.BLUETOOTH_ADVERTISE, packageName) != PackageManager.PERMISSION_GRANTED) {
                PCILog.d("Need to Bluetooth Permission!" );
                checPermission = false;
                return false;
            } else {
                PCILog.d("Bluetooth Permission is OK!");
                checPermission = true;
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
