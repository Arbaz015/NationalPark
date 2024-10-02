package com.example.nationalparks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nationalparks.adaptor.ParkRecyclerViewAdoptor;
import com.example.nationalparks.data.AsyncResponse;
import com.example.nationalparks.data.Repository;
import com.example.nationalparks.model.Park;

import java.util.List;

public class ParksFragment extends Fragment {
        private RecyclerView recyclerView;
        private ParkRecyclerViewAdoptor parkRecyclerViewAdoptor;
        private List<Park> parkList;

    public ParksFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static ParksFragment newInstance() {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository.getParks(parks ->
                parkRecyclerViewAdoptor= new ParkRecyclerViewAdoptor(parks));

        recyclerView.setAdapter(parkRecyclerViewAdoptor);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parks, container, false);
        recyclerView = view.findViewById(R.id.park_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}