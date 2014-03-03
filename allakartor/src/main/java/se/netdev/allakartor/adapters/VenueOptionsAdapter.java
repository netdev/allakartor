package se.netdev.allakartor.adapters;

import se.netdev.allakartor.R;
import se.netdev.allakartor.activities.PicturesActivity;
import se.netdev.allakartor.activities.ReviewsActivity;
import se.netdev.allakartor.entities.Venue;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class VenueOptionsAdapter extends BaseAdapter implements OnItemClickListener {
	private static final String REVIEWS = "reviews";
	
	private Context context;
	private Venue venue;
	private int[] options = new int[] { R.string.reviews, R.string.pictures, R.string.map_and_driving_directions, R.string.phone, R.string.opening_hours };
	
	public VenueOptionsAdapter(final Context context) {
		this.context = context;
	}
	
	static class ViewHolder {
		public ImageView icon;
	    public TextView title;
	    public TextView subTitle;
	    public TextView count;
	}
	  
	@Override
	public int getCount() {
		if (venue != null) {
			return options.length;
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(final int position) {
		return options[position];
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
			view = inflater.inflate(R.layout.venue_options_list_item, null);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
		    viewHolder.title = (TextView) view.findViewById(R.id.title);
		    viewHolder.subTitle = (TextView) view.findViewById(R.id.sub_title);
		    viewHolder.count = (TextView) view.findViewById(R.id.count);
		    
		    view.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		holder.title.setText(options[position]);
		
		if (position == 0) {
			holder.count.setText(String.valueOf(venue.getReviews().size()));
			holder.count.setVisibility(View.VISIBLE);
			holder.subTitle.setVisibility(View.GONE);
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.reviews));
		} else if (position == 1) {
			holder.count.setText(String.valueOf(venue.getNumberOfPictures()));
			holder.count.setVisibility(View.VISIBLE);
			holder.subTitle.setVisibility(View.GONE);
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.images));
		} else if (position == 2) {
			holder.count.setVisibility(View.GONE);
			holder.subTitle.setVisibility(View.GONE);
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.directions));
		} else if (position == 3) {
			holder.count.setVisibility(View.GONE);
			holder.subTitle.setVisibility(View.VISIBLE);
			holder.subTitle.setText(venue.getPhone());
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.phone));
		} else if (position == 4) {
			holder.count.setVisibility(View.GONE);
			holder.subTitle.setVisibility(View.GONE);
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.opening_hours));
		}

		return view;
	}
	
	@Override
	public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
		if (position == 1) {
			Intent intent = new Intent(context, ReviewsActivity.class);
			intent.putParcelableArrayListExtra(REVIEWS, venue.getReviews()); 
			context.startActivity(intent);
		} else if (position == 2) {
			Intent intent = new Intent(context, PicturesActivity.class);
			context.startActivity(intent);
		} else if (position == 3) {
			
		} else if (position == 4) {
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:" + venue.getPhone()));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			context.startActivity(intent);
		} else if (position == 5) {
			
		}
	}
	
	public void setVenue(final Venue venue) {
		this.venue = venue;
		notifyDataSetChanged();
	}
}
