package com.android.charly.view.requests;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.charly.R;

/**
 * Created by Jdandrade on 4/30/2017.
 */

public class RequestListFragment extends Fragment {

  private RequestAdapter requestAdapter;
  private OnListItemClickCallback listener;

  public static RequestListFragment newInstance() {
    return new RequestListFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnListItemClickCallback) {
      listener = (OnListItemClickCallback) context;
    } else {
      throw new RuntimeException("context not instanceof onlistitemclickcallback");
    }
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_request_list, container, false);
    Context context = view.getContext();
    RecyclerView recyclerView = (RecyclerView) view;
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    requestAdapter = new RequestAdapter(getContext(), listener);
    recyclerView.setAdapter(requestAdapter);

    return view;
  }
}
