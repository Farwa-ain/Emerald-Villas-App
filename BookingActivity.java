package com.sunrisehotel.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    private Room selectedRoom;
    private String checkInDate;
    private String checkOutDate;

    private TextView tvSelectedRoomType;
    private TextView tvTotalPriceSummary;
    private EditText etCustomerName;
    private EditText etCustomerContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().setTitle("Booking Details");

        tvSelectedRoomType = findViewById(R.id.tv_selected_room_type);
        tvTotalPriceSummary = findViewById(R.id.tv_total_price_summary);
        etCustomerName = findViewById(R.id.et_customer_name);
        etCustomerContact = findViewById(R.id.et_customer_contact);

        // Get data passed from ResultsActivity
        selectedRoom = (Room) getIntent().getSerializableExtra("SELECTED_ROOM");
        checkInDate = getIntent().getStringExtra("CHECK_IN");
        checkOutDate = getIntent().getStringExtra("CHECK_OUT");

        if (selectedRoom == null) {
            Toast.makeText(this, "Error: No room selected.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Display room and price summary
        displayBookingDetails();
    }

    /**
     * Displays selected room details and calculates mock total price.
     */
    private void displayBookingDetails() {
        // Calculate number of nights (MOCK: For simplicity, assume 2 nights for all bookings)
        int numberOfNights = calculateNights(checkInDate, checkOutDate);
        double totalPrice = selectedRoom.getPricePerNight() * numberOfNights;

        tvSelectedRoomType.setText(selectedRoom.getType() + " (Room " + selectedRoom.getRoomNo() + ")");
        tvTotalPriceSummary.setText("Total Price (" + numberOfNights + " Nights): PKR " + String.format("%,.0f", totalPrice));
    }

    /**
     * Mock calculation for number of nights.
     */
    private int calculateNights(String checkInStr, String checkOutStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date checkIn = sdf.parse(checkInStr);
            Date checkOut = sdf.parse(checkOutStr);
            if (checkIn != null && checkOut != null) {
                long diff = checkOut.getTime() - checkIn.getTime();
                // Convert milliseconds to days
                return (int) (diff / (24 * 60 * 60 * 1000));
            }
        } catch (ParseException e) {
            // Should not happen as dates were validated in SearchActivity
        }
        return 2; // Default mock return
    }

    /**
     * Handles the final confirmation of the reservation.
     * Implements the core logic of the BookRoom system contract.
     */
    public void confirmReservation(View view) {
        String name = etCustomerName.getText().toString().trim();
        String contact = etCustomerContact.getText().toString().trim();

        if (name.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please enter your Name and Contact details.", Toast.LENGTH_LONG).show();
            return;
        }

        // --- Post-conditions of BookRoom Contract Simulation ---
        // 1. New Booking record is created (Mock: generating a simple Booking ID)
        String bookingId = "SRN" + System.currentTimeMillis() % 10000;
        // 2. Booking is linked to customer (Mock: using entered name)
        // 3. Booking is linked to selected room (selectedRoom object)
        // 4. Total price is calculated (done in displayBookingDetails)
        // 5. Room is marked as unavailable (Mock: simulated, but no state change here)
        // 6. A booking confirmation message is shown to the user (Output of contract)

        String confirmationMessage = "Success! Booking ID: " + bookingId + "\n" +
                "Room: " + selectedRoom.getType() + " (" + selectedRoom.getRoomNo() + ")\n" +
                "Dates: " + checkInDate + " to " + checkOutDate + "\n" +
                "Total Price: " + tvTotalPriceSummary.getText().toString() + "\n" +
                "Status: Reserved (Pending Payment)";

        Toast.makeText(this, confirmationMessage, Toast.LENGTH_LONG).show();

        // Return to the main search screen after successful booking
        Intent intent = new Intent(this, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}