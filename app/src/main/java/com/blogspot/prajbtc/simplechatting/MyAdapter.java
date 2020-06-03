package com.blogspot.prajbtc.simplechatting;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    ArrayList<Item> arrayList;

    Context context;

    public MyAdapter(Context context, ArrayList<Item> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.single_a,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        TextView textView=new TextView(context);
        textView.setGravity(Gravity.CENTER);

        if(arrayList.get(position).isA()){
            holder.rootLayout.setGravity(Gravity.RIGHT);
            textView.setText(arrayList.get(position).getMessage());
            textView.setGravity(Gravity.RIGHT);
            holder.rootLayout.addView(textView);
        }
        else
        {
            holder.rootLayout.setGravity(Gravity.LEFT);
            textView.setText(arrayList.get(position).getMessage());
            textView.setGravity(Gravity.LEFT);
            holder.rootLayout.addView(textView);

        }
}

public void addItem(Item item){
        arrayList.add(item);
}
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayout;
        TextView message;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
             rootLayout=itemView.findViewById(R.id.lay);
             message=itemView.findViewById(R.id.msgBTv);
        }
    }
}
