package com.example.nationalparks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nationalparks.adaptor.ViewPagerAdaptor;
import com.example.nationalparks.model.Park;
import com.example.nationalparks.model.ParkViewModel;


public class DetailsFragment extends Fragment  {

    private ParkViewModel parkViewModel;
    private ViewPagerAdaptor viewPagerAdaptor;
    private ViewPager2 viewPager;

    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager=view.findViewById(R.id.details_viewpager);
            parkViewModel=new ViewModelProvider(requireActivity())
                    .get(ParkViewModel.class);
//        TextView test=view.getRootView().findViewById(R.id.test);

        TextView parkName=view.findViewById(R.id.details_park_name_textiew);
        TextView parkDes=view.findViewById(R.id.details_park_designation);

        TextView description=view.getRootView().findViewById(R.id.details_description);
        TextView activities=view.getRootView().findViewById(R.id.details_activities);
        TextView entranceFees=view.getRootView().findViewById(R.id.details_entrancefees);
        TextView opHours=view.getRootView().findViewById(R.id.details_operatinghours);
        TextView detailsTopics=view.getRootView().findViewById(R.id.details_topics);
        TextView directions=view.getRootView().findViewById(R.id.details_directions);

        parkViewModel.getSelectedPark().observe(getViewLifecycleOwner(), park -> {
            parkName.setText(park.getName());
            parkDes.setText(park.getDesignation());
            viewPagerAdaptor=new ViewPagerAdaptor(park.getImages());

            viewPager.setAdapter(viewPagerAdaptor);
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}