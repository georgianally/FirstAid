package com.example.firstaid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Report implements Parcelable {
    private String start;
    private String finish;
    private String location;
    private String safe;
    private String danger;
    private String responsive;
    private String bleed;
    private String cpr;

    public Report(String start) {
        this.start = start;
        cpr = "No";
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getResponsive() {
        return responsive;
    }

    public void setResponsive(String responsive) {
        this.responsive = responsive;
    }

    public String getBleed() {
        return bleed;
    }

    public void setBleed(String bleed) {
        this.bleed = bleed;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    protected Report(Parcel in) {
        start = in.readString();
        finish = in.readString();
        location = in.readString();
        safe = in.readString();
        danger = in.readString();
        responsive = in.readString();
        bleed = in.readString();
        cpr = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(start);
        dest.writeString(finish);
        dest.writeString(location);
        dest.writeString(safe);
        dest.writeString(danger);
        dest.writeString(responsive);
        dest.writeString(bleed);
        dest.writeString(cpr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };
}
