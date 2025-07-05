package com.example.assignment_writer.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_writer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        Model item=itemList.get(position);
        holder.projectType.setText(item.getProjectType());
        holder.type.setText(itemList.get(position).getType());
        holder.title.setText(item.getTitle());
        holder.word.setText(item.getWord());
        holder.language.setText(item.getLanguage());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model model = itemList.get(position);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("History");
                ref.child(model.getKey()).removeValue()
                        .addOnSuccessListener(aVoid -> {

                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView projectType,type,title,word,language,date,time;
       ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            projectType=itemView.findViewById(R.id.projectType);
            type=itemView.findViewById(R.id.type);
            title=itemView.findViewById(R.id.title);
            word=itemView.findViewById(R.id.word);
            language=itemView.findViewById(R.id.language);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
