/*
 * ���ߣ�������
 */
package com.edu.thss.smartdental;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edu.thss.smartdental.AppointmentFragment.calendarItemClickListener;
import com.edu.thss.smartdental.model.ScheduleElement;
import com.edu.thss.smartdental.ui.calendar.CalendarView;
import com.edu.thss.smartdental.adapter.ScheduleListAdapter;
import com.edu.thss.smartdental.db.ScheduleManager;
import com.edu.thss.smartdental.R;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class ScheduleDetailFragment extends Fragment {
	
	private Button btnChangeTime;
	private View myView = null;
	private int year = 0;
	private int month = 0;
	private int day = 0;

	public void addListenerOnButton() {
		final TextView tvDisplayTime = (TextView) myView.findViewById(R.id.edit_time);
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		setTime(tvDisplayTime, hour, minute);

		btnChangeTime = (Button) myView.findViewById(R.id.time_btn);

		btnChangeTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				MyTimePicker tp = new MyTimePicker(tvDisplayTime);
//				TimePickerDialog td = new TimePickerDialog(getActivity(), timePickerListener, hour, minute,
//    					false);
                tp.show(fm, "fragment_schedule_detail"); 

//				getActivity().showDialog(TIME_DIALOG_ID);

			}

		});

	}
	
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
	
	public void setTime(TextView tv, int hour, int minute){
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current time into textview
		tv.setText(new StringBuilder().append(pad(hour)).append(":")
				.append(pad(minute)));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String y = this.getArguments().getString("year");
		String m = this.getArguments().getString("month");
		String d = this.getArguments().getString("day");
		year = Integer.parseInt(y);
		month = Integer.parseInt(m);
		day = Integer.parseInt(d);
		final String action = this.getArguments().getString("action");
		
		
		final View rootView = inflater.inflate(R.layout.fragment_schedule_edit, container, false);
		myView = rootView;
		Button cancel = (Button)rootView.findViewById(R.id.edit_cancel);
		Button submit = (Button)rootView.findViewById(R.id.edit_submit);
		addListenerOnButton();
		final String id = this.getArguments().getString("id");
		if (action.equals("edit")){
			TextView name = (TextView)rootView.findViewById(R.id.edit_name);
			TextView description = (TextView)rootView.findViewById(R.id.edit_description);
			TextView time = (TextView)rootView.findViewById(R.id.edit_time);
			String tn = this.getArguments().getString("name");
			String tt = this.getArguments().getString("time");
			String td = this.getArguments().getString("description");
			name.setText(tn);
			description.setText(td);
			time.setText(tt);
		}
		cancel.setOnClickListener(new OnClickListener() {
			   
			   @Override
			   public void onClick(View arg0) {
				   Fragment fragment  = new ScheduleFragment();
					if(fragment != null){
						FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
						fragmentManager.beginTransaction().replace(R.id.test_activity,fragment).commit();
					}
			   }
			});
		submit.setOnClickListener   (new OnClickListener(){
			@Override
			   public void onClick(View arg0) {
				TextView name = (TextView)rootView.findViewById(R.id.edit_name);
				TextView description = (TextView)rootView.findViewById(R.id.edit_description);
				TextView time = (TextView)rootView.findViewById(R.id.edit_time);
				String[] tmp = time.getText().toString().split(":");
				int hour = Integer.parseInt(tmp[0]);
				int min = Integer.parseInt(tmp[1]);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR,year);
				cal.set(Calendar.MONTH,month);
				cal.set(Calendar.DAY_OF_MONTH,day);
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE,min);

				Date d = cal.getTime();
				ScheduleElement se = new ScheduleElement(name.getText().toString(), d, description.getText().toString(), "fromUser", "toUser");
				ScheduleManager sm = new ScheduleManager(getActivity());
				if (action.equals("edit")){
					se.id = Integer.parseInt(id);
					sm.editSchedule(se);
				}
				else{
					sm.addSchedule(se);
				}
				Fragment fragment  = new ScheduleFragment();
				Bundle bundle = new Bundle();
                bundle.putString("action", "add");
                bundle.putString("year", String.valueOf(year));
		        bundle.putString("month", String.valueOf(month));
		        bundle.putString("day", String.valueOf(day));
                fragment.setArguments(bundle);
					if(fragment != null){
						FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
						fragmentManager.beginTransaction().replace(R.id.test_activity,fragment).commit();
					}
			   }
		});
		
		return rootView;
	}

}
