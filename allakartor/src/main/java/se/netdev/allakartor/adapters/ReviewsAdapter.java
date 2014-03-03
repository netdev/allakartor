package se.netdev.allakartor.adapters;

import java.util.List;

import se.netdev.allakartor.R;
import se.netdev.allakartor.entities.Review;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewsAdapter extends BaseAdapter {
	private Context context;
	private List<Review> reviews;
	
	public ReviewsAdapter(final Context context, final List<Review> reviews) {
		this.context = context;
		this.reviews = reviews;
	}
	
	static class ViewHolder {
	    public TextView description;
	    public TextView userName;
	    public ImageView image;
	    public TextView date;
	    public RatingBar grade;
	}

	@Override
	public int getCount() {
		return reviews.size();
	}

	@Override
	public Object getItem(final int position) {
		return reviews.get(position);
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
			view = inflater.inflate(R.layout.reviews_list_item, null);
			
			ViewHolder viewHolder = new ViewHolder();
		    viewHolder.description = (TextView) view.findViewById(R.id.description);
		    viewHolder.image = (ImageView) view.findViewById(R.id.image);
		    viewHolder.date = (TextView) view.findViewById(R.id.date);
		    viewHolder.userName = (TextView) view.findViewById(R.id.user_name);
		    viewHolder.grade = (RatingBar) view.findViewById(R.id.grade);
		    
		    view.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		Review review = reviews.get(position);
		
		holder.description.setText(review.getDescription());
		holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.no_image_50));
		holder.date.setText(review.getDate());
		holder.userName.setText(review.getUserName());
		holder.grade.setRating(review.getGrade());

		return view;
	}
	
	public void setReviews(final List<Review> reviews) {
		this.reviews = reviews;
		notifyDataSetChanged();
	}
}

