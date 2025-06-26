package com.example.assignment_writer.Download;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_writer.R;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

    private List<Model>itemList;
    private Context context;

    public ModelAdapter(Context context, List<Model> itemList){
        this.context=context;
        this.itemList=itemList;
    }
    @NonNull
    @Override
    public ModelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.model_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ModelAdapter.ViewHolder holder, int position) {

        holder.textView.setText(itemList.get(position).getText());
        holder.time.setText(itemList.get(position).getText());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView time,textView;
       ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.text);
            time=itemView.findViewById(R.id.time);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
