package com.example.todoapp;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    List<Data> data;
    int id;
    OnItemRemovedListener listener;





    public DataAdapter(List<Data> data) {
        this.data = data;
    }

    public void setOnItemRemovedListener(OnItemRemovedListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void  setData(List<Data> data){
        this.data=data;
        notifyDataSetChanged();
    }

    public interface OnItemRemovedListener {
        void onItemRemoved(Data removedItem);
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        Data currentData = data.get(position);
        holder.description.setText(currentData.description);
        holder.scheduledDate.setText(DateUtils.formatDate(currentData.scheduledDate));
        holder.checkBox.setChecked(currentData.getStatus().equals("COMPLETED"));


        if (currentData.getStatus().equals("COMPLETED")) {
            holder.itemView.setAlpha(0.5f);
            holder.checkBox.setChecked(true);
            holder.checkBox.setClickable(false);
            holder.checkBox.setEnabled(false);
        } else {
            holder.itemView.setEnabled(true);
            holder.itemView.setAlpha(1.0f);
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=holder.getAdapterPosition();
                removeFromRecyclerView1(id);
                Log.d("RC","working");
            }
        });
    }
    private void removeFromRecyclerView1(int id) {
         Data removedItem=data.remove(id);
         removedItem.setStatus("COMPLETED");
            notifyItemRemoved(id);
        if (listener != null) {
            listener.onItemRemoved(removedItem);
        }
            Log.d("PI","removed");

    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder  extends  RecyclerView.ViewHolder{
       TextView description,scheduledDate;
        CheckBox checkBox;

        public ViewHolder(@NonNull View dataView) {
            super(dataView);

            description = dataView.findViewById(R.id.textView1);
            scheduledDate = dataView.findViewById(R.id.date1);
            checkBox=dataView.findViewById(R.id.checkBox1);

        }
    }
}
