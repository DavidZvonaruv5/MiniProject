package client;

import java.io.IOException;
import java.util.HashMap;

import Entity.User;
import common.ChatIF;
import ocsf.client.AbstractClient;

public class ChatClient extends AbstractClient 
{
	ChatIF clientUI;
	private User clientUser;
	/***
	 * s
	 * @param host - saves the data of the ip-address the client entered in order to connect to the server.
	 * @param port - saves the data of the port the client entered in order to connect to the server.
	 * @param clientUI - interface that allows the upper layer of client to communicate with lower layers.
	 * @throws IOException
	 * 
	 *ChatClient is a class that communicate directly with the server. is the highest layered class and closest to the sever in terms of layering.
	 */
	public ChatClient(String host, int port, ChatIF clientUI) throws IOException 
	{
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
		sendToServer(getInetAddress());
	}
	
	/***
	 * @param msg
	 * Function handling @msg that client receives from the server.
	 * every message coming from the server is an object.
	 */
	@Override
	protected void handleMessageFromServer(Object msg) 
	{

		if (msg == null) 
		{
			clientUI.display("#errornoid");
		} else if (msg instanceof Boolean) 
		{
			clientUI.display((Boolean) msg ? "#SucssSubData have been updated sucssfully."
					: "#ErrorSubCould not update Subscriber number. Subscriber number already taken.");
		} 
		else if (msg instanceof HashMap) 
		{
			HashMap<String, String> subscriberDetails = (HashMap<String, String>) msg;
			clientUI.display(subscriberDetails);
		}
	}
	/***
	 * @param message
	 * Function Sending from third layer of client ( this level ) to the server a message with a type of object.
	 */
	public void handleMessageFromClientUI(Object message) 
	{
		try 
		{
			openConnection();
			sendToServer(message);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}