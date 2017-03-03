package com.app.ghazi.imagepicker;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vlk.multimager.utils.Image;

import java.util.ArrayList;

/**
 * Created by Ghazi on 02/03/2017.
 */

public class RecyclerList extends RecyclerView.Adapter<RecyclerList.ViewHolder> {

    Context context;
    ArrayList<Image> imageHolder = new ArrayList<Image>();

    public RecyclerList(Context context,ArrayList<Image> imageHolder){
        this.context = context;
        this.imageHolder = imageHolder;
    }

    @Override
    public RecyclerList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerList.ViewHolder holder, int position) {
        holder.mImage.setImageBitmap(BitmapFactory.decodeFile(imageHolder.get(position).imagePath));
    }

    @Override
    public int getItemCount() {
        return imageHolder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.imageView1);
        }
    }
}
