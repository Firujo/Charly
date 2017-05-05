package com.android.charly.view.requests;

import com.android.charly.data.HttpRequest;

/**
 * Created by jdandrade on 05/05/2017.
 */

public interface OnListItemClickCallback {
  void onListFragmentInteraction(HttpRequest httpRequest);
}
