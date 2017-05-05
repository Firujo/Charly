package com.android.charly.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import java.util.Date;

/**
 * Created by Jdandrade on 4/30/2017.
 */

public class JsonConvertor {

  private static Gson gson = null;

  private JsonConvertor() {
  }

  public static Gson getInstance() {
    if (gson == null) {
      gson = new GsonBuilder().setPrettyPrinting()
          .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
          .registerTypeAdapter(Date.class, new DateTypeAdapter())
          .create();
    }
    return gson;
  }
}
