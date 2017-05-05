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
package com.android.charly.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.android.charly.utils.NotificationHelper;

public abstract class CharlyActivity extends AppCompatActivity {

  private static boolean inForeground;

  private NotificationHelper notificationHelper;

  public static boolean isInForeground() {
    return inForeground;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    notificationHelper = new NotificationHelper(this);
  }

  @Override protected void onPause() {
    super.onPause();
    inForeground = false;
  }

  @Override protected void onResume() {
    super.onResume();
    inForeground = true;
    notificationHelper.dismiss();
  }
}
