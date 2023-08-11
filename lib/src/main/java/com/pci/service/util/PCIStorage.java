package com.pci.service.util;

import android.content.Context;
import android.content.SharedPreferences;

import android.text.TextUtils;

import java.util.Map;

import com.pci.beacon.C;
import com.pci.service.util.PCIStorageKey.StorageKey;

public class PCIStorage {

    private static final String TAG = com.pci.service.util.PCIStorage.class.getSimpleName();

    private static final int STORAGE_MODE = Context.MODE_PRIVATE;
    private static final String STORAGE_NAME = "com.pci.beacon.storage";

    public static final boolean DEFAULTT_IS_AVOID = true;

    private PCIStorage() {}

    /* SharedPreference Method Mapping */
    public static boolean contains(  Context context, @StorageKey   String key) {
        final boolean contains = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).contains(key);
        if (contains) PCILog.d(TAG, " O  : Storage[%s]", key);
        else PCILog.d(TAG, " X  : Storage[%s]", key);
        return contains;
    }

    public static Map<String, ?> getAll(  Context context) {
        PCILog.d(TAG, "ALL : Storage");
        return context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).getAll();
    }

    public static boolean getBoolean(  Context context, @StorageKey   String key, boolean fallback) {
        final boolean value = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).getBoolean(key, fallback);
        PCILog.d(TAG, "GET : Storage[%s] <-- %s (fallback : %s)", key, value, fallback);
        return value;
    }


    public static Boolean getBooleanNullable(  Context context, @StorageKey   String key) {
        if (com.pci.service.util.PCIStorage.contains(context, key)) {
            final boolean value = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).getBoolean(key, false);
            PCILog.d(TAG, "GET : Storage[%s] <-- %s (fallback : false(hard-coded))", key, value);
            return value;
        } else {
            return null;
        }
    }

    public static float getFloat(  Context context, @StorageKey   String key, float fallback) {
        final float value = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).getFloat(key, fallback);
        PCILog.d(TAG, "GET : Storage[%s] <-- %s (fallback : %s)", key, value, fallback);
        return value;
    }

    public static int getInt(  Context context, @StorageKey   String key, int fallback) {
        final int value = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).getInt(key, fallback);
        PCILog.d(TAG, "GET : Storage[%s] <-- %s (fallback : %s)", key, value, fallback);
        return value;
    }

    public static long getLong(  Context context, @StorageKey   String key, long fallback) {
        final long value = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).getLong(key, fallback);
        PCILog.d(TAG, "GET : Storage[%s] <-- %s (fallback : %s)", key, value, fallback);
        return value;
    }


    public static String getString(  Context context, @StorageKey   String key,   String fallback) {
        try {
            final String value = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).getString(key, fallback);
            PCILog.d(TAG, "GET : Storage[%s] <-- %s (fallback : %s)", key, value, fallback);
            return value;
        } catch (ClassCastException e) {
            PCILog.e(e);
            com.pci.service.util.PCIStorage.remove(context, key);
            return fallback;
        }
    }


    public static String getStringNullable(  Context context, @StorageKey   String key) {
        try {
            final String value = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).getString(key, null);
            PCILog.d(TAG, "GET : Storage[%s] <-- %s (fallback : null(hard-coded))", key, value);
            return value;
        } catch (ClassCastException e) {
            PCILog.e(e);
            com.pci.service.util.PCIStorage.remove(context, key);
            return null;
        }
    }

    /* SharedPreferences.Editor Method Mapping */
    public static void clearAll(  Context context) {
        PCILog.d(TAG, "CLEAR-ALL : Storage");
        com.pci.service.util.PCIStorage.clear(context);
    }

    public static void clear(  Context context) {
        PCILog.d(TAG, "CLR : Storage");
        context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).edit().clear().apply();
    }

    public static void put(  Context context, @StorageKey   String key, boolean value) {
        PCILog.d(TAG, "PUT : Storage[%s] <-- %s", key, value);
        context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).edit().putBoolean(key, value).apply();
    }

    public static void put(  Context context, @StorageKey   String key, float value) {
        PCILog.d(TAG, "PUT : Storage[%s] <-- %s", key, value);
        context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).edit().putFloat(key, value).apply();
    }

    public static void put(  Context context, @StorageKey   String key, int value) {
        PCILog.d(TAG, "PUT : Storage[%s] <-- %s", key, value);
        context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).edit().putInt(key, value).apply();
    }

    public static void put(  Context context, @StorageKey   String key, long value) {
        PCILog.d(TAG, "PUT : Storage[%s] <-- %s", key, value);
        context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).edit().putLong(key, value).apply();
    }

    public static void put(  Context context, @StorageKey   String key,   String value) {
        PCILog.d(TAG, "PUT : Storage[%s] <-- %s", key, value);
        context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).edit().putString(key, value).apply();
    }

    public static void remove(  Context context, @StorageKey   String key) {
        PCILog.d(TAG, "DEL : Storage[%s]", key);
        context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE).edit().remove(key).apply();
    }

    /* Counter */
    public static void countInt(  Context context, @StorageKey   String key) {
        final SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE);
        final int count = preferences.getInt(key, 0);
        preferences.edit().putInt(key, count + 1).apply();
        PCILog.d(TAG, "CNT : Storage[%s] %s <-- %s", key, count, count + 1);
    }

    public static void countLong(  Context context, @StorageKey   String key) {
        final SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(STORAGE_NAME, STORAGE_MODE);
        final long count = preferences.getLong(key, 0);
        preferences.edit().putLong(key, count + 1).apply();
        PCILog.d(TAG, "CNT : Storage[%s] %s <-- %s", key, count, count + 1);
    }

    /* Key Generator */

    public static String key(  Object key1,   Object key2) {
        return key1 + "." + key2;
    }


    public static String key(  Object key1,   Object key2,   Object key3) {
        return key1 + "." + key2 + "." + key3;
    }

    /* Gson Converter */

    public static PCIGsonConverter loadGson(  Context context, @StorageKey   String key) {
        return new PCIGsonConverter(com.pci.service.util.PCIStorage.getStringNullable(context, key));
    }

    public static <T> void saveGson(  Context context, @StorageKey   String key,   T model) {
        final String value = PCIGsonConverter.load(model).take();
        if (value == null) com.pci.service.util.PCIStorage.remove(context, key);
        else com.pci.service.util.PCIStorage.put(context, key, value);
    }

    public static class PCIGsonConverter implements C {


        private final String json;

        private PCIGsonConverter(  String json) {
            this.json = json;
        }


        public <T> T as(Class<T> clazz) {
            if (TextUtils.isEmpty(json)) return null;
            else try {
                return gson.fromJson(json, clazz);
            } catch (Throwable e) {
                // JsonSyntaxException
                return null;
            }
        }


        public String take() {
            return json;
        }


        public static PCIGsonConverter load(  String json) {
            return new PCIGsonConverter(json);
        }


        public static <T> PCIGsonConverter load(  T model) {
            return new PCIGsonConverter(gson.toJson(model));
        }
    }
}

