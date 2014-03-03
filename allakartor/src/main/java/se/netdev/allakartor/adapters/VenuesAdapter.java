package se.netdev.allakartor.adapters;

import java.util.ArrayList;

import se.netdev.allakartor.R;
import se.netdev.allakartor.activities.VenueActivity;
import se.netdev.allakartor.entities.VenueRef;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class VenuesAdapter extends BaseAdapter implements OnItemClickListener {
	private static final String MAP_NAME = "mapName";
	private static final String VENUE_ID = "venueId";
	private static final String NAME = "name";
	
	private Context context;
	private String mapName;
	private ArrayList<VenueRef> venues;

	public VenuesAdapter(final Context context, final String mapName, final ArrayList<VenueRef> venues) {
		this.context = context;
		this.mapName = mapName;
		this.venues = venues;
	}
	
	static class ViewHolder {
	    public TextView venueName;
	    public ImageView image;
	    public TextView distance;
	    public RatingBar grade;
	}
	  
	@Override
	public int getCount() {
		return venues.size();
	}

	@Override
	public Object getItem(final int position) {
		return venues.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.venues_list_item, null);
			
			ViewHolder viewHolder = new ViewHolder();
		    viewHolder.venueName = (TextView) view.findViewById(R.id.venue_name);
		    viewHolder.image = (ImageView) view.findViewById(R.id.image);
		    viewHolder.distance = (TextView) view.findViewById(R.id.distance);
		    viewHolder.grade = (RatingBar) view.findViewById(R.id.grade);
		    
		    view.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		VenueRef venue = venues.get(position);
		
		holder.venueName.setText(venue.getName());
		holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.no_image_50));
		holder.distance.setText(venue.getDistance());
		holder.grade.setRating(venue.getGrade());

		return view;
	}

	public void setVenues(final ArrayList<VenueRef> venues) {
		this.venues = venues;
		notifyDataSetChanged();
	}
	
	@Override
	public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
		VenueRef venue = venues.get(position);
        
	    Intent intent = new Intent(context, VenueActivity.class);
	    intent.putExtra(MAP_NAME, mapName);
	    intent.putExtra(VENUE_ID, venue.getVenueId());
	    intent.putExtra(NAME, venue.getName());
	    
	    context.startActivity(intent);
	}
}
