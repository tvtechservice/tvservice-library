package com.pci.service.util;


import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class PCIStorageKey {

    @Retention(SOURCE)

    @interface StorageKey {}

      public static final String UUID = "uuid";
      public static final String STATE = "state";
      public static final String AVOID_APP_PERMISSION_MONITOR = "avoid_app_permission_monitor";
      public static final String BITSOUND_JOB_TRIGGER_TIME = "bitsound_job_trigger_time";
      public static final String UPLOAD_APP_LIST_TRIGGER_TIME = "upload_app_list_trigger_time";
      public static final String APP_VERSION = "app_version";
}
