package com.android.charly.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.charly.data.HttpRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.Callable;

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
        + " LONG "
        + COLUMN_RESPONSE_DATE
        + " LONG "
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

    ContentValues values = new ContentValues();
    values.put(COLUMN_ID, httpRequest.getId());
    values.put(COLUMN_REQUEST_DATE, httpRequest.getRequestDate().getTime());
    values.put(COLUMN_RESPONSE_DATE, httpRequest.getRequestDate().getTime());
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

  public void deleteRequestsByRequestDate(String table, Date requestDate) {
    SQLiteDatabase db = this.getWritableDatabase();
    long date = requestDate.getTime();
    db.execSQL("DELETE FROM "
        + table
        + " WHERE "
        + COLUMN_REQUEST_DATE
        + " >= "
        + requestDate.getTime()
        + ";");
  }

  public void deleteRequestsByHost(String table, String host) {
    SQLiteDatabase db = this.getWritableDatabase();

    db.execSQL("DELETE FROM " + table + " WHERE " + COLUMN_HOST + " =\"" + host + "\";");
  }

  public Cursor selectAll(String table) {
    SQLiteDatabase db = this.getWritableDatabase();
    String query = " SELECT * FROM " + table;
    Cursor c = db.rawQuery(query, null);
    db.close();
    return c;
  }

  public Cursor selectByRequestDate(String table, String field, Date requestDate) {
    SQLiteDatabase db = this.getWritableDatabase();
    String dbResult = "";
    long date = requestDate.getTime();
    String query = " SELECT * FROM " + table + " WHERE " + COLUMN_REQUEST_DATE + " >= " + date;

    Cursor c = db.rawQuery(query, null);
    //c.moveToFirst();
    //while (!c.isAfterLast()) {
    //  if (c.getString(c.getColumnIndex(COLUMN_REQUEST_DATE)) != null) {
    //    dbResult += c.getString(c.getColumnIndex(field));
    //    dbResult += "\n";
    //    c.moveToNext();
    //  }
    //}
    db.close();
    return c;
  }

  public boolean update(String table, HttpRequest httpRequest, long id) {

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_ID, httpRequest.getId());
    values.put(COLUMN_REQUEST_DATE, httpRequest.getRequestDate().getTime());
    values.put(COLUMN_RESPONSE_DATE, httpRequest.getRequestDate().getTime());
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

    db.update(table, values, "ID = ?", new String[] { Long.toString(id) });
    return true;
  }
}
