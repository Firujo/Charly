package com.android.charly.view.requests;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.charly.R;
import java.util.Random;

/**
 * Created by jdandrade on 05/05/2017.
 */

class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

  private Context context;

  public RequestAdapter(Context context, OnListItemClickCallback listener) {
    this.context = context;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View requestItemView = inflater.inflate(R.layout.request_list_item, parent, false);
    return new ViewHolder(requestItemView);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int position) {
    String[] arr1 = { "40ms", "35ms", "34ms", "56ms" };
    String[] arr2 = { "13kb", "14kb", "13kb", "12kb" };
    Random random1 = new Random();
    Random random2 = new Random();
    viewHolder.code.setText("200");
    viewHolder.host.setText("api.github.com");
    viewHolder.path.setText("/users/octocat/repos");
    viewHolder.time.setText("20:31:0" + position);
    viewHolder.duration.setText(arr1[random1.nextInt(arr1.length)]);
    viewHolder.size.setText(arr2[random2.nextInt(arr2.length)]);
  }

  @Override public int getItemCount() {
    return 10;
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView code;
    public final TextView host;
    public final TextView path;
    public final TextView time;
    public final TextView duration;
    public final TextView size;

    public ViewHolder(View itemView) {
      super(itemView);
      code = (TextView) itemView.findViewById(R.id.http_code);
      path = (TextView) itemView.findViewById(R.id.path);
      host = (TextView) itemView.findViewById(R.id.host);
      time = (TextView) itemView.findViewById(R.id.time);
      duration = (TextView) itemView.findViewById(R.id.duration);
      size = (TextView) itemView.findViewById(R.id.size);
    }

    @Override public void onClick(View v) {
      // TODO: 10/05/2017
    }
  }
}
