package com.android.charly.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import com.android.charly.R;
import com.android.charly.data.HttpRequest;
import com.android.charly.view.requests.OnListItemClickCallback;
import com.android.charly.view.requests.RequestListFragment;

public class MainActivity extends CharlyActivity implements OnListItemClickCallback {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.charly_activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setSubtitle(R.string.app_name);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, RequestListFragment.newInstance())
          .commit();
    }
  }

  @Override public void onListFragmentInteraction(HttpRequest httpRequest) {

  }
}
