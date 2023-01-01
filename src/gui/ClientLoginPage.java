package gui;

import java.net.URL;
import java.util.ResourceBundle;

import Entity.Employee;
import Entity.User;
import client.ClientUI;
import common.IController;
import common.RequestObjectClient;
import common.ResponseObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ClientLoginPage implements Initializable, IController
{
	private boolean isLogged;
	private boolean isEmployee = false; /////////
	private String role;

	private User user;
    @FXML
    private VBox vboxlogo;

    @FXML
    private ImageView exitbutton;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;
    
    @FXML
    private Button LoginButton;

    @FXML
    private Button LoginApp;
    
    @FXML
    void ExitWindow(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void actionLoggin(ActionEvent event) 
    {
    	if(userNameTextField.getText() == null || passwordTextField.getText() == null)
    		return;
    	String userName = userNameTextField.getText();
    	String password = passwordTextField.getText();
    	
//    	RequestObjectClient request = new RequestObjectClient("#USER_LOGIN_DATA",String.format("table=users#condition=userName=%s&userPassword=%s#values=userName=username&userPassword=password", userName, password),"GET");    	
    	RequestObjectClient request = new RequestObjectClient("#USER_LOGIN_DATA",String.format("table=users#condition=userName=%s&userPassword=%s", userName, password),"GET");    	
    	
    	ClientUI.clientController.accept(request);
    	System.out.println("Hey" + Thread.currentThread().getName());

    	if(isLogged)
    	{
    		((Node) event.getSource()).getScene().getWindow().hide();
    		request = new RequestObjectClient("#USER_IS_EMPLOYEE",String.format("table=Employees#condition=userName=%s", userName),"GET");
    		ClientUI.clientController.accept(request);
    		if(isEmployee)
    		{
    			String open= new String(); 
    			open=String.format("../views/%sInterface.fxml",role);
    			ClientUI.sceneManager.ShowScene(open);
    			return;
    		}
    		 
    		
    		ClientUI.sceneManager.ShowScene("../views/Homepage.fxml");		
    	}
    	else
    	{
//    		System.out.println("Login failed");
//			Alert alert = new Alert(AlertType.ERROR, "Laptop LG 2 hours");
//			alert.showAndWait();
    	}

    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		user = new User(null,null);
		exitbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("Hello");
			event.consume();
		});
    }


	@Override
	public void updatedata(Object data) {
		// TODO Auto-generated method stub
		//Platform.runLater(() -> {
			System.out.println("ClientLoginPage");
			if(data instanceof ResponseObject)
			{
				ResponseObject serverResponse = (ResponseObject) data;
				switch(serverResponse.getRequest())
				{	
					case"#USER_LOGIN_DATA":
					{
						if(serverResponse.Responsedata.size() != 0)
						{
							isLogged = true;
							Object[] values =(Object[]) serverResponse.Responsedata.get(0);//Row 1 
							
//							String firstName = (String)values[0];
//							String LastName = (String)values[1];
//							String Telephone = (String)values[2];
//							String Email = (String)values[3];
//							String ID = (String)values[4];
//							String userName = (String)values[5];
//							String userPassword = (String)values[6];
							user.setFirstName((String)values[0]);
							user.setLastName((String)values[1]);
							user.setPhone((String)values[2]);
							
							user.setEmail((String)values[3]);
							
							user.setID((String)values[4]);
							user.setUserName((String)values[5]);
							
							user.setPassword((String)values[6]);
							user.setArea((String)values[8]);
							//	public User(String firstName, String lastName, String phone, String email, String ID, String UserName,String Area
							ClientUI.clientController.setUser(user); 
							System.out.println("Hey 2" + Thread.currentThread().getName());
							
						}
						break;
						
					}
					case "#USER_IS_EMPLOYEE":
					{
						
						if(serverResponse.Responsedata.size() != 0)
						{
							Object[] values =(Object[]) serverResponse.Responsedata.get(0);//Row 1 
							role=(String)values[0];
//							String userName=(String)values[1];
							String branch=(String)values[2];
							isEmployee=true;
							System.out.println("Employee");
							Employee employee = new Employee(user,branch);
							ClientUI.clientController.setUser(employee); 
						}
					}
					break;
					
				}
			}
		//});
	}

}
