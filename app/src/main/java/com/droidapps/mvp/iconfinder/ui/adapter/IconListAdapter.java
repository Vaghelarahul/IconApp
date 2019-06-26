package com.droidapps.mvp.iconfinder.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidapps.mvp.iconfinder.R;
import com.droidapps.mvp.iconfinder.lib.ImageLoader;
import com.droidapps.mvp.iconfinder.pojo.IconData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IconListAdapter extends RecyclerView.Adapter<IconListAdapter.IconViewHolder> {

    private List<IconData> iconList;
    private DownloadListener downloadListener;
    private ImageLoader imageLoader;

    private Context context;

    public IconListAdapter(List<IconData> iconList, ImageLoader imageLoader, DownloadListener downloadListener) {
        this.iconList = iconList;
        this.imageLoader = imageLoader;
        this.downloadListener = downloadListener;

        context = (Context) downloadListener;
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {

        IconData data = iconList.get(position);

        try {
            holder.iconName.setText(data.getIconName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            imageLoader.loadImage(holder.iconView, data.getIconUrls().getPreviewUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.iconDownloadView.setOnClickListener(v -> {
            downloadListener.onDownload(iconList.get(position).getIconUrls().getPreviewUrl());
        });
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    public void setIconList(List<IconData> iconList) {
        this.iconList.addAll(iconList);
        notifyDataSetChanged();
    }

    class IconViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon_view)
        ImageView iconView;

        @BindView(R.id.icon_download)
        ImageView iconDownloadView;

        @BindView(R.id.icon_name)
        TextView iconName;

        IconViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
