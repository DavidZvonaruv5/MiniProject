package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ServerHandlers.SearchUserHandler;
import ServerHandlers.UpdateUserHandler;
import common.ChatIF;
import common.ClientConnection;
import common.MessageHandler;
import database.DBConnect;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class EchoServer extends AbstractServer 
{
	ChatIF serverUI;
	DBConnect mySqlConnection;
	private Map<String,MessageHandler> handlers;
	private ArrayList<String> serverConfing;
	
	public EchoServer(int port) 
	{
		super(port);
		handlers = new HashMap<>();
	}

	public EchoServer(int port, ChatIF serverUI) 
	{
		this(port);
		this.serverUI = serverUI;
	}

	boolean isServerClosed;
	protected void serverStarted() 
	{
		serverUI.display("Server listening for connections on port " + getPort());
		serverUI.setButtons(true);
		isServerClosed = false;
		UpdateUserHandler Userupdatehandler = new UpdateUserHandler(mySqlConnection, serverUI);
		SearchUserHandler Usersearchhandler = new SearchUserHandler(mySqlConnection, serverUI);
		
		handlers.put("UPDATE_USER", Userupdatehandler);
		handlers.put("USER_SEARCH", Usersearchhandler);
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() 
	{
		serverUI.display("Server has stopped listening for connections.");
		isServerClosed = true;
	}
	
	// Register a message handler for a given message type.
	public void registerHandler(String type, MessageHandler handler)
	{
		handlers.put(type, handler);
	}
	public void handleMessageFromServerUI(Object message) {
		if(message instanceof ArrayList)
		{
			serverConfing = (ArrayList<String>)message;
			mySqlConnection = new DBConnect(serverUI, serverConfing);
			mySqlConnection.connectToDB();
		}
		else if (message instanceof String) 
		{
			String strMessage = (String) message;
			if (strMessage.charAt(0) == '#') 
			{
				try 
				{
					handleServerCommands(strMessage);
				} catch (IOException e) 
				{
					serverUI.display(e.getMessage());
				}
			}
		}
	}

	private void handleServerCommands(String message) throws IOException {
		switch (message) {
		case "#close":
			close();
			serverUI.setButtons(false);
			break;
		}
	}

	protected void clientConnected(ConnectionToClient client) {
		ClientConnection clientToShow = new ClientConnection(client);
		serverUI.display("Client Connected");
		serverUI.display(clientToShow);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) 
	{
		if(msg instanceof String)
		{
			String message = (String) msg;
			
			int spaceIndex = message.indexOf(",");
			String type = (spaceIndex == -1) ? message : message.substring(0, spaceIndex);
			MessageHandler handler = handlers.get(type);
			
			if(handler != null)
			{
				handler.handle(message.substring(spaceIndex+1),client);
			}
			else 
			{
				serverUI.display(String.format(">Server Message: Error command{%s} is not existing", type));
			}
		}
	}

}
