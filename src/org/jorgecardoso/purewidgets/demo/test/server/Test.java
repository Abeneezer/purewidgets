package org.jorgecardoso.purewidgets.demo.test.server;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.instantplaces.purewidgets.server.application.PublicDisplayApplication;
import org.instantplaces.purewidgets.server.application.ApplicationLifeCycle;
import org.instantplaces.purewidgets.shared.Log;
import org.instantplaces.purewidgets.shared.events.ActionEvent;
import org.instantplaces.purewidgets.shared.widgetmanager.WidgetManager;
import org.instantplaces.purewidgets.shared.widgets.TextBox;
import org.instantplaces.purewidgets.shared.widgets.Widget;
import org.instantplaces.purewidgets.shared.widgets.Button;

public class Test extends HttpServlet implements ApplicationLifeCycle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	PublicDisplayApplication app;
	HttpServletRequest req;
	HttpServletResponse resp;
	
	long clicks;
	
	String message;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		
		PublicDisplayApplication.load(req, this);
	}
	
	@Override
	public void loaded(PublicDisplayApplication application) {
		this.app = application;
	}
	
	/**
	 * Called only on the first time
	 */
	@Override
	public void setup() {
		
		Log.debug(this, "Setup");
		
		
		app.setLong("button_1", 0);
		
	}
	
	@Override
	public void start() {
		Button bt = new Button("button_1", "Click me");
		bt.addActionListener(this);
		
		//WidgetManager.get().addWidget(bt);
		app.addWidget(bt);
		
		clicks = app.getLong("button_1");
		
	
		if ( this.clicks > 1 && this.clicks < 3) {
			TextBox text = new TextBox("txt_1", "Gimme text");
			text.addActionListener(this);
			app.addWidget(text);
		}
		
		Log.debug(this, "Loaded clicks: " + clicks);
		
		this.message = "";
	}
	
	@Override
	public void finish() {
		Log.debug(this, "Finish");
		
		
		app.setLong("button_1", clicks);
		
		message += "Clicks: " + clicks + "\n";
		for (Widget w : WidgetManager.get().getWidgetList()) {
			
				message += w.toDebugString() + ";";
			
		}
		
		
		
		try {
			resp.getWriter().write(message);
		} catch (IOException e) {
			Log.error(this, "Could not write the Http response.");
			e.printStackTrace();
		}
	}

	@Override
	public void onAction(ActionEvent<?> ae) {
		//ae = (ActionEvent<? extends Widget>)ae;
		
		Log.debug(this, ae.toDebugString());
		Widget source = (Widget)ae.getSource();
		
		if ( source.getWidgetId().equals("button_1") ) {
			this.clicks++;
			
			if ( this.clicks > 1 && this.clicks < 3) {
				TextBox text = new TextBox("txt_1", "Gimme text");
				app.addWidget(text);
			}
		} else if ( source.getWidgetId().equals("txt_1")) {
			message += ae.getParam() + "\n"; 
				
		}
		
		
	}
}