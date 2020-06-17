package com.example.proyectosqlite.Holder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectosqlite.R;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    public ImageView mImageView;
    public TextView mTitle, mDescription;
    View.OnCreateContextMenuListener contextClick;

    public MyHolder(@NonNull View itemView) {
        super(itemView);
        this.mImageView = itemView.findViewById(R.id.imagelv);
        this.mDescription = itemView.findViewById(R.id.descripcion);
        this.mTitle = itemView.findViewById(R.id.title);

        itemView.setOnCreateContextMenuListener(this);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        contextClick.onCreateContextMenu(menu, v, menuInfo);
    }

    public void setCreateContextMenu(View.OnCreateContextMenuListener c) {
        this.contextClick = c;
    }
}