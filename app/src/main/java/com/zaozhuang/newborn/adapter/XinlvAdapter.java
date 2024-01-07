package com.zaozhuang.newborn.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.data.InputEntity;

import java.util.List;

public class XinlvAdapter extends RecyclerView.Adapter<XinlvAdapter.ViewHolder> {

    private List<InputEntity> list;
    private int flag;

    public XinlvAdapter(List<InputEntity> list, int flag) {
        this.list = list;
        this.flag = flag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xinlv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        InputEntity entity = list.get(position);

        holder.tv_key.setText(entity.key);
        holder.tv_value.setText(entity.value);

        holder.tv_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                entity.value = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_value;
        private TextView tv_key;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_key = itemView.findViewById(R.id.tv_key);
            tv_value = itemView.findViewById(R.id.tv_value);
        }
    }
}
