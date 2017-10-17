package com.softworks.origami.velibfinder.Models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by franckzhang on 0516//2017.
 */

public class Fields {
    @SerializedName("name")
    public final String name;

    @SerializedName("address")
    public final String address;

    @SerializedName("status")
    public final String status;

    @SerializedName("available_bike_stands")
    public final int available_bike_stands;

    @SerializedName("bike_stands")
    public final int bike_stands;

    @SerializedName("last_update")
    public final Date lastUpdate;

    public Fields(String name, String address, String status, int available_bike_stands,
                  int bike_stands, Date lastUpdate) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.bike_stands = bike_stands;
        this.available_bike_stands = available_bike_stands;
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdate() {
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return simpleDate.format(lastUpdate);
    }

    public Boolean isOpen() { return status.equals("OPEN"); }

    public String getAvailableBikeStand() { return available_bike_stands + "/" + bike_stands; }
}