package com.robotium.solo;


import junit.framework.Assert;
import android.app.Instrumentation;
import android.view.KeyEvent;

/**
 * Contains send key event methods. Examples are:
 * sendKeyCode(), goBack()
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

class Sender {

	private final Instrumentation inst;
	
	
	/**
	 * Constructs this object.
	 * 
	 * @param inst the {@code Instrumentation} instance
	 * @param sleeper the {@code Sleeper} instance
	 */

	Sender(Instrumentation inst, Sleeper sleeper) {
		this.inst = inst;
	}

	/**
	 * Tells Robotium to send a key code: Right, Left, Up, Down, Enter or other.
	 * 
	 * @param keycode the key code to be sent. Use {@link KeyEvent#KEYCODE_ENTER}, {@link KeyEvent#KEYCODE_MENU}, {@link KeyEvent#KEYCODE_DEL}, {@link KeyEvent#KEYCODE_DPAD_RIGHT} and so on
	 */

	public void sendKeyCode(int keycode)
	{
		try{
			inst.sendCharacterSync(keycode);
		}catch(SecurityException e){
			Assert.fail("Can not complete action! ("+(e != null ? e.getClass().getName()+": "+e.getMessage() : "null")+")");
		}
	}

	/**
	 * Simulates pressing the hardware back key.
	 */

	public void goBack() {
		try {
			inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
		} catch (Throwable ignored) {}
	}
}
