//package com.pci.service.util;
//
//import android.content.Context;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//
//
//public class PCINetworkUtil {
//
//    public static WifiInfo getWifiInfo(  Context context) {
//        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        if (wifiManager == null) return null;
//
//        return wifiManager.getConnectionInfo();
//    }
//
//    public static String getBSSID(  Context context) {
//        WifiInfo wifiInfo = getWifiInfo(context);
//        if (wifiInfo == null) return "nullWifiManager";
//
//        return PCIFormatter.newMac(wifiInfo.getBSSID());
//    }
//
//    public static String getSSID(  Context context) {
//        WifiInfo wifiInfo = getWifiInfo(context);
//        if (wifiInfo == null) return "nullWifiManager";
//
//        return PCIFormatter.newMac(wifiInfo.getSSID());
//    }
//}
