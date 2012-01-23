/**
 * 
 */
package org.jorgecardoso.purewidgets.demo.everybodyvotes.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.instantplaces.purewidgets.client.application.PublicDisplayApplication;
import org.instantplaces.purewidgets.client.application.PublicDisplayApplicationLoadedListener;
import org.instantplaces.purewidgets.client.storage.LocalStorage;
import org.instantplaces.purewidgets.client.widgets.GuiListBox;
import org.instantplaces.purewidgets.client.widgets.GuiTextBox;
import org.instantplaces.purewidgets.shared.Log;
import org.instantplaces.purewidgets.shared.widgetmanager.WidgetManager;
import org.instantplaces.purewidgets.shared.widgets.TextBox;
import org.jorgecardoso.purewidgets.demo.everybodyvotes.client.service.PollService;
import org.jorgecardoso.purewidgets.demo.everybodyvotes.client.service.PollServiceAsync;
import org.jorgecardoso.purewidgets.demo.everybodyvotes.shared.dao.EBVPollDao;
import org.jorgecardoso.purewidgets.demo.everybodyvotes.shared.dao.EBVPollOptionDao;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.ChartArea;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.BarChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;



/**
 * @author "Jorge C. S. Cardoso"
 *
 */
public class EveryBodyVotes implements PublicDisplayApplicationLoadedListener, EntryPoint{
	
	private static final String LS_CURRENT_POLL_INDEX = "currentPollIndex";
	
	private static final int POLL_DISPLAY_INTERVAL = 60000; 
	
	private List<EBVPollDao> polls;
	private PollServiceAsync pollService;
	
	private LocalStorage localStorage;
	
	private int currentPollIndex;
	
	private Timer timer;
	
	@Override
	public void onModuleLoad() {

		VisualizationUtils.loadVisualizationApi(new Runnable() {

			@Override
			public void run() {
				PublicDisplayApplication.load(EveryBodyVotes.this, "EveryBodyVotes", false);
				
			}
			
		} , BarChart.PACKAGE);
		
		
	}

	@Override
	public void onApplicationLoaded() {
		String page = Window.Location.getPath();
		if ( page.contains("admin.html") ) {
			new Admin().run();
			return;
		} 
		WidgetManager.get().setAutomaticInputRequests(true);
		
		this.localStorage = PublicDisplayApplication.getLocalStorage();
		Integer currentPoll =  this.localStorage.getInteger(LS_CURRENT_POLL_INDEX);
		if ( null == currentPoll ) {
			this.currentPollIndex = -1;
		} else {
			this.currentPollIndex = currentPoll.intValue();
		}
		
		pollService = GWT.create(PollService.class);
		((ServiceDefTarget)pollService).setServiceEntryPoint("/pollservice"); 
		
		this.askForPollList();
		
	}
	
	
	

	private void askForPollList() {
		if ( null != this.polls ) {
			this.polls.clear();
		}
		
		pollService.getPolls(PublicDisplayApplication.getPlaceName(), new AsyncCallback<List<EBVPollDao>> () {

			@Override
			public void onFailure(Throwable caught) {
				Log.warn(EveryBodyVotes.class.getName(), "Oops! " + caught.getMessage() );
			}

			@Override
			public void onSuccess(List<EBVPollDao> result) {
				if ( null == result || result.size() == 0 ) {
					Log.warn(EveryBodyVotes.class.getName(), "No polls found");
				}
				EveryBodyVotes.this.polls = result;
				EveryBodyVotes.this.advancePoll();
				
			}
		});
	}
	
	
	private void showPoll(EBVPollDao poll) {
		long today = new Date().getTime();
		
		if ( poll.getClosesOn() < today ) { // closed poll
			showClosedPoll(poll);
		} else {
			showClosedPoll(poll);
		}	
	}
	
	private void showOpenPoll(EBVPollDao poll) {
		ArrayList<String> l = new ArrayList<String>();
		for ( EBVPollOptionDao pollOption : poll.getPollOptions() ) {
			l.add(pollOption.getOption());
		}
		
		GuiListBox tb = new GuiListBox("poll " + poll.getPollId(), poll.getPollQuestion(), l);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(tb);
		
	}
	
	private void showClosedPoll(EBVPollDao poll) {
		DataTable dt = DataTable.create();
		dt.addColumn(ColumnType.STRING, "Option");
		dt.addColumn(ColumnType.NUMBER, "Votes"); 
		for ( EBVPollOptionDao pollOption : poll.getPollOptions() ) {
			int i = dt.addRow();
			dt.setValue(i, 0, pollOption.getOption() + " (" + pollOption.getVotes()+")");
			dt.setValue(i, 1, pollOption.getVotes());
		}
		
		//PieChart.PieOptions options = PieChart.PieOptions.create();
		Options options = Options.create();

		options.setWidth(500);
	    options.setHeight(200);
	    //options.set3D(false);
	    options.setFontSize(23);
	    
	    options.setLegend(LegendPosition.NONE);
	    ChartArea ca = ChartArea.create();
	    ca.setTop(0);
	    ca.setLeft(0);
	    ca.setWidth("100%");
	    ca.setHeight("100%");
	    options.setChartArea(ca);
	   // options.setTitle("title");
	    options.setAxisTitlesPosition("none");
	    AxisOptions ao = AxisOptions.create();
	    ao.setTextPosition("in");
	    options.setVAxisOptions(ao);
	    ao = AxisOptions.create();
	    ao.setTextPosition("none");
	    options.setHAxisOptions(ao);
		BarChart pie = new BarChart(dt, options);
		pie.setWidth("500px");
		
		RootPanel.get("content").clear();
		Label title = new Label("Results for \"" + poll.getPollQuestion() + "\"");
		
		RootPanel.get("content").add(title);
		RootPanel.get("content").add(pie);
		
		GuiTextBox suggest = new GuiTextBox("suggest", "Suggest a poll");
		suggest.setWidth("500px");
		RootPanel.get("content").add(suggest);
	}
	
	
	private void advancePoll() {
		if ( null == this.polls || this.polls.size() < 1 ) {
			return;
		}
		
		this.currentPollIndex++;
		
		if ( this.currentPollIndex > this.polls.size()-1 ) {
			this.currentPollIndex = 0;
		}
		
		this.localStorage.setInt(LS_CURRENT_POLL_INDEX, this.currentPollIndex);
		
		
		this.showPoll(this.polls.get(this.currentPollIndex));
		
		if ( null == timer ) {
			timer = new Timer() {
				@Override
				public void run() {
					EveryBodyVotes.this.timerElapsed();
				}
			};
		}
		timer.schedule(POLL_DISPLAY_INTERVAL);
	}

	protected void timerElapsed() {
		this.advancePoll();
	}
}
