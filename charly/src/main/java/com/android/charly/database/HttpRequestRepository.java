package com.android.charly.database;

import com.android.charly.data.HttpRequest;
import java.util.List;

/**
 * Created by filipe on 13-05-2017.
 */

public class HttpRequestRepository {

  private DatabaseManager databaseManager;

  public HttpRequestRepository(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  public List<HttpRequest> getAll(){
    return databaseManager.getHttpRequests();
  }

  public void removeAll(){
    databaseManager.clearHttpRequests();
  }

}
