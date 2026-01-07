package com.sunrisehotel.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

public class ResultsActivity extends AppCompatActivity implements RoomAdapter.OnRoomBookingListener {

    private TextView tvSearchSummary;
    private RecyclerView rvRooms;
    private TextView tvNoResults;
    private String checkInDate;
    private String checkOutDate;
    private String roomTypeFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setTitle("Available Rooms");

        tvSearchSummary = findViewById(R.id.tv_search_summary);
        rvRooms = findViewById(R.id.rv_available_rooms);
        tvNoResults = findViewById(R.id.tv_no_results);

        // 1. Get search criteria from Intent
        checkInDate = getIntent().getStringExtra("CHECK_IN");
        checkOutDate = getIntent().getStringExtra("CHECK_OUT");
        roomTypeFilter = getIntent().getStringExtra("ROOM_TYPE");

        tvSearchSummary.setText("Search: " + checkInDate + " to " + checkOutDate + " | Type: " + roomTypeFilter);

        // 2. Call the method simulating the ViewAvailableRooms system contract output
        displayAvailableRooms(checkInDate, checkOutDate, roomTypeFilter);
    }

    /**
     * Simulates the core logic of the ViewAvailableRooms system contract.
     */
    private void displayAvailableRooms(String checkIn, String checkOut, String roomType) {
        // Mock: In a real app, this would call a backend service with the dates and type
        List<Room> allRooms = Room.generateMockRooms();

        // Apply room type filter
        List<Room> filteredRooms = allRooms.stream()
                .filter(room -> roomType.equals("Any Type") || room.getType().equalsIgnoreCase(roomType))
                .collect(Collectors.toList());

        // Apply date availability filter (MOCK: all mock rooms are available)

        if (filteredRooms.isEmpty()) {
            rvRooms.setVisibility(View.GONE);
            tvNoResults.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No rooms found matching your criteria.", Toast.LENGTH_LONG).show();
        } else {
            tvNoResults.setVisibility(View.GONE);
            rvRooms.setVisibility(View.VISIBLE);
            RoomAdapter adapter = new RoomAdapter(filteredRooms, this);
            rvRooms.setAdapter(adapter);
            // Show success result (A list of available rooms is shown)
            Toast.makeText(this, filteredRooms.size() + " rooms found!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the click event from the RecyclerView item's "Book Now" button.
     * Initiates the BookRoom use case flow.
     */
    @Override
    public void onBookNowClick(Room room) {
        // Navigate to the BookingActivity
        Intent intent = new Intent(ResultsActivity.this, BookingActivity.class);
        intent.putExtra("SELECTED_ROOM", room); // Pass the selected room object
        intent.putExtra("CHECK_IN", checkInDate);
        intent.putExtra("CHECK_OUT", checkOutDate);
        startActivity(intent);
    }
}