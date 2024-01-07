package com.zaozhuang.newborn.adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.data.InputEntity;

import java.util.List;

public class TransmitAdapter extends RecyclerView.Adapter<TransmitAdapter.ViewHolder> {

    private List<InputEntity> list;


    public TransmitAdapter(List<InputEntity> list) {
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transmit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        InputEntity entity = list.get(position);

        holder.tv_key.setText(entity.key + "月");

        holder.ll_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("jinyan","点击了：  "+entity.key + "月");
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_key;
        private LinearLayout ll_key;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_key = itemView.findViewById(R.id.tv_key);
            ll_key = itemView.findViewById(R.id.ll_key);
        }

    }
}
