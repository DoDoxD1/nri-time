package com.example.nritime;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    ArrayList<Trip> trips;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public TripAdapter(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip, parent, false);
        TripViewHolder tripViewHolder = new TripViewHolder(v);
        return tripViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);
        String tripDate;
        if(trip.isActive)
            tripDate = "Trip "+(position+1)+": "+trip.getStartDateStr()+" - "+"Active";
        else
            tripDate = "Trip "+(position+1)+": "+trip.getStartDateStr()+" - "+trip.getEndDateStr();
        String tripTime = trip.getDays()+" days this FY";
        holder.tripDate.setText(tripDate);
        holder.tripTime.setText(tripTime);
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder{

        public TextView tripDate, tripTime;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tripDate = itemView.findViewById(R.id.trip_date);
            tripTime = itemView.findViewById(R.id.trip_time);
        }
    }
}
