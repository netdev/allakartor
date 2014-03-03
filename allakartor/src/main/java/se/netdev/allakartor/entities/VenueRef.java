package se.netdev.allakartor.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class VenueRef extends VenueBase {
	private static final String VENUE_ID = "venue_id";
	private static final String GRADE = "grade";
	private static final String GRADE_COUNT = "grade_count";
	private static final String VENUE_OPENING_HOURS = "venue_opening_hours";
	private static final String DISTANCE = "distance";
	
	private String venueId;
	private float grade;
	private String gradeCount;
	private String venueOpeningHours;
	private String distance;
	
	public VenueRef() {
		
	}
	
	public VenueRef(final JSONObject jsonObject) throws JSONException {
		deserialize(jsonObject);
	}
	
	public String getVenueId() {
		return venueId;
	}
	
	public void setVenueId(final String venueId) {
		this.venueId = venueId;
	}
	
	public float getGrade() {
		return grade;
	}
	
	public void setGrade(final float grade) {
		this.grade = grade;
	}
	
	public String getGradeCount() {
		return gradeCount;
	}
	
	public void setGradeCount(final String gradeCount) {
		this.gradeCount = gradeCount;
	}
	
	public String getVenueOpeningHours() {
		return venueOpeningHours;
	}
	
	public void setVenueOpeningHours(final String venueOpeningHours) {
		this.venueOpeningHours = venueOpeningHours;
	}
	
	public String getDistance() {
		return distance;
	}
	
	public void setDistance(final String distance) {
		this.distance = distance;
	}
	
	@Override
	public void deserialize(JSONObject jsonObject) throws JSONException {
		super.deserialize(jsonObject);
		
		this.venueId = jsonObject.getString(VENUE_ID);
		this.grade = Float.valueOf(jsonObject.getString(GRADE));
		this.gradeCount = jsonObject.getString(GRADE_COUNT);
		this.venueOpeningHours = jsonObject.optString(VENUE_OPENING_HOURS);
		this.distance = jsonObject.getString(DISTANCE);
	}
}

