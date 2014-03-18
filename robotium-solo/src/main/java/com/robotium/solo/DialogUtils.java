package com.robotium.solo;


import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * Contains the waitForDialogToClose() method.
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

class DialogUtils {

	private final ActivityUtils activityUtils;
	private final ViewFetcher viewFetcher;
	private final Sleeper sleeper;
	private final static int TIMEOUT_DIALOG_TO_CLOSE = 1000;
	private final int MINISLEEP = 200;

	/**
	 * Constructs this object.
	 * 
	 * @param activityUtils the {@code ActivityUtils} instance
	 * @param viewFetcher the {@code ViewFetcher} instance
	 * @param sleeper the {@code Sleeper} instance
	 */

	public DialogUtils(ActivityUtils activityUtils, ViewFetcher viewFetcher, Sleeper sleeper) {
		this.activityUtils = activityUtils;
		this.viewFetcher = viewFetcher;
		this.sleeper = sleeper;
	}


	/**
	 * Waits for a {@link android.app.Dialog} to close.
	 *
	 * @param timeout the amount of time in milliseconds to wait
	 * @return {@code true} if the {@code Dialog} is closed before the timeout and {@code false} if it is not closed
	 */

	public boolean waitForDialogToClose(long timeout) {
		waitForDialogToOpen(TIMEOUT_DIALOG_TO_CLOSE, false);
		final long endTime = SystemClock.uptimeMillis() + timeout;

		while (SystemClock.uptimeMillis() < endTime) {

			if(!isDialogOpen(false)){
				return true;
			}
			sleeper.sleep(MINISLEEP);
		}
		return false;
	}



	/**
	 * Waits for a {@link android.app.Dialog} to open.
	 *
	 * @param timeout the amount of time in milliseconds to wait
	 * @return {@code true} if the {@code Dialog} is opened before the timeout and {@code false} if it is not opened
	 */

	public boolean waitForDialogToOpen(long timeout, boolean sleepFirst) {
		final long endTime = SystemClock.uptimeMillis() + timeout;

		if(sleepFirst)
			sleeper.sleep();

		while (SystemClock.uptimeMillis() < endTime) {

			if(isDialogOpen(true)){
				return true;
			}
			sleeper.sleepMini();
		}
		return false;
	}

	/**
	 * Checks if a dialog is open. 
	 * 
	 * @return true if dialog is open
	 */

	private boolean isDialogOpen(boolean checkOpen){
		final Activity activity = activityUtils.getCurrentActivity();
		final View[] views = viewFetcher.getWindowDecorViews();
		final View activityDecorView = activity.getWindow().getDecorView();
		View view = viewFetcher.getRecentDecorView(views);	

		if(checkOpen){
			if(activityDecorView.equals(view)){
				for(View v : views){
					if(v != null && v.isShown() && !activityDecorView.equals(v)){
						view = v;
						break;
					}
				}
			}
		}

		Context viewContext = null;
		if(view != null){
			viewContext = view.getContext();
		}

		Context activityBaseContext = activity.getBaseContext();
		return (!activityBaseContext.equals(viewContext)) && (view != activityDecorView);
	}

	/**
	 * Hides the soft keyboard
	 */

	public void hideSoftKeyboard(EditText editText) {
		Activity activity = activityUtils.getCurrentActivity();

		InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

		if(editText != null) {
			inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			return;
		}
		View focusedView = activity.getCurrentFocus();

		if(!(focusedView instanceof EditText)) {
			EditText freshestEditText = viewFetcher.getFreshestView(viewFetcher.getCurrentViews(EditText.class));
			if(freshestEditText != null){
				focusedView = freshestEditText;
			}
		}
		if(focusedView != null) {
			inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
		}
	}
}
