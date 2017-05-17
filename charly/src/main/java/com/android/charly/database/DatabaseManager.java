package com.android.charly.database;

import android.database.Cursor;
import com.android.charly.data.HttpRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by filipe on 13-05-2017.
 */

public class DatabaseManager {

  private Database database;

  public DatabaseManager(Database database) {
    this.database = database;
  }

  public List<HttpRequest> getHttpRequests() {
    List<HttpRequest> httpRequestList = new ArrayList<>();
    Cursor cursor = database.selectAll(Database.TABLE_REQUESTS);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      httpRequestList.add(buildHttpRequestFromCursor(cursor));
      cursor.moveToNext();
    }

    return httpRequestList;
  }

  public void clearHttpRequests() {
    database.deleteAllRequests();
  }

  public void addHttpRequest(HttpRequest httpRequest) {
    database.insert(Database.TABLE_REQUESTS, httpRequest);
  }

  public HttpRequest buildHttpRequestFromCursor(Cursor cursor) {
    HttpRequest httpRequest = new HttpRequest();
    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_ID)) != null) {
      httpRequest.setId(cursor.getLong(cursor.getColumnIndex(Database.COLUMN_ID)));
    }
    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_REQUEST_DATE)) != null) {
      httpRequest.setRequestDate(
          new Date(cursor.getLong(cursor.getColumnIndex(Database.COLUMN_REQUEST_DATE))));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_RESPONSE_DATE)) != null) {
      httpRequest.setResponseDate(
          new Date(cursor.getLong(cursor.getColumnIndex(Database.COLUMN_RESPONSE_DATE))));
    }
    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_REQUEST_DURATION)) != null) {
      httpRequest.setRequestDuration(
          cursor.getLong(cursor.getColumnIndex(Database.COLUMN_REQUEST_DURATION)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_URL)) != null) {
      httpRequest.setUrl(cursor.getString(cursor.getColumnIndex(Database.COLUMN_URL)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_HOST)) != null) {
      httpRequest.setHost(cursor.getString(cursor.getColumnIndex(Database.COLUMN_HOST)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_PATH)) != null) {
      httpRequest.setPath(cursor.getString(cursor.getColumnIndex(Database.COLUMN_PATH)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_METHOD)) != null) {
      httpRequest.setMethod(cursor.getString(cursor.getColumnIndex(Database.COLUMN_METHOD)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_REQUEST_HEADERS)) != null) {
      httpRequest.setRequestHeaders(
          cursor.getString(cursor.getColumnIndex(Database.COLUMN_REQUEST_HEADERS)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_REQUEST_BODY)) != null) {
      httpRequest.setRequestBody(
          cursor.getString(cursor.getColumnIndex(Database.COLUMN_REQUEST_BODY)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_RESPONSE_CODE)) != null) {
      httpRequest.setResponseCode(
          cursor.getInt(cursor.getColumnIndex(Database.COLUMN_RESPONSE_CODE)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_RESPONSE_MESSAGE)) != null) {
      httpRequest.setResponseMessage(
          cursor.getString(cursor.getColumnIndex(Database.COLUMN_RESPONSE_MESSAGE)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_ERROR)) != null) {
      httpRequest.setError(cursor.getString(cursor.getColumnIndex(Database.COLUMN_ERROR)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_RESPONSE_HEADERS)) != null) {
      httpRequest.setResponseHeaders(
          cursor.getString(cursor.getColumnIndex(Database.COLUMN_RESPONSE_HEADERS)));
    }

    if (cursor.getString(cursor.getColumnIndex(Database.COLUMN_RESPONSE_BODY)) != null) {
      httpRequest.setResponseBody(
          cursor.getString(cursor.getColumnIndex(Database.COLUMN_RESPONSE_BODY)));
    }
    return httpRequest;
  }
}
