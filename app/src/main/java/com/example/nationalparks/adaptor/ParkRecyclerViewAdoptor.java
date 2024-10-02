package com.example.nationalparks.adaptor;

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

    public ParkRecyclerViewAdoptor(List<Park> parkList) {
        this.parkList = parkList;
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
        return parkList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public  ImageView parkImage;
        public TextView parkName;
        public TextView parkType;
        public TextView parkState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parkImage=itemView.findViewById(R.id.row_park_imageView);
            parkName=itemView.findViewById(R.id.row_park_name_textView);
            parkType=itemView.findViewById(R.id.row_park_type_textView);
            parkState=itemView.findViewById(R.id.row_park_state_textView);
        }
    }
}
