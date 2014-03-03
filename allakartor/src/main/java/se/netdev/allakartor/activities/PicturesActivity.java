package se.netdev.allakartor.activities;

import java.util.ArrayList;

import se.netdev.allakartor.R;
import se.netdev.allakartor.adapters.PicturesAdapter;
import se.netdev.allakartor.entities.Picture;
import se.netdev.allakartor.operations.GetPicturesOperation;
import se.netdev.allakartor.operations.GetPicturesOperation.GetPicturesCallback;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.sogeti.droidnetworking.NetworkEngine;

public class PicturesActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pictures);

	    GridView gridView = (GridView) findViewById(R.id.gridview);
	    gridView.setAdapter(new PicturesAdapter());
		
		actionBar.setTitle(R.string.pictures);
		
		GetPicturesOperation operation = new GetPicturesOperation("cafekartan", "0", new GetPicturesCallback() {
			@Override
			public void done(final ArrayList<Picture> pictures, final Exception exception) {

			}
		});
		
		NetworkEngine.getInstance().enqueueOperation(operation);
	}
	
	@Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.pictures_actions, menu);
        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_new_picture:
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
