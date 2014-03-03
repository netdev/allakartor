package se.netdev.allakartor.entities;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Site implements Parcelable {
	private static final String TITLE = "title";
	private static final String NAME = "name";
	
	private String title;
	private String name;
	
	public Site(final JSONObject jsonObject) throws JSONException {
		deserialize(jsonObject);
	}
	
	public Site(final String title, final String name) {
		this.title = title;
		this.name = name;
	}

	public String getTitle() {
		return title;
    }
	
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getName() {
		return name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(final Object obj) {
    	if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        
        Site site = (Site) obj;
	
    	return name.equals(site.getName());
    }
    
    public JSONObject serialize() throws JSONException {
    	JSONObject jsonObject = new JSONObject();
    	
    	jsonObject.put(TITLE, title);
    	jsonObject.put(NAME, name);
    	
    	return jsonObject;
    }
    
    public void deserialize(final JSONObject jsonObject) throws JSONException {
    	this.title = jsonObject.getString(TITLE);
    	this.name = jsonObject.getString(NAME);
    }
    
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(name);
    }
    
    private Site(Parcel in) {
        title = in.readString();
        name = in.readString();
    }
    
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Site> CREATOR = new Parcelable.Creator<Site>() {
        public Site createFromParcel(Parcel in) {
            return new Site(in);
        }

        public Site[] newArray(int size) {
            return new Site[size];
        }
    };
}

