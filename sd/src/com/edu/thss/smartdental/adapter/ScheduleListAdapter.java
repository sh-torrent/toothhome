/*
 * 作者：耿正霖
 */
package com.edu.thss.smartdental.adapter;

import com.edu.thss.smartdental.R;
import com.edu.thss.smartdental.ScheduleDetailFragment;
import com.edu.thss.smartdental.ScheduleFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edu.thss.smartdental.db.ScheduleManager;
import com.edu.thss.smartdental.model.ScheduleElement;

public class ScheduleListAdapter extends BaseAdapter {
	private LayoutInflater inflater = null;
	private FragmentActivity fa = null;
	private List<ScheduleElement> se = null;
	private Date date = null;
	
	public ScheduleListAdapter(FragmentActivity f, Date d){
		fa = f;
		Context context = f;
		inflater = LayoutInflater.from(context);
		ScheduleManager sm = new ScheduleManager(fa);
		date = d;
		se = Arrays.asList(sm.ScheduleList(d));
//		se = getData();
	}
	
	private String getLen2(String s){
		while(s.length() < 2){
			s = '0' + s;
		}
		return s;
	}
	public List<ScheduleElement> getData(){	
		ScheduleElement s1 = new ScheduleElement("看蓝猫", new Date(2014, 12, 3, 17, 30), "descirption", "fromUser", "toUser");
		ScheduleElement s2 = new ScheduleElement("学蓝猫", new Date(2014, 12, 3, 18, 30), "descirption", "fromUser", "toUser");
		ScheduleElement s3 = new ScheduleElement("我有姿势我自豪！", new Date(2014, 12, 3, 19, 30), "descirption", "fromUser", "toUser");
		List<ScheduleElement> t = new ArrayList<ScheduleElement>();
		t.add(s1);
		t.add(s2);
		t.add(s3);
		return t;
	}
	
	@Override
    public int getCount() {
        // TODO Auto-generated method stub
        return se.size();
    }

    @Override
    public ScheduleElement getItem(int arg0) {
        // TODO Auto-generated method stub
        return se.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if(arg1==null)
        {
            arg1 = inflater.inflate(R.layout.schedule_item, arg2,false);
        }
        
        TextView hour = (TextView)arg1.findViewById(R.id.hour);
        TextView min = (TextView)arg1.findViewById(R.id.min);
        TextView name = (TextView)arg1.findViewById(R.id.name);
        TextView fromUser = (TextView)arg1.findViewById(R.id.fromUser);
        
        final ScheduleElement temp = se.get(arg0);
        
        Date d = temp.alertTime;
        Calendar c = Calendar.getInstance(); 
        c.setTime(d);
        
        hour.setText(getLen2(Integer.toString(c.get(Calendar.HOUR_OF_DAY))));
        min.setText(':' + getLen2(Integer.toString(c.get(Calendar.MINUTE))));
        name.setText(temp.name);
        fromUser.setText(temp.fromUser);
        final String ttt = hour.getText().toString() +min.getText().toString();
        final ImageView edit = (ImageView)arg1.findViewById(R.id.schedule_edit);
        ImageView cancel = (ImageView)arg1.findViewById(R.id.schedule_cancel);
        
        edit.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    edit.setAlpha(1.0f);
                    break;
                }
                case MotionEvent.ACTION_UP:{
                	edit.setAlpha(0.8f);
                	Fragment fragment  = new ScheduleDetailFragment();
                	Calendar cal = Calendar.getInstance();
			        cal.setTime(date);
			        int year = cal.get(Calendar.YEAR);
			        int month = cal.get(Calendar.MONTH);
			        int day = cal.get(Calendar.DAY_OF_MONTH);
                	Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(temp.id));
                    bundle.putString("action", "edit");
                    bundle.putString("name", temp.name);
                    bundle.putString("time", ttt);
                    bundle.putString("description", temp.description);
			        bundle.putString("year", String.valueOf(year));
			        bundle.putString("month", String.valueOf(month));
			        bundle.putString("day", String.valueOf(day));
                    fragment.setArguments(bundle);

        			if(fragment != null){
        				FragmentManager fragmentManager = fa.getSupportFragmentManager();
        				fragmentManager.beginTransaction().replace(R.id.test_activity,fragment).commit();
        			}
                    break;
                }
                }
                return true;
            }
        });
        cancel.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    edit.setAlpha(1.0f);
                    break;
                }
                case MotionEvent.ACTION_UP:{
                	edit.setAlpha(0.8f);
                	ScheduleManager sm = new ScheduleManager(fa);
                	Fragment fragment  = new ScheduleFragment();
                	sm.deleteSchedule(temp.id);
                	Bundle bundle = new Bundle();
                	Calendar cal = Calendar.getInstance();
			        cal.setTime(date);
			        int year = cal.get(Calendar.YEAR);
			        int month = cal.get(Calendar.MONTH);
			        int day = cal.get(Calendar.DAY_OF_MONTH);
			        bundle.putString("year", String.valueOf(year));
			        bundle.putString("month", String.valueOf(month));
			        bundle.putString("day", String.valueOf(day));
			        fragment.setArguments(bundle);
        			if(fragment != null){
        				FragmentManager fragmentManager = fa.getSupportFragmentManager();
        				fragmentManager.beginTransaction().replace(R.id.test_activity,fragment).commit();
        			}
                    break;
                }
                }
                return true;
            }
        });
        
        
        return arg1;
    }

}
