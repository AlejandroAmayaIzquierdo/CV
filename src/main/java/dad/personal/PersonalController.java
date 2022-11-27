package dad.personal;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.util.CSV;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {
	
	//Model
	
	private Personal personalModel = new Personal();
	private ListProperty<String> paises = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<String> Allnacionalities = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty selectetdNacionalidad = new SimpleIntegerProperty();

	
	//View
	
    @FXML
    private TextField apellidosTextField;

    @FXML
    private TextField codigoPostalTextField;

    @FXML
    private TextArea direccionTextField;

    @FXML
    private TextField dniTextField;

    @FXML
    private DatePicker fechaNacimientoDatePicker;

    @FXML
    private TextField localidadTextField;

    @FXML
    private ListView<Nacionalidad> nacionalidadListView;

    @FXML
    private TextField nombreTextField;

    @FXML
    private ComboBox<String> paisComboBox;

    @FXML
    private GridPane view;
	
	public PersonalController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	
	
	public Personal getPersonalModel() {
		return personalModel;
	}



	public void setPersonalModel(Personal personalModel) {
		getPersonalModel().setIdentificador(personalModel.getIdentificador());
		getPersonalModel().setNombre(personalModel.getNombre());
		getPersonalModel().setApellidos(personalModel.getApellidos());
		getPersonalModel().setFechaNacimiento(personalModel.getFechaNacimiento());
		getPersonalModel().setDireccion(personalModel.getDireccion());
		getPersonalModel().setCodigoPostal(personalModel.getCodigoPostal());
		getPersonalModel().setLocalidad(personalModel.getLocalidad());
		String miPais = personalModel.getPais();
		if(miPais != null && !miPais.isBlank() && !miPais.isEmpty()) {
			this.paisComboBox.getSelectionModel().select(miPais);
		}
		getPersonalModel().setNacionalidades(personalModel.getNacionalidades());
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Load Files

		try {
			CSV.readAllLines(Paths.get(getClass().getResource("/csv/paises.csv").toURI())).forEach(var -> {
				paises.add(var[0]);
			});
			
			CSV.readAllLines(Paths.get(getClass().getResource("/csv/nacionalidades.csv").toURI())).forEach(var -> {
				Allnacionalities.add(var[0]);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Bindings
		
		personalModel.nombreProperty().bindBidirectional(nombreTextField.textProperty());
		personalModel.identificadorProperty().bindBidirectional(dniTextField.textProperty());
		personalModel.apellidosProperty().bindBidirectional(apellidosTextField.textProperty());
		personalModel.fechaNacimientoProperty().bindBidirectional(fechaNacimientoDatePicker.valueProperty());
		personalModel.direccionProperty().bindBidirectional(direccionTextField.textProperty());
		personalModel.codigoPostalProperty().bindBidirectional(codigoPostalTextField.textProperty());
		personalModel.localidadProperty().bindBidirectional(localidadTextField.textProperty());
		paisComboBox.itemsProperty().bindBidirectional(paises);
		personalModel.paisProperty().bind(paisComboBox.getSelectionModel().selectedItemProperty());
		nacionalidadListView.itemsProperty().bindBidirectional(personalModel.nacionalidadesProperty());
		selectetdNacionalidad.bind(nacionalidadListView.getSelectionModel().selectedIndexProperty());
	}
	
    @FXML
    void addNacionalidad(ActionEvent event) {
		ChoiceDialog<String> dialog = new ChoiceDialog<String>("afgano", Allnacionalities);
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir nacionalidad");
		dialog.setContentText("Seleccione una nacionalidad:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !personalModel.getNacionalidades().contains(new Nacionalidad(result.get()))) {
			personalModel.nacionalidadesProperty().add(new Nacionalidad(result.get()));
		}
    }

    @FXML
    void subtractNacionalidad(ActionEvent event) {
    	if(selectetdNacionalidad.intValue() > -1) {
    		personalModel.nacionalidadesProperty().remove(selectetdNacionalidad.intValue());
    	}
    	
    }
	
	public GridPane getview() {
		return this.view;
	}

	public final ListProperty<String> paisesProperty() {
		return this.paises;
	}
	

	public final ObservableList<String> getPaises() {
		return this.paisesProperty().get();
	}
	

	public final void setPaises(final ObservableList<String> paises) {
		this.paisesProperty().set(paises);
	}
	
	

	public final ListProperty<String> AllnacionalitiesProperty() {
		return this.Allnacionalities;
	}
	

	public final ObservableList<String> getAllnacionalities() {
		return this.AllnacionalitiesProperty().get();
	}
	

	public final void setAllnacionalities(final ObservableList<String> Allnacionalities) {
		this.AllnacionalitiesProperty().set(Allnacionalities);
	}
	

	public final IntegerProperty selectetdNacionalidadProperty() {
		return this.selectetdNacionalidad;
	}
	

	public final int getSelectetdNacionalidad() {
		return this.selectetdNacionalidadProperty().get();
	}
	

	public final void setSelectetdNacionalidad(final int selectetdNacionalidad) {
		this.selectetdNacionalidadProperty().set(selectetdNacionalidad);
	}
	

}
