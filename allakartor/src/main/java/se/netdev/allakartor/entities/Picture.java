package se.netdev.allakartor.entities;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Picture implements Parcelable {
	private static final String PICTURE_MEDIUM = "picture_medium";
	private static final String PICTURE_LARGE = "picture_large";
	private static final String DESCRIPTION = "description";
	private static final String USER_ID = "user_id";
	private static final String USER_NAME = "user_name";
	private static final String DATE = "date";
	
	private String pictureMedium;
	private String pictureLarge;
	private String description;
	private String userId;
	private String userName;
	private String date;
	
	public Picture(final JSONObject jsonObject) throws JSONException {
		deserialize(jsonObject);
	}
	
	public String getPictureMedium() {
		return pictureMedium;
	}
	
	public void setPictureMedium(final String pictureMedium) {
		this.pictureMedium = pictureMedium;
	}
	
	public String getPictureLarge() {
		return pictureLarge;
	}
	
	public void setPictureLarge(final String pictureLarge) {
		this.pictureLarge = pictureLarge;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(final String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(final String userName) {
		this.userName = userName;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(final String date) {
		this.date = date;
	}
	
	public void deserialize(JSONObject jsonObject) throws JSONException {
    	this.pictureMedium = jsonObject.getString(PICTURE_MEDIUM);
    	this.pictureLarge = jsonObject.getString(PICTURE_LARGE);
		this.description = jsonObject.getString(DESCRIPTION);
		this.userId = jsonObject.getString(USER_ID);
		this.userName = jsonObject.getString(USER_NAME);
		this.date = jsonObject.getString(DATE);
	}
	
	public void writeToParcel(Parcel out, int flags) {
        out.writeString(pictureMedium);
        out.writeString(pictureLarge);
        out.writeString(description);
        out.writeString(userId);
        out.writeString(userName);
        out.writeString(date);
    }
    
    private Picture(Parcel in) {
        pictureMedium = in.readString();
        pictureLarge = in.readString();
        description = in.readString();
        userId = in.readString();
        userName = in.readString();
        date = in.readString();
    }
    
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
