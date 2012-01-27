/**
 * 
 */
package org.instantplaces.purewidgets.server.storage;


import org.instantplaces.purewidgets.server.dao.Dao;
import org.instantplaces.purewidgets.server.dao.StorageDao;



/**
 * The RemoteStorage is a helper class for storing the application's 
 * widgets and name/value pairs on the serverstore.
 * 
 * @author Jorge C. S. Cardoso
 */
public class RemoteStorage {
	
	private String storageId;
	private StorageDao storage;
	
	/**
	 * Creates and empty RemoteStorage object.
	 */
	private RemoteStorage(String placeName, String applicationName) {
		this.storageId = placeName+"-"+applicationName;
		
		/*
		 * Check if exists, if not create
		 * 
		 */
		Dao.beginTransaction();
		StorageDao storage = Dao.getStorage(this.storageId);
		if ( null == storage ) {
			storage = new StorageDao(this.storageId);
			Dao.put(storage);
		}
		Dao.commitOrRollbackTransaction();
	}
	
	public static RemoteStorage get(String placeName, String applicationName) {
		RemoteStorage rs = new RemoteStorage(placeName, applicationName);
		
		
		return rs;
	}
	
	public void open() {
		Dao.beginTransaction();
		this.storage = Dao.getStorage(storageId);
	}
	
	public void close() {
		Dao.put(this.storage);
		Dao.commitOrRollbackTransaction();
	}
	
	public void setString(String name, String value) {
		Dao.beginTransaction();
		this.storage = Dao.getStorage(storageId);
		this.storage.setString(name, value);
		Dao.commitOrRollbackTransaction();
	}
	
	public String getString(String name) {
		Dao.beginTransaction();
		this.storage = Dao.getStorage(storageId);
		
		String value = this.storage.getString(name);
		Dao.commitOrRollbackTransaction();
		return value;
	}
	
	
	
	public void setLong(String name, long value) {
		this.setString(name, Long.toString(value));
	}
	
	public long getLong(String name) {
		String value = this.getString(name);
		return Long.parseLong(value);
	}
	
}