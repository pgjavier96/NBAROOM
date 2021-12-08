package com.example.pinillagarrido.trabajofintrimestre.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Player",
        indices = {@Index(value = "name", unique = true)},
        foreignKeys = {@ForeignKey(entity = Team.class, parentColumns = "id", childColumns = "idtype", onDelete = ForeignKey.CASCADE)})
public class Player implements Parcelable {

    //id autonumérico
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "idtype")
    public long idtype;

    @ColumnInfo(name = "weight")
    public int weight;

    @ColumnInfo(name = "height")
    public double height;

    @ColumnInfo(name = "url")
    public String url;

    public Player() {
    }

    protected Player(Parcel in) {
        id = in.readLong();
        name = in.readString();
        idtype = in.readLong();
        weight = in.readInt();
        height = in.readDouble();
        url = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public boolean isValid() {
        return !(name.isEmpty() || height <= 0  || weight <= 0 || url.isEmpty() || idtype <= 0);
        //shortcut
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(idtype);
        dest.writeInt(weight);
        dest.writeDouble(height);
        dest.writeString(url);
    }
}