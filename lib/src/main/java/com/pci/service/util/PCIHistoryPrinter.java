package com.pci.service.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import android.util.Log;

public class PCIHistoryPrinter extends BroadcastReceiver {

    private static final int LEVEL = Log.INFO;
      private static final String TAG = PCIHistoryPrinter.class.getSimpleName();
      private static final String ACTION_DUMP = "com.pci.service.intent.action.DUMP";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.println(LEVEL, TAG, "---> Received intent:" + intent);
        if (context == null || intent == null) return;
        if (!ACTION_DUMP.equals(intent.getAction())) return;

          final PCISQLite sqlite = PCILog.database();
          final SQLiteDatabase sqliteDatabase = sqlite != null ? sqlite.getReadableDatabase() : null;
        if (sqliteDatabase == null) {
            Log.println(LEVEL, TAG, "Database is null");
        } else {
            final Cursor cursor = sqliteDatabase.query(
                PCISQLite.Record.Table.NAME,
                PCISQLite.Record.Table.Columns.ALL,
                null,
                null,
                null,
                null,
                null
            );

            while (cursor.moveToNext()) Log.println(Log.INFO, TAG, PCISQLite.Record.fromCursor(cursor).toString());
            cursor.close();
        }
    }

}
