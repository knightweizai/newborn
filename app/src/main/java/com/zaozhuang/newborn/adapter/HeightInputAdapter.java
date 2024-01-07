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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.data.InputEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeightInputAdapter extends RecyclerView.Adapter<HeightInputAdapter.ViewHolder> {

    private List<InputEntity> list;
    private int flag;

    public HeightInputAdapter(List<InputEntity> list, int flag) {
        this.list = list;
        this.flag = flag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_input, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        InputEntity entity = list.get(position);

        if (flag == 2) {
            holder.tv_key.setText(entity.key + "小时");
        } else {
            holder.tv_key.setText(entity.key + "月");
        }

        if (holder.numTxt.getTag() != null && holder.numTxt.getTag() instanceof TextWatcher) {
            holder.numTxt.removeTextChangedListener((TextWatcher) holder.numTxt.getTag());
        }
        if (TextUtils.isEmpty(entity.value)) {
            holder.numTxt.setText("");
        } else {
            if (Float.parseFloat(entity.value) <= 0) {
                holder.numTxt.setText("");
            } else {
                holder.numTxt.setText(entity.value);
            }
        }
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                entity.value = s.toString();
            }
        };
        holder.numTxt.addTextChangedListener(textWatcher);
        holder.numTxt.setTag(textWatcher);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private EditText numTxt;
        private TextView tv_key;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_key = itemView.findViewById(R.id.tv_key);
            numTxt = itemView.findViewById(R.id.et_value);
        }

    }
}
