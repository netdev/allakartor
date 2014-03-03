package se.netdev.allakartor.entities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Venue extends VenueBase {
	private static final String DESCRIPTION = "description";
	private static final String NUMBER_OF_PICTURES = "number_of_pictures";
	private static final String REVIEWS = "reviews";
	
	private String description;
	private String numberOfPictures;
	private ArrayList<Review> reviews;
	
	public Venue(final JSONObject jsonObject) throws JSONException {
		deserialize(jsonObject);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public String getNumberOfPictures() {
		return numberOfPictures;
	}
	
	public void setNumberOfPictures(final String numberOfPictures) {
		this.numberOfPictures = numberOfPictures;
	}
	
	public ArrayList<Review> getReviews() {
		if (reviews == null) {
			reviews = new ArrayList<Review>();
		}
		
		return reviews;
	}
	
	@Override
	public void deserialize(final JSONObject jsonObject) throws JSONException {
		super.deserialize(jsonObject);
		
		this.description = jsonObject.getString(DESCRIPTION);
		this.numberOfPictures = jsonObject.getString(NUMBER_OF_PICTURES).replaceAll("\\s\\D+", "");
		
		JSONArray jsonArray =  jsonObject.getJSONArray(REVIEWS);
		
		for (int index = 0; index < jsonArray.length(); index++) {
			getReviews().add(new Review(jsonArray.getJSONObject(index)));
		}
	}
}
