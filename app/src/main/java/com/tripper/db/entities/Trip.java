package com.tripper.db.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip")
public class Trip {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public int getId() { return this.id; }

    @ColumnInfo(name="name")
    private String name;
    public String getName(){ return this.name; }

}
