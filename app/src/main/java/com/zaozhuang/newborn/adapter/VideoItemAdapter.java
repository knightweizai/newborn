package com.zaozhuang.newborn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.data.InputEntity;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.util.GlideUtils;

import java.io.File;
import java.util.List;

public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.ViewHolder> {

    private Context context;
    private List<InputEntity> list;
    private OnItemClickListener listener;

    public VideoItemAdapter(Context context, List<InputEntity> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        listener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        InputEntity entity = list.get(position);

//        GlideUtils.loadCircleImage(context, holder.iv_head, new File(entity.imgPath),R.mipmap.default_avatar);

        holder.tv_key.setText(entity.key);
//        holder.tv_birthday.setText(entity.birthday);
//
        holder.rl_item.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position, entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

//        private ImageView iv_head;
        private TextView tv_key;
        private RelativeLayout rl_item;
//        private TextView tv_birthday;
//        private LinearLayout ll_baby;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            ll_baby = itemView.findViewById(R.id.ll_baby);
//            iv_head = itemView.findViewById(R.id.iv_head);
            rl_item = itemView.findViewById(R.id.rl_item);
            tv_key = itemView.findViewById(R.id.tv_key);
//            tv_birthday = itemView.findViewById(R.id.tv_birthday);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, InputEntity path);
    }
}
