/**
 * 
 */
package org.jorgecardoso.purewidgets.demo.everybodyvotes.server.service;


import java.util.List;

import org.jorgecardoso.purewidgets.demo.everybodyvotes.client.service.PollService;
import org.jorgecardoso.purewidgets.demo.everybodyvotes.server.dao.Dao;
import org.jorgecardoso.purewidgets.demo.everybodyvotes.shared.dao.EBVPollDao;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author "Jorge C. S. Cardoso"
 *
 */
public class PollServiceImpl extends RemoteServiceServlet implements PollService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	@Override
	public List<EBVPollDao> getPolls(String placeId) {
		
		List<EBVPollDao> polls = Dao.getPolls(placeId);
		
		
		return polls;
	}

}