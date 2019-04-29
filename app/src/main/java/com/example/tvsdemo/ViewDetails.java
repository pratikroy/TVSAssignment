package com.example.tvsdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDetails extends AppCompatActivity {

    private static final String TAG = ViewDetails.class.getSimpleName();
    private static final int CAMERA_REQUEST = 100;
    private static final int MY_CAMERA_REQUEST_CODE = 200;
    private TextView empName;
    private TextView empPosition;
    private TextView empPlace;
    private TextView empId;
    private TextView empDate;
    private TextView empSalary;
    private EmpDetails details;
    private Button takeImage;
    private ImageView showImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        empName = findViewById(R.id.tv_emp_name);
        empPosition = findViewById(R.id.tv_emp_position);
        empPlace = findViewById(R.id.tv_emp_place);
        empId = findViewById(R.id.tv_emp_id);
        empDate = findViewById(R.id.tv_emp_date);
        empSalary = findViewById(R.id.tv_emp_salary);
        takeImage = findViewById(R.id.capture_image);
        showImage = findViewById(R.id.show_captured_image);
        showImage.setVisibility(View.GONE);

        Intent receivedIntent = getIntent();
        details = receivedIntent.getExtras().getParcelable("empObject");
        empName.setText("Name: " + details.getEmpName());
        empPosition.setText("Position: " + details.getEmpPosition());
        empPlace.setText("Place: " + details.getEmpPlace());
        empId.setText("ID: " + details.getEmpId());
        empDate.setText("Join Date: " + details.getJoinDate());
        empSalary.setText("Salary: " + details.getEmpSalary());

        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkCameraPermission();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, MY_CAMERA_REQUEST_CODE);
                }
            }
        });
    }

    // Not getting camera permission. Need to debug the below two functions
    /*
        @TargetApi(Build.VERSION_CODES.M)
    private void checkCameraPermission(){
        Log.v(TAG, "Inside checkCameraPermission function");
        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }else{
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_CAMERA_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MY_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            showImage.setVisibility(View.VISIBLE);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            showImage.setImageBitmap(photo);
        }
    }
}
