package com.example.nationalparks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nationalparks.adaptor.OnParkClickListener;
import com.example.nationalparks.adaptor.ParkRecyclerViewAdoptor;
import com.example.nationalparks.data.AsyncResponse;
import com.example.nationalparks.data.Repository;
import com.example.nationalparks.model.Park;
import com.example.nationalparks.model.ParkViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParksFragment extends Fragment implements OnParkClickListener {
        private RecyclerView recyclerView;
        private ParkRecyclerViewAdoptor parkRecyclerViewAdoptor;
        private ParkViewModel parkViewModel;
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

        parkList=new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.park_recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        super.onViewCreated(view, savedInstanceState);
        parkViewModel=new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);
                if (parkViewModel.getParks().getValue() != null){   /*Getting Value from parkviView Model
                                                                      and checking if not null then Adding to
                                                                       Recycler View Adaptor*/
                    parkList=parkViewModel.getParks().getValue();
                    parkRecyclerViewAdoptor= new ParkRecyclerViewAdoptor(parkList,this);
                    recyclerView.setAdapter(parkRecyclerViewAdoptor);
                    }

//            Repository.getParks(parks -> {Removed but first to try and check apply
//                      this ... Adding Parks to ListView Adaptor
//                parkRecyclerViewAdoptor= new ParkRecyclerViewAdoptor(parks,this);
//                for (Park park1:parks){
//                    Log.d("Check","onBind"+park1.getName());
//
//                }
//                recyclerView.setAdapter(parkRecyclerViewAdoptor);
//                    parkRecyclerViewAdoptor.notifyDataSetChanged();
//            });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parks, container, false);

        return view;
    }

    @Override
    public void onParkClicked(Park park) {
        Log.d("TAG", "onParkClicked: "+park.getName());
        parkViewModel.setSelectPark(park);
        getChildFragmentManager().beginTransaction()/*Navigating From Current
                                                        Fragment to another*/
                .replace(R.id.park_fragment,DetailsFragment.newInstance())
                .commit();

    }
}