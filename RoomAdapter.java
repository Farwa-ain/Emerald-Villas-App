package com.sunrisehotel.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for displaying the list of available rooms in RecyclerView.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private final List<Room> roomList;
    private final OnRoomBookingListener listener;

    // Interface to handle the Book Now button click
    public interface OnRoomBookingListener {
        void onBookNowClick(Room room);
    }

    public RoomAdapter(List<Room> roomList, OnRoomBookingListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        final Room room = roomList.get(position);

        holder.tvRoomType.setText(room.getType());
        holder.tvRoomNumber.setText("Room No: " + room.getRoomNo());
        holder.tvRoomPrice.setText(room.getFormattedPrice());
        holder.tvRoomFacilities.setText("Facilities: " + room.getFacilities());

        // Set the click listener for the 'Book Now' button
        holder.btnBookNow.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookNowClick(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    // ViewHolder class to hold the views for each room item
    static class RoomViewHolder extends RecyclerView.ViewHolder {
        final TextView tvRoomType;
        final TextView tvRoomNumber;
        final TextView tvRoomPrice;
        final TextView tvRoomFacilities;
        final Button btnBookNow;

        RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomType = itemView.findViewById(R.id.tv_room_type);
            tvRoomNumber = itemView.findViewById(R.id.tv_room_number);
            tvRoomPrice = itemView.findViewById(R.id.tv_room_price);
            tvRoomFacilities = itemView.findViewById(R.id.tv_room_facilities);
            btnBookNow = itemView.findViewById(R.id.btn_book_now);
        }
    }
}