package com.softworks.origami.velibfinder.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 12/05/2017.
 */

public class Station {

    @SerializedName("records")
    public List<Records> records;

    public static Station copy (Station target){
        Station newStation = new Station();
        newStation.records = new ArrayList<>();
        for (Records record : target.records)
        {
            Records newRecord = new Records();
            newRecord.fields = new Fields(
                    record.fields.name,
                    record.fields.address,
                    record.fields.status,
                    record.fields.available_bike_stands,
                    record.fields.bike_stands,
                    record.fields.lastUpdate
            );
            newStation.records.add(newRecord);
        }
        return newStation;
    }
}



