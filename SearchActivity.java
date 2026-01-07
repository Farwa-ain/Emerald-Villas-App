package com.sunrisehotel.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private EditText etCheckInDate;
    private EditText etCheckOutDate;
    private Spinner spinnerRoomType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Set the activity title
        getSupportActionBar().setTitle("Sunrise hotel - Find Rooms");

        etCheckInDate = findViewById(R.id.et_check_in_date);
        etCheckOutDate = findViewById(R.id.et_check_out_date);
        spinnerRoomType = findViewById(R.id.spinner_room_type);

        // Mock data for Room Type Spinner
        String[] roomTypes = {"Any Type", "Standard Single", "Deluxe Double", "Luxury Suite"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, roomTypes);
        spinnerRoomType.setAdapter(adapter);
    }

    /**
     * Handles the click event for the "Search Available Rooms" button.
     * Implements the input validation step of the View AvailableRooms contract.
     */
    public void onSearchRoomsClick(View view) {
        String checkInStr = etCheckInDate.getText().toString();
        String checkOutStr = etCheckOutDate.getText().toString();
        String roomType = spinnerRoomType.getSelectedItem().toString();

        if (checkInStr.isEmpty() || checkOutStr.isEmpty()) {
            Toast.makeText(this, "Please enter both Check-in and Check-out dates.", Toast.LENGTH_LONG).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date checkInDate = sdf.parse(checkInStr);
            Date checkOutDate = sdf.parse(checkOutStr);

            // Date validation (start date cannot be after or same as end date)
            if (checkInDate == null || checkOutDate == null || !checkOutDate.after(checkInDate)) {
                Toast.makeText(this, "Invalid dates: Check-out must be after Check-in.", Toast.LENGTH_LONG).show();
                return;
            }

            // Proceed to ResultsActivity (View AvailableRooms success path)
            Intent intent = new Intent(SearchActivity.this, ResultsActivity.class);
            intent.putExtra("CHECK_IN", checkInStr);
            intent.putExtra("CHECK_OUT", checkOutStr);
            intent.putExtra("ROOM_TYPE", roomType);
            startActivity(intent);

        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format. Use DD/MM/YYYY.", Toast.LENGTH_LONG).show();
        }
    }
}