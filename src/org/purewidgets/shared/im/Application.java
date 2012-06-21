/**
 * 
 */
package org.purewidgets.shared.im;

/**
 * @author "Jorge C. S. Cardoso"
 *
 */
public class Application {
	public static enum STATE {All, Active, Inactive};
	
	private String placeId;
	
	private String applicationId;
	
	private String applicationBaseUrl;
	
	public Application(String placeId, String appId) {
		this.placeId = placeId;
		this.applicationId = appId;
	}
	
	
	/**
	 * @return the placeId
	 */
	public String getPlaceId() {
		return placeId;
	}
	

	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	

	/**
	 * @return the appId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	
	/**
	 * @param appId the appId to set
	 */
	public void setApplicationId(String appId) {
		this.applicationId = appId;
	}


	/**
	 * @return the applicationBaseUrl
	 */
	public String getApplicationBaseUrl() {
		return applicationBaseUrl;
	}


	/**
	 * @param applicationBaseUrl the applicationBaseUrl to set
	 */
	public void setApplicationBaseUrl(String applicationBaseUrl) {
		this.applicationBaseUrl = applicationBaseUrl;
	}


	
	
}
