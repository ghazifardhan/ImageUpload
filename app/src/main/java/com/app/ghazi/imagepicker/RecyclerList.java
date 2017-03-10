package com.app.ghazi.imagepicker;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vlk.multimager.utils.Image;

import net.yazeed44.imagepicker.model.ImageEntry;

import java.util.ArrayList;

/**
 * Created by Ghazi on 02/03/2017.
 */

public class RecyclerList extends RecyclerView.Adapter<RecyclerList.ViewHolder> {

    Context context;
    ArrayList<Image> imageHolder = new ArrayList<Image>();
    ArrayList<ImageEntry> imageHolderV2 = new ArrayList<ImageEntry>();

    public RecyclerList(Context context,ArrayList<ImageEntry> imageHolderV2){
        this.context = context;
        this.imageHolderV2 = imageHolderV2;
    }

    @Override
    public RecyclerList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerList.ViewHolder holder, int position) {
        holder.mImage.setImageBitmap(BitmapFactory.decodeFile(imageHolderV2.get(position).path));
    }

    @Override
    public int getItemCount() {
        return imageHolderV2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.imageView1);
        }
    }
}
