package com.example.nationalparks.adaptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nationalparks.R;
import com.example.nationalparks.model.Park;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ParkRecyclerViewAdoptor extends RecyclerView.Adapter<ParkRecyclerViewAdoptor.ViewHolder> {
    private final List<Park>parkList;
    private final OnParkClickListener parkClickListener;

    public ParkRecyclerViewAdoptor(List<Park> parkList,OnParkClickListener  parkClickListener  )      {
        this.parkList = parkList;
        this.parkClickListener=parkClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.park_row,parent,false); //infalting Park Row here
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Park park=parkList.get(position);
            holder.parkName.setText(park.getName());
            holder.parkType.setText(park.getDesignation());
            holder.parkState.setText(park.getStates());
            if (park.getImages().size()>0){          /*Getting Image From Payload Using picasso
                                                Library and Setting into Image */
                Picasso.get()
                        .load(park.getImages().get(0).getUrl())
                        .placeholder(android.R.drawable.stat_sys_download) //It Shows Image is Downloading
                        .error(android.R.drawable.stat_notify_error)//if not download it show error
                        .resize(100,100)//Resizing Images
                        .centerCrop()
                        .into(holder.parkImage);
            }

    }

    @Override
    public int getItemCount() {
        Log.d("GetCount","ParkRecycler"+parkList.size());

        return parkList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        public  ImageView parkImage;
        public TextView parkName;
        public TextView parkType;
        public TextView parkState;
        OnParkClickListener onParkClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parkImage=itemView.findViewById(R.id.row_park_imageview);
            parkName=itemView.findViewById(R.id.row_par_name_textview);
            parkType=itemView.findViewById(R.id.row_park_type_textview);
            parkState=itemView.findViewById(R.id.row_part_state_textview);
            this.onParkClickListener=parkClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Park currPark=parkList.get(getAdapterPosition());
            onParkClickListener.onParkClicked(currPark);

        }
    }
}
