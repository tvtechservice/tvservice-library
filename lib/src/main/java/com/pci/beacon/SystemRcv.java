//package com.pci.beacon;
//
//import static com.pci.beacon.PCI.sysrcv;
//import static com.pci.beacon.pciutil.SearchStbWifiManager.S_MAID;
//import static com.pci.beacon.pciutil.SearchStbWifiManager.S_MINOR;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import android.net.ConnectivityManager;
//
//import android.net.NetworkInfo;
//import android.os.Handler;
//
//import com.pci.beacon.pciutil.SearchStbWifiManager;
//import com.pci.service.util.PCILog;
//
//
//public class SystemRcv extends BroadcastReceiver{
//    public boolean dmr_yn = false;
//
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        if(!dmr_yn){
//            dmr_yn = true;
//            try {
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                    Log.e("dalkommjk", "boolean ::: " + dmr_yn + " // intent ::: "+ intent.getAction().toString() + " " + activeNetwork.getType());
//                        if (activeNetwork.getType() == 0 && S_MAID != null && S_MINOR != null) {
//                            SearchStbWifiManager.getInstance(context).findStbAddr(0, S_MAID, S_MINOR);
//                        } else {
//                            PCILog.d("MAID/MINOR is NULL !!" );
//                        }
//
//                        //context.unregisterReceiver(sysrcv);
//                        dmr_yn = false;
//
//                    }
//                }, 3 * 1000);
//            }catch (Exception e){
//                PCILog.d("WiFi State Get Fail !!");
//            }
//
//        }
////        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
////        {
//////            BroadcastReceiver sysrcv = new SystemRcv();
//////            IntentFilter wifi_filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//////            context.registerReceiver(sysrcv, wifi_filter);
////
////            Log.e("dalkommjk", "Regi ::: Connectivity_Action");
////        }
//
//    }
//
//
//}
