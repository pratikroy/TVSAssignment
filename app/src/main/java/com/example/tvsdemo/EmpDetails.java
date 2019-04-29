package com.example.tvsdemo;

import android.os.Parcel;
import android.os.Parcelable;

public class EmpDetails implements Parcelable {

    private String empName;
    private String empPosition;
    private String empPlace;
    private String empId;
    private String joinDate;
    private String empSalary;

    public EmpDetails(String empName, String empPosition, String empPlace, String empId,
                      String joinDate, String empSalary) {
        this.empName = empName;
        this.empPosition = empPosition;
        this.empPlace = empPlace;
        this.empId = empId;
        this.joinDate = joinDate;
        this.empSalary = empSalary;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpPosition() {
        return empPosition;
    }

    public String getEmpPlace() {
        return empPlace;
    }

    public String getEmpId() {
        return empId;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getEmpSalary() {
        return empSalary;
    }

    private EmpDetails(Parcel in){
        empName = in.readString();
        empPlace = in.readString();
        empPosition = in.readString();
        empId = in.readString();
        joinDate = in.readString();
        empSalary = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(empName);
        dest.writeString(empPlace);
        dest.writeString(empPosition);
        dest.writeString(empId);
        dest.writeString(joinDate);
        dest.writeString(empSalary);
    }

    public static final Parcelable.Creator<EmpDetails> CREATOR =
            new Parcelable.Creator<EmpDetails>(){

                @Override
                public EmpDetails createFromParcel(Parcel in) {
                    return new EmpDetails(in);
                }

                @Override
                public EmpDetails[] newArray(int size) {
                    return new EmpDetails[size];
                }
            };
}
