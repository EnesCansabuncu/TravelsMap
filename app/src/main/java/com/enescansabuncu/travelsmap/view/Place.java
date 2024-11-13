package com.enescansabuncu.travelsmap.view;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Place implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="placeName")
    public String placeName;
    @ColumnInfo(name="latitude")
    public double latitude;
    @ColumnInfo(name="longitude")
    public double longitude;
    public Place(String placeName,double latitude,double longitude){
        this.placeName=placeName;
        this.latitude=latitude;
        this.longitude=longitude;
    }

}
