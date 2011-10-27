package org.instantplaces.purewidgets.client.widgets.feedback;

import org.instantplaces.purewidgets.client.widgets.GuiWidget;
import org.instantplaces.purewidgets.shared.events.ActionEvent;
import org.instantplaces.purewidgets.shared.events.InputEvent;




/**
 * 
 * Represents the feedback information given by widgets to the users.
 * 
 * @author Jorge C. S. Cardoso
 *
 */
public class InputFeedback<T extends GuiWidget> {
	public enum Type {ACCEPTED, NOT_ACCEPTED};
	
	/**
	 * The InputEvent that originated this feedback.
	 */
	private InputEvent inputEvent;
	
	/**
	 * The information that is going to be displayed
	 */
	private String info;
	
	/**
	 * The type of feedback (Accepted, not accepted)
	 */
	private Type type; 

	/**
	 * The start time for this feedback.
	 */
	private long time;
	
	/**
	 * The action event to trigger if the input was accepted.
	 */
	private ActionEvent<T> actionEvent;
	
	/**
	 * Creates a new InputFeedback object with the given information text and
	 * with the given indication of whether the widget accepted the input or not.
	 * 
	 * @param event The InputEvent that originated the feedback.
	 * @param info The text to show.
	 * @param type The type of feedback.
	 */
	public InputFeedback(InputEvent event) {
		this(event, Type.ACCEPTED, null);
	}

	
	/**
	 * Creates a new InputFeedback object with the given information text and
	 * with the given indication of whether the widget accepted the input or not.
	 * 
	 * @param event The InputEvent that originated the feedback.
	 * @param info The text to show.
	 * @param type The type of feedback.
	 */
	public InputFeedback(InputEvent event,  Type type, ActionEvent<T> ae) {
		this.inputEvent =  event;
		this.actionEvent = ae;
		this.info = "";
		this.type = type;
		this.time = System.currentTimeMillis(); //getTimeStampMilli();
		//System.currentTimeMillis();
		
	}
	
	/*
	
	private long getTimeStampMilli() {
        Date date = new Date();
        return date.getTime();
	}*/

	/**
	 * 
	 * @return The age of this info in milliseconds.
	 */
	public long age() {
		//Log.debug("Now: " + getTimeStampMilli() + "Info time:" + time);
		//return (getTimeStampMilli()-time);
		return System.currentTimeMillis()-this.time;
	}
	
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return this.info+": " + this.type.toString();
	}


	/**
	 * @param inputEvent the inputEvent to set
	 */
	public void setInputEvent(InputEvent inputEvent) {
		this.inputEvent = inputEvent;
	}

	/**
	 * @return the inputEvent
	 */
	public InputEvent getInputEvent() {
		return inputEvent;
	}


	public void setActionEvent(ActionEvent<T> actionEvent) {
		this.actionEvent = actionEvent;
	}


	public ActionEvent<T> getActionEvent() {
		return actionEvent;
	}
}
