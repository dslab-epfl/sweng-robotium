package com.robotium.solo;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.Assert;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;


/**
 * Contains various wait methods. Examples are: waitForText(),
 * waitForView().
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

class Waiter {

	private final ViewFetcher viewFetcher;
	private final Searcher searcher;
	private final Sleeper sleeper;
	/**
	 * Constructs this object.
	 *
	 * @param activityUtils the {@code ActivityUtils} instance
	 * @param viewFetcher the {@code ViewFetcher} instance
	 * @param searcher the {@code Searcher} instance
	 * @param scroller the {@code Scroller} instance
	 * @param sleeper the {@code Sleeper} instance
	 */

	public Waiter(ActivityUtils activityUtils, ViewFetcher viewFetcher, Searcher searcher, Scroller scroller, Sleeper sleeper){
		this.viewFetcher = viewFetcher;
		this.searcher = searcher;
		this.sleeper = sleeper;
	}

	/**
	 * Returns a web element.
	 * 
	 * @param by the By object. Examples are By.id("id") and By.name("name")
	 * @param minimumNumberOfMatches the minimum number of matches that are expected to be shown. {@code 0} means any number of matches
	 * @param scroll {@code true} if scrolling should be performed 
	 */

	public WebElement getWebElement(final By by, int minimumNumberOfMatches, boolean scroll){
		return searcher.searchForWebElement(by, minimumNumberOfMatches, scroll); 
	}


	/**
	 * Waits for a condition to be satisfied.
	 * 
	 * @param condition the condition to wait for
	 * @param timeout the amount of time in milliseconds to wait
	 * @return {@code true} if condition is satisfied and {@code false} if it is not satisfied before the timeout
	 */
	public boolean waitForCondition(Condition condition, int timeout){
		final long endTime = SystemClock.uptimeMillis() + timeout;

		while (true) {
			if (condition.isSatisfied()){
				return true;
			}

			final boolean timedOut = SystemClock.uptimeMillis() > endTime;
			if (timedOut){
				return false;
			}

			sleeper.sleep();
		}
	}

	/**
	 * Waits for a text to be shown. Default timeout is 20 seconds.
	 *
	 * @param text the text that needs to be shown, specified as a regular expression
	 * @return {@code true} if text is found and {@code false} if it is not found before the timeout
	 */

	public TextView waitForText(String text) {
		return waitForText(text, 0, Timeout.getLargeTimeout(), true);
	}

	/**
	 * Waits for a text to be shown.
	 *
	 * @param text the text that needs to be shown, specified as a regular expression
	 * @param expectedMinimumNumberOfMatches the minimum number of matches of text that must be shown. {@code 0} means any number of matches
	 * @param timeout the amount of time in milliseconds to wait
	 * @return {@code true} if text is found and {@code false} if it is not found before the timeout
	 */

	public TextView waitForText(String text, int expectedMinimumNumberOfMatches, long timeout)
	{
		return waitForText(text, expectedMinimumNumberOfMatches, timeout, true);
	}

	/**
	 * Waits for a text to be shown.
	 *
	 * @param text the text that needs to be shown, specified as a regular expression
	 * @param expectedMinimumNumberOfMatches the minimum number of matches of text that must be shown. {@code 0} means any number of matches
	 * @param timeout the amount of time in milliseconds to wait
	 * @param scroll {@code true} if scrolling should be performed
	 * @return {@code true} if text is found and {@code false} if it is not found before the timeout
	 */

	public TextView waitForText(String text, int expectedMinimumNumberOfMatches, long timeout, boolean scroll) {
		return waitForText(TextView.class, text, expectedMinimumNumberOfMatches, timeout, scroll, false, true);	
	}
	
	/**
	 * Waits for a text to be shown.
	 *
	 * @param classToFilterBy the class to filter by
	 * @param text the text that needs to be shown, specified as a regular expression
	 * @param expectedMinimumNumberOfMatches the minimum number of matches of text that must be shown. {@code 0} means any number of matches
	 * @param timeout the amount of time in milliseconds to wait
	 * @param scroll {@code true} if scrolling should be performed
	 * @return {@code true} if text is found and {@code false} if it is not found before the timeout
	 */

	public <T extends TextView> T waitForText(Class<T> classToFilterBy, String text, int expectedMinimumNumberOfMatches, long timeout, boolean scroll) {
		return waitForText(classToFilterBy, text, expectedMinimumNumberOfMatches, timeout, scroll, false, true);	
	}

	/**
	 * Waits for a text to be shown.
	 *
	 * @param text the text that needs to be shown, specified as a regular expression.
	 * @param expectedMinimumNumberOfMatches the minimum number of matches of text that must be shown. {@code 0} means any number of matches
	 * @param timeout the amount of time in milliseconds to wait
	 * @param scroll {@code true} if scrolling should be performed
	 * @param onlyVisible {@code true} if only visible text views should be waited for
	 * @param hardStoppage {@code true} if search is to be stopped when timeout expires
	 * @return {@code true} if text is found and {@code false} if it is not found before the timeout
	 */
	
	public TextView waitForText(String text, int expectedMinimumNumberOfMatches, long timeout, boolean scroll, boolean onlyVisible, boolean hardStoppage) {
		return waitForText(TextView.class, text, expectedMinimumNumberOfMatches, timeout, scroll, onlyVisible, hardStoppage);
	}

	/**
	 * Waits for a text to be shown.
	 *
	 * @param classToFilterBy the class to filter by
	 * @param text the text that needs to be shown, specified as a regular expression.
	 * @param expectedMinimumNumberOfMatches the minimum number of matches of text that must be shown. {@code 0} means any number of matches
	 * @param timeout the amount of time in milliseconds to wait
	 * @param scroll {@code true} if scrolling should be performed
	 * @param onlyVisible {@code true} if only visible text views should be waited for
	 * @param hardStoppage {@code true} if search is to be stopped when timeout expires
	 * @return {@code true} if text is found and {@code false} if it is not found before the timeout
	 */

	public <T extends TextView> T waitForText(Class<T> classToFilterBy, String text, int expectedMinimumNumberOfMatches, long timeout, boolean scroll, boolean onlyVisible, boolean hardStoppage) {
		final long endTime = SystemClock.uptimeMillis() + timeout;

		while (true) {
			final T textViewToReturn = searcher.searchFor(classToFilterBy, text, expectedMinimumNumberOfMatches, scroll, onlyVisible);
			if (textViewToReturn != null ){
				return textViewToReturn;
			}

			final boolean timedOut = SystemClock.uptimeMillis() > endTime;
			if (timedOut){
				return null;
			}

			sleeper.sleep();
		}
	}

	/**
	 * Returns a View.
	 * 
	 * @param index the index of the view
	 * @param classToFilterby the class to filter
	 * @return the specified View
	 */

	public <T extends View> T getView(int index, Class<T> classToFilterBy){
		int numberOfUniqueViews = searcher.getNumberOfUniqueViews();
		ArrayList<T> views = RobotiumUtils.removeInvisibleViews(viewFetcher.getCurrentViews(classToFilterBy));

		if(views.size() < numberOfUniqueViews){
			int newIndex = index - (numberOfUniqueViews - views.size());
			if(newIndex >= 0)
				index = newIndex;
		}

		T view = null;
		try{
			view = views.get(index);
		}catch (IndexOutOfBoundsException exception) {
			int match = index + 1;
			if(match > 1) {
				Assert.fail(match + " " + classToFilterBy.getSimpleName() +"s" + " are not found!");
			}
			else {
				Assert.fail(classToFilterBy.getSimpleName() + " is not found!");
			}
		}
		views = null;
		return view;
	}

	/**
	 * Clears the log.
	 */

	public void clearLog(){
		try {
			Runtime.getRuntime().exec("logcat -c");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
