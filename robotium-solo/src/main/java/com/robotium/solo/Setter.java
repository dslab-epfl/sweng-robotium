package com.robotium.solo;

import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TimePicker;


/**
 * Contains set methods. Examples are setDatePicker(),
 * setTimePicker().
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

class Setter{

	private final ActivityUtils activityUtils;
	private final Getter getter;
	private final Clicker clicker;
	private final Waiter waiter;

	/**
	 * Constructs this object.
	 *
	 * @param activityUtils the {@code ActivityUtils} instance
	 * @param getter the {@code Getter} instance
	 * @param clicker the {@code Clicker} instance
	 * @param waiter the {@code Waiter} instance
	 */

	public Setter(ActivityUtils activityUtils, Getter getter, Clicker clicker, Waiter waiter) {
		this.activityUtils = activityUtils;
		this.getter = getter;
		this.clicker = clicker;
		this.waiter = waiter;
	}


	/**
	 * Sets the date in a given {@link DatePicker}.
	 *
	 * @param datePicker the {@code DatePicker} object.
	 * @param year the year e.g. 2011
	 * @param monthOfYear the month which is starting from zero e.g. 03
	 * @param dayOfMonth the day e.g. 10
	 */

	public void setDatePicker(final DatePicker datePicker, final int year, final int monthOfYear, final int dayOfMonth) {
		if(datePicker != null){

			activityUtils.getCurrentActivity().runOnUiThread(new Runnable()
			{
				public void run()
				{
					try{
						datePicker.updateDate(year, monthOfYear, dayOfMonth);
					}catch (Exception ignored){}
				}
			});
		}
	}


	/**
	 * Sets the time in a given {@link TimePicker}.
	 *
	 * @param timePicker the {@code TimePicker} object.
	 * @param hour the hour e.g. 15
	 * @param minute the minute e.g. 30
	 */

	public void setTimePicker(final TimePicker timePicker, final int hour, final int minute) {
		if(timePicker != null){

			activityUtils.getCurrentActivity().runOnUiThread(new Runnable()
			{
				public void run()
				{
					try{
						timePicker.setCurrentHour(hour);
						timePicker.setCurrentMinute(minute);
					}catch (Exception ignored){}
				}
			});
		}
	}


	/**
	 * Sets the progress of a given {@link ProgressBar}. Examples are SeekBar and RatingBar.
	 * @param progressBar the {@code ProgressBar}
	 * @param progress the progress that the {@code ProgressBar} should be set to
	 */

	public void setProgressBar(final ProgressBar progressBar,final int progress) {
		if(progressBar != null){

			activityUtils.getCurrentActivity().runOnUiThread(new Runnable()
			{
				public void run()
				{
					try{
						progressBar.setProgress(progress);
					}catch (Exception ignored){}
				}
			});
		}
	}
}
