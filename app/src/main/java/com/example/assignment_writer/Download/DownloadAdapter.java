package com.example.assignment_writer.Download;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_writer.Edit.DownloadView;
import com.example.assignment_writer.History.Model;
import com.example.assignment_writer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {
    private List<DownloadModel> itemList;
    private Context context;

    public DownloadAdapter(Context context, List<DownloadModel> itemList){
        this.context=context;
        this.itemList=itemList;
    }

    @NonNull
    @Override
    public DownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DownloadAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.download_view,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.ViewHolder holder, int position) {

        DownloadModel item=itemList.get(position);

        holder.projectType.setText(item.getProjectType());
        holder.type.setText(item.getType());
        holder.title.setText(item.getTitle());
        holder.character.setText(item.getCharacter());
        holder.language.setText(item.getLanguage());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadModel model= itemList.get(position);

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("DownloadDb");
                reference.child(model.getKey()).removeValue()
                        .addOnSuccessListener(a->{

                        })
                        .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        holder.viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadModel model= itemList.get(position);

                Intent intent=new Intent(context, DownloadView.class);
                intent.putExtra("key",model.getKey());
                Toast.makeText(context, ""+model.getKey(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView projectType,type,title,character,language,date,time,viewbtn;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            projectType=itemView.findViewById(R.id.projectType);
            type=itemView.findViewById(R.id.type);
            title=itemView.findViewById(R.id.text);
            character=itemView.findViewById(R.id.character);
            language=itemView.findViewById(R.id.language);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            viewbtn=itemView.findViewById(R.id.view_button);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
