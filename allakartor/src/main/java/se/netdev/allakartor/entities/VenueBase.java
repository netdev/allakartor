package se.netdev.allakartor.entities;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class VenueBase {
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String NAME = "name";
	private static final String LINK = "link";
	private static final String PICTURE = "picture";
	private static final String STREET_ADDRESS = "street_address";
	private static final String PHONE = "phone";
	
	private float latitude;
	private float longitude;
	private String name;
	private String link;
	private String picture;
	private String streetAddress;
	private String phone;
	
	public VenueBase() {}
	
	public VenueBase(final JSONObject jsonObject) throws JSONException {
		deserialize(jsonObject);
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(final float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(final float longitude) {
		this.longitude = longitude;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(final String link) {
		this.link = link;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(final String picture) {
		this.picture = picture;
	}
	
	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(final String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getPhone() {
		return phone;
	}
	
	public void setPhone(final String phone) {
		this.phone = phone;
	}
	
	public void deserialize(JSONObject jsonObject) throws JSONException {
		this.latitude = Float.valueOf(jsonObject.getString(LATITUDE));
		this.longitude = Float.valueOf(jsonObject.getString(LONGITUDE));
		this.name = StringEscapeUtils.unescapeHtml4(jsonObject.getString(NAME));
		this.link = jsonObject.getString(LINK);
		this.picture = jsonObject.getString(PICTURE);
		this.streetAddress = jsonObject.optString(STREET_ADDRESS);
		this.phone = jsonObject.getString(PHONE);
	}
}
