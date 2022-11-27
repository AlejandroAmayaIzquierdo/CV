package dad.contacto;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dad.contacto.dialog.PhoneDialog;
import dad.util.CSV;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

public class ContactoController implements Initializable {
	
	//Model
	
	private Contacto contacto = new Contacto();
	
	private IntegerProperty selectedPhone = new SimpleIntegerProperty();
	private IntegerProperty selectedEmail = new SimpleIntegerProperty();
	private IntegerProperty selectedWeb = new SimpleIntegerProperty();
	
	//View

    @FXML
    private TableView<Telefono> telefonosTableView;
    @FXML
    private TableView<Email> emailTableView;
    @FXML
    private TableView<Web> websTableView;
    
    @FXML
    private TableColumn<Email, String> emailCollumn;
    @FXML
    private TableColumn<Telefono, TipoTelefono> tipoTelefonoCollumn;
    @FXML
    private TableColumn<Telefono, String> numeroTelefonoCollumn;
    @FXML
    private TableColumn<Web, String> urlWebCollumn;


    @FXML
    private SplitPane view;


    
    public ContactoController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
    
    
    
    public Contacto getContacto() {
		return contacto;
	}



	public void setContacto(Contacto contacto) {
		
		this.getContacto().setTelefonos(contacto.getTelefonos());
		this.getContacto().setEmails(contacto.getEmails());
		this.getContacto().setWebs(contacto.getWebs());
	}



	@Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	//Bindings
    	
			//tables
		
    	emailTableView.itemsProperty().bindBidirectional(contacto.emailsProperty());
    	telefonosTableView.itemsProperty().bindBidirectional(contacto.telefonosProperty());
    	websTableView.itemsProperty().bindBidirectional(contacto.websProperty());
    	
    		//Selections
    	
    	selectedPhone.bind(telefonosTableView.getSelectionModel().selectedIndexProperty());
    	selectedEmail.bind(emailTableView.getSelectionModel().selectedIndexProperty());
    	selectedWeb.bind(websTableView.getSelectionModel().selectedIndexProperty());
    	
    	//Cell value factory
    	
    	
    	numeroTelefonoCollumn.setCellValueFactory(v -> v.getValue().numeroProperty());
    	tipoTelefonoCollumn.setCellValueFactory(v -> v.getValue().tipoProperty());
    	
    	emailCollumn.setCellValueFactory(v -> v.getValue().direccionProperty());
    	
    	urlWebCollumn.setCellValueFactory(v -> v.getValue().urlProperty());
    	
    	
    }
    @FXML
    void onAddPhoneAction(ActionEvent event) {
    	try {
			List<TipoTelefono> a = new ArrayList<TipoTelefono>(Arrays.asList(TipoTelefono.values()));
			
	    	PhoneDialog phone = new PhoneDialog<>(TipoTelefono.Movil, a);
	    	phone.setTitle("Nuevo teléfono");
	    	phone.setHeaderText("Introduzca el nuevo número de teléfono");
	    	Optional<String[]> result = phone.showAndWait();
	    	if (result.isPresent() && !contacto.getTelefonos().contains(new Telefono(result.get()[1],result.get()[0]))) {
	    		contacto.getTelefonos().add(new Telefono(result.get()[1],result.get()[0]));
	    	}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void onRemovePhoneAction(ActionEvent event) {
    	if(selectedPhone.get() > -1) {
    		contacto.getTelefonos().remove(selectedPhone.get());
    	}
    }
    
    @FXML
    void onAddEmailAction(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Nuevo e-mail");
    	dialog.setHeaderText("Crear una nueva dirección de correo.");
    	dialog.setContentText("E-mail: ");
    	Optional<String> result = dialog.showAndWait();
    	if(result.isPresent() && !contacto.getEmails().contains(new Email(result.get()))) {
    		contacto.emailsProperty().add(new Email(result.get()));
    	}
    	
    }
    @FXML
    void onRemoveEmailAction(ActionEvent event) {
    	if(selectedEmail.get() > -1) {
    		contacto.getEmails().remove(selectedEmail.get());
    	}
    }

    @FXML
    void onAddWebAction(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog("http://");
    	dialog.setTitle("Nueva Web");
    	dialog.setHeaderText("Crear una nueva dirección web.");
    	dialog.setContentText("URL: ");
    	Optional<String> result = dialog.showAndWait();
    	if(result.isPresent() && !contacto.getWebs().contains(new Web(result.get()))) {
    		contacto.websProperty().add(new Web(result.get()));
    	}
    }
    @FXML
    void onRemoveWebAction(ActionEvent event) {
    	if(selectedWeb.get() > -1) {
    		contacto.websProperty().remove(selectedWeb.get());
    	}
    }
    
    public SplitPane getView() {
    	return this.view;
    }

}
