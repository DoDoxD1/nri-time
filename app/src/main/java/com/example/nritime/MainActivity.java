 package com.example.nritime;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

 public class MainActivity extends AppCompatActivity {

     private RecyclerView recyclerView;
     private TextView daysTextView, daysLeftTextView;
     private Spinner spinner;
     private TextView textView, textView1, textView2;
     private Button addTripButton, saveTripButton, cancelButton;

     String startDate="", endDate="";

     private DatePickerDialog.OnDateSetListener setListener;

     private LinearLayoutManager linearLayoutManager;
     private TripAdapter tripAdapter;
     ArrayList<Trip> trips = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //refrence
        recyclerView = findViewById(R.id.recylerview);
        daysTextView = findViewById(R.id.nri_days_textView);
        daysLeftTextView = findViewById(R.id.nri_target_textView);
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.new_trip_textview);
        textView1 = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        addTripButton = findViewById(R.id.add_trip);
        saveTripButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.fy_list));
        spinner.setAdapter(arrayAdapter);

        Trip trip = new Trip("13 02 2222","26 02 2222");
        Trip trip1 = new Trip("13 02 2222","26 03 2222");
        trips.add(trip);
        trips.add(trip1);

        loadData();
        updateTextFields();

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        tripAdapter = new TripAdapter(trips);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(tripAdapter);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = "Fly out: "+day+"/"+month+"/"+year;
                        if(month>9){
                            if(day>9)
                                startDate = day+" "+month+" "+year;
                            else
                                startDate = "0"+day+" "+month+" "+year;
                        }
                        else{
                            if(day>9)
                                startDate = day+" 0"+month+" "+year;
                            else
                                startDate = "0"+day+" 0"+month+" "+year;
                        }
                        textView1.setText(date);
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd MM yyyy").parse("01 04 2021");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePickerDialog.getDatePicker().setMinDate(date.getTime());
                datePickerDialog.show();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = "Fly in: "+day+"/"+month+"/"+year;
                        if(month>9){
                            if(day>9)
                                endDate = day+" "+month+" "+year;
                            else
                                endDate = "0"+day+" "+month+" "+year;
                        }
                        else {
                            if(day>9)
                                endDate = day+" 0"+month+" "+year;
                            else
                                endDate = "0"+day+" 0"+month+" "+year;
                        }
                        textView2.setText(date);
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd MM yyyy").parse(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePickerDialog.getDatePicker().setMinDate(date.getTime());
                datePickerDialog.show();
            }
        });

        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                saveTripButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                addTripButton.setVisibility(View.GONE);
            }
        });

        saveTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(startDate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Select date first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(endDate.isEmpty()) endDate = "Active";
                textView.setVisibility(View.GONE);
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                saveTripButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                addTripButton.setVisibility(View.VISIBLE);
                Trip trip = new Trip(startDate,endDate);
                trips.add(trip);
                tripAdapter.notifyDataSetChanged();
                saveData();
                updateTextFields();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setVisibility(View.GONE);
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                saveTripButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                addTripButton.setVisibility(View.VISIBLE);
            }
        });
    }

     private void saveData() {
         SharedPreferences sharedPreferences = getSharedPreferences("Trips",MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         Gson gson = new Gson();
         String json = gson.toJson(trips);
         editor.putString("trip list",json);
         editor.apply();
     }

     private void loadData(){
         SharedPreferences sharedPreferences = getSharedPreferences("Trips",MODE_PRIVATE);
         Gson gson = new Gson();
         String json = sharedPreferences.getString("trip list",null);
         Type type = new TypeToken<ArrayList<Trip>>() {}.getType();
         trips = gson.fromJson(json,type);
         if(trips == null){
             trips = new ArrayList<>();
         }
     }

     @RequiresApi(api = Build.VERSION_CODES.O)
     private void updateTextFields() {
        long nriDays = 0;
         for (Trip trip:
              trips) {
             nriDays += trip.getDays();
         }
         String nriDaysStr, nriDaysLeftStr;
         nriDaysStr = nriDays + " Days";
         nriDaysLeftStr = (183-nriDays) + " More days needed";
         daysTextView.setText(nriDaysStr);
         daysLeftTextView.setText(nriDaysLeftStr);

     }
 }