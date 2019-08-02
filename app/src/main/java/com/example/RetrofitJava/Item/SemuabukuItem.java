package com.example.RetrofitJava.Item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SemuabukuItem implements Parcelable {
    @Expose
    @SerializedName("judul")
    private String judul;

    @Expose
    @SerializedName("id")
    private String id;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return
                "SemuabukuItem{" +
                        "id = '" + id + '\'' +
                        ",judul = '" + judul + '\'' +
                        "}";
//                "SemuabukuItem{" +
//                        "title='" + title + '\'' +
//                        ", body='" + body + '\'' +
//                        ", judul=" + judul+
//                        ", id=" + id +
//                        '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(judul);
    }

    public SemuabukuItem(String id,String judul) {
        this.id = id;
        this.judul= judul;
    }

    protected SemuabukuItem(Parcel in) {
//        id = in.readString();
        id = in.readString();
        judul = in.readString();
    }

    public static final Creator<SemuabukuItem> CREATOR = new Creator<SemuabukuItem>() {
        @Override
        public SemuabukuItem createFromParcel(Parcel in) {
            return new SemuabukuItem(in);
        }

        @Override
        public SemuabukuItem[] newArray(int size) {
            return new SemuabukuItem[size];
        }
    };
}
