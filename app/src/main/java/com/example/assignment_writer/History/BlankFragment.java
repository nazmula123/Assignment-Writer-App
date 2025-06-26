package com.example.assignment_writer.History;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment_writer.Download.Model;
import com.example.assignment_writer.Download.ModelAdapter;
import com.example.assignment_writer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference reference;
    ModelAdapter adapter;
    private List<Model>list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_blank, container, false);

        list=new ArrayList<>();

         recyclerView=view.findViewById(R.id.recyclerView);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         reference= FirebaseDatabase.getInstance().getReference("HistoryDatabase");

         AddedHistoryDatabase();

         return view;
    }
    private void AddedHistoryDatabase(){

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Model item=dataSnapshot.getValue(Model.class);
                    if(item!=null){
                        list.add(item);
                    }
                }
                adapter=new ModelAdapter(getContext(),list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "network no available", Toast.LENGTH_SHORT).show();
            }
        });
    }
}