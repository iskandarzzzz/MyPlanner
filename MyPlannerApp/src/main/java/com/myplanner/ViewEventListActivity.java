package com.myplanner;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewEventListActivity extends Activity{


	private ListView listView;
	public static ArrayList<Schedule> list;
	private ArrayAdapter<String> listAdapter;
    private TextView text;
    private String[] imageURI;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventlist);

		listView = (ListView) findViewById(R.id.list);
        text = (TextView)findViewById(R.id.emptyView);
        listView.setEmptyView(text);
        String user = Utility.getUserFromSharedPref(this);
		list = new DatabaseHelper(ViewEventListActivity.this).getEvents(user);
        ArrayList<String> eventList = new ArrayList<String>();
        if(null != list && list.size()>0) {
            String[] eventNames = new String[list.size()];
            imageURI = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                Schedule schedule = list.get(i);
                String eventName = schedule.get_eventName();

                eventNames[i] = eventName;
                imageURI[i] = schedule.get_photoId();
            }

            eventList.addAll(Arrays.asList(eventNames));
        }
		// Create ArrayAdapter using the planet list.  
		listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, eventList);  


		// Assign adapter to ListView
		listView.setAdapter(listAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(ViewEventListActivity.this , ViewEventActivity.class);
				
				intent.putExtra("index", position);
				intent.putExtra("image_uri",imageURI[position]);
				startActivity(intent);
			}
			
			
		});

	}
}
