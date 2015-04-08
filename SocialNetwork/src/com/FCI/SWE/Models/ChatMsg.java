package com.FCI.SWE.Models;
/**
 * <h1>ChatMsg class</h1>
 * <p>
 * This class will act as a subclass of Messages to deal with one to one message chat
 * </p>
 *
 * @author  Yasmine Abdel Latif, Nour Mohammed Srour Alwani, Mariam Fouad, Salwa
*         Ahmed, Huda Mohammed
 * @version 1.0
 * @since 2014-02-12
 */
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class ChatMsg extends Messages {

	/**
	 * Constructor 
	 * 
	 * @param recievers of the message
	 * @param sender of the message
	 * @param msg 
	 */
	public ChatMsg(String reciever, String sender, String msg )
	{
		super(reciever, sender, msg);
		type = false;
	}
	
	/**
	 * SendMessage Method: Sends a message from a sender to a set of receivers
	 * @return Boolean indicating the status
	 */
	public Boolean sendMessage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("message");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		Entity message = new Entity("message", list.size()+1);

		message.setProperty("Type", type);
		message.setProperty("Sender", sender);
		message.setProperty("Reciever", reciever);
		message.setProperty("Msg", msg);
		message.setProperty("Seen", false);
		datastore.put(message);
		
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("Notifications");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		List<Entity> list1 = pq1.asList(FetchOptions.Builder.withDefaults());
		Entity messageNotify = new Entity("Notifications", list1.size()+1);

		messageNotify.setProperty("Type", 3);
		messageNotify.setProperty("Sender", sender);
		messageNotify.setProperty("Name", reciever);
		messageNotify.setProperty("Msg", msg);
		messageNotify.setProperty("Seen", false);
		datastore1.put(messageNotify);
		return true;
	}
}
