/**
 * 
 */
package org.instantplaces.purewidgets.client.storage;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author "Jorge C. S. Cardoso"
 *
 */
public class RemoteStorage {

	private String storageId;
	private RemoteStorageServiceAsync remoteStorageService;
	
	public RemoteStorage(String placeName, String appName) {
		this.storageId = placeName+"-"+appName;
		remoteStorageService = GWT.create(RemoteStorageService.class);
		((ServiceDefTarget)remoteStorageService).setServiceEntryPoint("/storageservice"); 
	}
	
	public void setString(String name, String value, AsyncCallback<Void> callback) {
		this.remoteStorageService.set(this.storageId, name, value, callback);
	}
	
	public void getString(String name, AsyncCallback<String> callback) {
		this.remoteStorageService.get(this.storageId, name, callback);
	}
	
	
}
