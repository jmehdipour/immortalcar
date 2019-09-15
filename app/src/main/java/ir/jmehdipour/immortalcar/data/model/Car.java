
package ir.jmehdipour.immortalcar.data.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tbl_cars")
public class Car implements Parcelable {
    public static final int ON_SORT_LATEST = 0;
    public static final int ON_SORT_POPULAR = 1;
    public static final int ON_SORT_HIGH_TO_LOW = 2;
    public static final int ON_SORT_LOW_TO_HIGH = 3;
    public static final String ON_MODELS = "model";
    public static final String ON_BRANDS = "brand";

    @SerializedName("brand")
    private String brand;
    @SerializedName("description")
    private String description;

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("model")
    private Long model;
    @SerializedName("price")
    private double price;
    @SerializedName("title")
    private String title;
    @SerializedName("likes")
    private String likes;

    @ColumnInfo(name = "is_favorite")
    @SerializedName("is_favorite")
    private boolean isFavorite;


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getModel() {
        return model;
    }

    public void setModel(Long model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.brand);
        dest.writeString(this.description);
        dest.writeValue(this.id);
        dest.writeString(this.imageUrl);
        dest.writeValue(this.model);
        dest.writeDouble(this.price);
        dest.writeString(this.title);
        dest.writeString(this.likes);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
    }

    public Car() {
    }

    protected Car(Parcel in) {
        this.brand = in.readString();
        this.description = in.readString();
        this.id = (int) in.readValue(Long.class.getClassLoader());
        this.imageUrl = in.readString();
        this.model = (Long) in.readValue(Long.class.getClassLoader());
        this.price = in.readDouble();
        this.title = in.readString();
        this.likes = in.readString();
        this.isFavorite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel source) {
            return new Car(source);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };
}


