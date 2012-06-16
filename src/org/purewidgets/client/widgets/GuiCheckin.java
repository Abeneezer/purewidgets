package org.purewidgets.client.widgets;

import org.purewidgets.shared.Log;
import org.purewidgets.shared.widgets.Checkin;

public class GuiCheckin extends GuiWidget{

	private Checkin checkin;
	
	public GuiCheckin() {
		checkin = new Checkin();
		this.setWidget(checkin);
		
		Log.debug(this, "Sending to server");
		this.sendToServer();
	}
}