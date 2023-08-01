package com.example.testsolutionn3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.net.URL;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private ArrayList<URL> URLList;

    public ImageAdapter(Context context, ArrayList<URL> URLList) {
        this.context = context;
        this.URLList = URLList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (URLList != null) {
            URL imageUrl = URLList.get(position);

            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.imageView);


            holder.buttonOpenUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openUrlInGoogle(imageUrl);
                }
            });

            Animation fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
            holder.imageView.startAnimation(fadeInAnimation);

            Animation slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            holder.buttonOpenUrl.startAnimation(slideUpAnimation);
        }
    }

    @Override
    public int getItemCount() {
        return URLList != null ? URLList.size() : 0;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button buttonOpenUrl;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItem);
            buttonOpenUrl = itemView.findViewById(R.id.buttonOpenUrl);
        }
    }
    private void openUrlInGoogle(URL url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
        context.startActivity(intent);
    }
}

