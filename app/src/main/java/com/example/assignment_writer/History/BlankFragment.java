package com.example.assignment_writer.History;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment_writer.Download.Model;
import com.example.assignment_writer.Download.ModelAdapter;
import com.example.assignment_writer.R;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment {

    RecyclerView recyclerView;
    ModelAdapter adapter;
    List<Model>modelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_blank, container, false);

         recyclerView=view.findViewById(R.id.recyclerView);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         modelList=new ArrayList<>();

          modelList.add(new Model("4/08/2025","text hire...."));
          modelList.add(new Model("4/08/2025","text hire...."));
          modelList.add(new Model("4/08/2025","text hire...."));
          modelList.add(new Model("4/08/2025","text hire...."));
          modelList.add(new Model("4/08/2025","text hire...."));

          adapter=new ModelAdapter(getContext(),modelList);
          recyclerView.setAdapter(adapter);

         return view;
    }
}