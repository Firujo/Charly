/*
 * Copyright (C) 2017 Jeff Gilfelt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.charly.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.LongSparseArray;
import com.android.charly.Charly;
import com.android.charly.R;
import com.android.charly.data.HttpRequest;
import com.android.charly.view.CharlyActivity;

public class NotificationHelper {

  private static final int NOTIFICATION_ID = 1138;
  private static final int BUFFER_SIZE = 10;

  private static final LongSparseArray<HttpRequest> requestBuffer = new LongSparseArray<>();
  private static int requestCount;

  private final Context context;
  private final NotificationManager notificationManager;

  public NotificationHelper(Context context) {
    this.context = context;
    notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
  }

  public static synchronized void clearBuffer() {
    requestBuffer.clear();
    requestCount = 0;
  }

  private static synchronized void addToBuffer(HttpRequest httpRequest) {
    requestCount++;
    requestBuffer.put(1, httpRequest);
    if (requestBuffer.size() > BUFFER_SIZE) {
      requestBuffer.removeAt(0);
    }
  }

  public synchronized void show(HttpRequest httpRequest) {
    addToBuffer(httpRequest);
    if (!CharlyActivity.isInForeground()) {
      NotificationCompat.Builder mBuilder =
          new NotificationCompat.Builder(context).setContentIntent(
              PendingIntent.getActivity(context, 0, Charly.getLaunchIntent(context), 0))
              .setLocalOnly(true)
              .setSmallIcon(R.mipmap.ic_launcher)
              .setContentTitle(httpRequest.getHost());
      NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
      int count = 0;
      for (int i = requestBuffer.size() - 1; i >= 0; i--) {
        if (count < BUFFER_SIZE) {
          if (count == 0) {
            mBuilder.setContentText(requestBuffer.valueAt(i).getNotificationText());
          }
          inboxStyle.addLine(requestBuffer.valueAt(i).getNotificationText());
        }
        count++;
      }
      mBuilder.setAutoCancel(true);
      mBuilder.setStyle(inboxStyle);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        mBuilder.setSubText(String.valueOf(requestCount));
      } else {
        mBuilder.setNumber(requestCount);
      }
      notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
  }

  public void dismiss() {
    notificationManager.cancel(NOTIFICATION_ID);
  }
}
