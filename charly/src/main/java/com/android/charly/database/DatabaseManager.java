package com.android.charly.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.charly.data.HttpRequest;
import java.text.SimpleDateFormat;

/**
 * Created by filipe on 03-05-2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {

  private static int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "charly.db";
  public static final String TABLE_REQUESTS = "Requests";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_REQUEST_DATE = "request_date";
  public static final String COLUMN_RESPONSE_DATE = "response_date";
  public static final String COLUMN_REQUEST_DURATION = "request_duration";
  public static final String COLUMN_URL = "url";
  public static final String COLUMN_HOST = "host";
  public static final String COLUMN_PATH = "path";
  public static final String COLUMN_METHOD = "method";
  public static final String COLUMN_REQUEST_HEADERS = "request_headers";
  public static final String COLUMN_REQUEST_BODY = "request_body";
  public static final String COLUMN_RESPONSE_CODE = "response_code";
  public static final String COLUMN_RESPONSE_MESSAGE = "response_message";
  public static final String COLUMN_ERROR = "error";
  public static final String COLUMN_RESPONSE_HEADERS = "response_headers";
  public static final String COLUMN_RESPONSE_BODY = "response_body";

  public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory,
      int version) {
    super(context, DATABASE_NAME, factory, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    String query = "CREATE TABLE IF NOT EXISTS "
        + TABLE_REQUESTS
        + "("
        + COLUMN_ID
        + " LONG PRIMARY KEY AUTOINCREMENT "
        + COLUMN_REQUEST_DATE
        + " TEXT "
        + COLUMN_RESPONSE_DATE
        + " TEXT "
        + COLUMN_REQUEST_DURATION
        + " LONG "
        + COLUMN_URL
        + " TEXT "
        + COLUMN_HOST
        + " TEXT "
        + COLUMN_PATH
        + " TEXT "
        + COLUMN_METHOD
        + " TEXT "
        + COLUMN_REQUEST_HEADERS
        + " TEXT "
        + COLUMN_REQUEST_BODY
        + " TEXT "
        + COLUMN_RESPONSE_CODE
        + " INTEGER "
        + COLUMN_RESPONSE_MESSAGE
        + " TEXT "
        + COLUMN_ERROR
        + " TEXT "
        + COLUMN_RESPONSE_HEADERS
        + " TEXT "
        + COLUMN_RESPONSE_BODY
        + " TEXT "
        + ");";

    db.execSQL(query);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUESTS);
  }

  public boolean insert(String table, HttpRequest httpRequest) {
    SQLiteDatabase db = this.getWritableDatabase();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    ContentValues values = new ContentValues();
    values.put(COLUMN_ID, httpRequest.getId());
    values.put(COLUMN_REQUEST_DATE, sdf.format(httpRequest.getRequestDate()));
    values.put(COLUMN_RESPONSE_DATE, sdf.format(httpRequest.getRequestDate()));
    values.put(COLUMN_REQUEST_DURATION, httpRequest.getRequestDuration());
    values.put(COLUMN_URL, httpRequest.getUrl());
    values.put(COLUMN_HOST, httpRequest.getHost());
    values.put(COLUMN_PATH, httpRequest.getPath());
    values.put(COLUMN_METHOD, httpRequest.getMethod());
    values.put(COLUMN_REQUEST_HEADERS, httpRequest.getRequestHeaders());
    values.put(COLUMN_REQUEST_BODY, httpRequest.getRequestBody());
    values.put(COLUMN_RESPONSE_CODE, httpRequest.getResponseCode());
    values.put(COLUMN_RESPONSE_MESSAGE, httpRequest.getResponseMessage());
    values.put(COLUMN_ERROR, httpRequest.getError());
    values.put(COLUMN_RESPONSE_HEADERS, httpRequest.getResponseHeaders());
    values.put(COLUMN_RESPONSE_BODY, httpRequest.getResponseBody());

    long result = db.insert(table, null, values);
    if (result == -1) {
      return false;
    }
    return true;
  }


}
