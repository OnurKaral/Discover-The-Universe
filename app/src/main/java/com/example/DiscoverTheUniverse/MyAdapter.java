package com.example.DiscoverTheUniverse;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ImageViewHolder> {
    private Context mContext;
    private List<User> mUser;

    public MyAdapter(Context context, List<User> users) {
        mContext = context;
        mUser = users;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_items, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        User usercurrent = mUser.get(position);
        holder.mtextView.setText(usercurrent.getMimageTitle());
        Picasso.get().load(usercurrent.getDownloadurl()).fit().centerCrop().into(holder.mimageView);
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView mtextView;
        public ImageView mimageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            mimageView = itemView.findViewById(R.id.card_imageview);
            mtextView = itemView.findViewById(R.id.card_textview);


        }
    }
}

