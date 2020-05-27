package com.echo.moviememoir.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cinema {
    @PrimaryKey(autoGenerate = true)
    private Integer cinemaId;
    private String cinemaName;
    private String location;
    private String postcode;

    public Cinema() {
    }

    public Cinema(Integer cinemaId, String cinemaName, String location, String postcode) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.location = location;
        this.postcode = postcode;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "cinemaId=" + cinemaId +
                ", cinemaName='" + cinemaName + '\'' +
                ", location='" + location + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
