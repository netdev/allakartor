package se.netdev.allakartor.entities;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
	private static final String GRADE = "grade";
	private static final String DESCRIPTION = "description";
	private static final String USER_ID = "user_id";
	private static final String USER_NAME = "user_name";
	private static final String USER_PICTURE = "user_picture";
	private static final String DATE = "date";
	
	private float grade;
	private String description;
	private String userId;
	private String userName;
	private String userPicture;
	private String date;
	
	public Review() {
		
	}

	public Review(final JSONObject jsonObject) throws JSONException {
		deserialize(jsonObject);
	}
	
	public float getGrade() {
		return grade;
    }
	
    public void setGrade(final float grade) {
        this.grade = grade;
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
    
    public String getUserPicture() {
        return userPicture;
    }
    
    public void setUserPicture(final String userPicture) {
        this.userPicture = userPicture;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(final String date) {
        this.date = date;
    } 
    
    public void deserialize(JSONObject jsonObject) throws JSONException {
    	this.grade = Float.valueOf(jsonObject.getString(GRADE));
		this.description = jsonObject.getString(DESCRIPTION);
		this.userId = jsonObject.getString(USER_ID);
		this.userName = jsonObject.getString(USER_NAME);
		this.userPicture = jsonObject.getString(USER_PICTURE);
		this.date = jsonObject.getString(DATE);
	}
    
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(grade);
        out.writeString(description);
        out.writeString(userId);
        out.writeString(userName);
        out.writeString(userPicture);
        out.writeString(date);
    }
    
    private Review(Parcel in) {
        grade = in.readFloat();
        description = in.readString();
        userId = in.readString();
        userName = in.readString();
        userPicture = in.readString();
        date = in.readString();
    }
    
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}

