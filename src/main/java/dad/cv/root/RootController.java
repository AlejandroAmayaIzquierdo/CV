package dad.cv.root;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dad.Conocimiento.Conocimiento;
import dad.Conocimiento.ConocimientoController;
import dad.Experiencia.Experiencia;
import dad.Experiencia.ExperienciaController;
import dad.Formacion.FormacionController;
import dad.Formacion.Titulo;
import dad.contacto.Contacto;
import dad.contacto.ContactoController;
import dad.contacto.Email;
import dad.contacto.Telefono;
import dad.contacto.Web;
import dad.json.ConocimientoAdapter;
import dad.json.EmailAdapter;
import dad.json.ExperenciaAdapter;
import dad.json.LocalDateAdapter;
import dad.json.LocalDateTimeAdapter;
import dad.json.TelefonoAdapter;
import dad.json.TitulosAdapter;
import dad.json.WebAdapter;
import dad.main.App;
import dad.personal.Personal;
import dad.personal.PersonalController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class RootController implements Initializable {
	
	
	//Files
	
//	public static BooleanProperty isEdited = new SimpleBooleanProperty(false);
	
	ObjectProperty<File> actualFile = new SimpleObjectProperty<File>(new File("untitled.cv"));
	
//	File actualFile = new File("untitled.cv");
	
	public Gson gson = FxGson.fullBuilder().setPrettyPrinting()
			.registerTypeAdapter(Conocimiento.class, new ConocimientoAdapter())
			.registerTypeAdapter(Experiencia.class, new ExperenciaAdapter())
			.registerTypeAdapter(Web.class, new WebAdapter())
			.registerTypeAdapter(Email.class, new EmailAdapter())
			.registerTypeAdapter(Titulo.class, new TitulosAdapter())
			.registerTypeAdapter(Telefono.class, new TelefonoAdapter())
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
			.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
			.create();
	
	
	//Controllers
	
	private PersonalController personalController = new PersonalController();
	
	private ContactoController contactoController = new ContactoController();
	
	private FormacionController formacionController = new FormacionController();
	
	private ExperienciaController experienciaController = new ExperienciaController();
	
	private ConocimientoController conocimientoController = new ConocimientoController();
	
	//View

    @FXML
    private Menu archivoMenu,ayudaMenu;
    @FXML
    private MenuItem nuevoItem,abrirItem,guardarComoItem,salirItem,guardarItem,acercaDeItem;
    @FXML
    private MenuBar menuBar;
    @FXML
    private SeparatorMenuItem separatorItem;

    @FXML
    private TabPane cvTabPane;
    @FXML
    private Tab personalTab,contactoTab,formacionTab,experienciaTab,conocimientosTab;


    @FXML
    private BorderPane view;
    
    
	public RootController() throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		personalTab.setContent(personalController.getview());
		contactoTab.setContent(contactoController.getView());
		formacionTab.setContent(formacionController.getView());
		experienciaTab.setContent(experienciaController.getView());
		conocimientosTab.setContent(conocimientoController.getView());
		
		
		actualFile.addListener((o,ov,nv) ->{
			App.mainStage.setTitle("MiCV - " + nv.getName());
		});
	}
	
    @FXML
    public void nuevoArchivoAction(ActionEvent event) {
    	try {
    		ButtonType type = salirAction(event);
    		if(type != buttonTypeCancel) {
    			App.mainStage.close();
    			App.createInstance();
    		}
    		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    @FXML
    public void abrirArchivoAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CV", "*.cv"));
    	
        File tempFile = fileChooser.showOpenDialog(App.mainStage);
		
        
        if(tempFile != null) {        	
        	actualFile.set(tempFile);
        	
        	
        	String personal = geElementById(actualFile.get(),0);
        	personalController.setPersonalModel(gson.fromJson(personal, Personal.class));
        	
        	String contacto = geElementById(actualFile.get(),1);
        	contactoController.setContacto(gson.fromJson(contacto, Contacto.class));
        	
        	
        	String formacion = geElementById(actualFile.get(), 2);
        	Type listType = new TypeToken<ArrayList<Titulo>>(){}.getType();
        	List<Titulo> classListTi = gson.fromJson(formacion, listType);
        	formacionController.setTitulos(classListTi);
        	
        	
        	String experiencia = geElementById(actualFile.get(), 3);
        	listType = new TypeToken<ArrayList<Experiencia>>(){}.getType();
        	List<Experiencia> classListExpe = gson.fromJson(experiencia, listType);
        	experienciaController.setExperiencias(classListExpe);
        	
        	
        	String conocimientos = geElementById(actualFile.get(), 4);
        	listType = new TypeToken<ArrayList<Conocimiento>>(){}.getType();
        	List<Conocimiento> classListCono = gson.fromJson(conocimientos, listType);
        	conocimientoController.setConocimientos(classListCono);
        	
        	
        	
        	
        	
        }
    }
    
    private String geElementById(File file,int num) throws IOException {
    	FileReader fr = new FileReader(file);
    	
    	BufferedReader br = new BufferedReader(fr);
    	String line = br.readLine();
    	String element = "";
    	
    	int count = 0;
    	
    	while(count <= num)
    	{
    		line = br.readLine();
    		
    		if(line.equals(".")) {
    			count++;
    		}else if(count == num) {
    			element += line + "\n";
    		}
    	}
    	
    	return element;
    }

    @FXML
    public void guardarAction(ActionEvent event) throws IOException {
    	
    	if(!actualFile.get().getName().equals("untitled.cv")) {

			String toSave = "";
			
			
			toSave += "\n" + gson.toJson(personalController.getPersonalModel()) + "\n.";
			toSave += "\n" + gson.toJson(contactoController.getContacto()) + "\n.";
			toSave += "\n" + gson.toJson(formacionController.getTitulos()) + "\n.";
			toSave += "\n" + gson.toJson(experienciaController.getExperiencias()) + "\n.";
			toSave += "\n" + gson.toJson(conocimientoController.getConocimientos()) + "\n.";

			System.out.println(toSave);


			Files.write(actualFile.get().toPath(), toSave.getBytes());
    	}else {
    		guardarComoAction(event);
    	}
    	

    	

    }
    @FXML
    public void guardarComoAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CV", "*.cv"));
        
        File tempFile = fileChooser.showSaveDialog(App.mainStage);
        
        if(tempFile != null) {
        	actualFile.set(tempFile);
        	
            System.out.println(actualFile);
            
            try {
    			guardarAction(event);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
    }

    @FXML
    public ButtonType salirAction(ActionEvent event) {
    	Alert alert = saveAlert();
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	
    	if (result.get() == buttonTypeSave) {
    		try {
    			guardarAction(event);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	} else if (result.get() == buttonTypeDoNotSave) {
    		App.mainStage.close();
    	}
    	return result.get();
    	
    }
    
    @FXML
    public void acercarDeAction(ActionEvent event) {

    }
    
	public ButtonType buttonTypeSave = new ButtonType("Save");
	public ButtonType buttonTypeDoNotSave = new ButtonType("Don't Save");
	public ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    
    private Alert saveAlert() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Save");
    	alert.setHeaderText("Save changes before closing?");
    	alert.setContentText(actualFile.get().getName());
    	alert.initOwner(App.mainStage);
    	


    	alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeDoNotSave, buttonTypeCancel);
    	
    	return alert;
    }
	
    
    public BorderPane getView() {
    	return this.view;
    }

}
