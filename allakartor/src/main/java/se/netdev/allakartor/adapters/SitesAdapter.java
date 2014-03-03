package se.netdev.allakartor.adapters;

import java.util.List;

import se.netdev.allakartor.R;

import se.netdev.allakartor.activities.VenuesActivity;
import se.netdev.allakartor.entities.Site;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SitesAdapter extends BaseAdapter implements OnItemClickListener {
	private static final String MAP_NAME = "mapName";
	private static final String MAP_TITLE = "mapTitle";
	
	private Context context;
	private List<Site> sites;
	
	public SitesAdapter(final Context context, final List<Site> sites) {
		this.context = context;
		this.sites = sites;
	}
	
	static class ViewHolder {
	    public TextView name;
	    public ImageView icon;
	}
	  
	@Override
	public int getCount() {
		return sites.size();
	}

	@Override
	public Object getItem(final int position) {
		return sites.get(position);
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
			view = inflater.inflate(R.layout.sites_list_item, null);
			
			ViewHolder viewHolder = new ViewHolder();
		    viewHolder.name = (TextView) view.findViewById(R.id.name);
		    viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
		    
		    view.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		
		Site site = sites.get(position);
		
		holder.name.setText(site.getTitle());
		
		int identifier = context.getResources().getIdentifier(site.getName(), "drawable", context.getPackageName());
		holder.icon.setImageDrawable(context.getResources().getDrawable(identifier));

		return view;
	}
	
	@Override
	public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
		Site site = sites.get(position);
        
	    Intent intent = new Intent(context, VenuesActivity.class);
	    intent.putExtra(MAP_NAME, site.getName());
	    intent.putExtra(MAP_TITLE, site.getTitle());
	    
	    context.startActivity(intent);
	}
	
	public void setSites(final List<Site> sites) {
		this.sites = sites;
		notifyDataSetChanged();
	}
}
