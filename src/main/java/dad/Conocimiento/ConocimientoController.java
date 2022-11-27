package dad.Conocimiento;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.Conocimiento.dialog.ConocimientoDialog;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ConocimientoController implements Initializable {
	
	//Model
	
	private ListProperty<Conocimiento> conocimientos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty selectedConocimiento = new SimpleIntegerProperty();
	
	//View
	
    @FXML
    private TableView<Conocimiento> conocimientoTableView;

    @FXML
    private TableColumn<Conocimiento, String> denominacionTableCollumn;

    @FXML
    private TableColumn<Conocimiento, Nivel> nivelTableCollumn;

    @FXML
    private BorderPane view;
	
	public ConocimientoController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Bindings
		
		conocimientoTableView.itemsProperty().bind(conocimientos);
		selectedConocimiento.bind(conocimientoTableView.getSelectionModel().selectedIndexProperty());
		
		//Cell value factory
		
		denominacionTableCollumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		nivelTableCollumn.setCellValueFactory(v -> v.getValue().nivelProperty());
	}
	
    @FXML
    void onAddConocimiento(ActionEvent event) {
		List<Nivel> a = new ArrayList<Nivel>(Arrays.asList(Nivel.values()));
    	
    	ConocimientoDialog dialog = new ConocimientoDialog(Nivel.Basico, a);
    	dialog.setTitle("Nuevo conocimiento");
    	Optional<Conocimiento> result = dialog.showAndWait();
    	if(result.isPresent()) {
    		conocimientos.add(result.get());
    	}
    }
	

    @FXML
    void onAddIdioma(ActionEvent event) {

    }

    @FXML
    void onRemove(ActionEvent event) {
    	if(selectedConocimiento.get() > -1) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Eliminar Conocimiento");
    		alert.setHeaderText("Estas seguro de eliminar el conocimiento");
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK) {
    			conocimientos.remove(selectedConocimiento.get());
    		}
    		
    	}
    }
    
    public BorderPane getView() {
    	return this.view;
    }

	public final ListProperty<Conocimiento> conocimientosProperty() {
		return this.conocimientos;
	}
	

	public final ObservableList<Conocimiento> getConocimientos() {
		return this.conocimientosProperty().get();
	}
	

	public final void setConocimientos(final List<Conocimiento> conocimientos) {
		this.conocimientos.clear();
		
		for(Conocimiento i : conocimientos) {
			this.conocimientos.add(i);
		}
	}
	

	public final IntegerProperty selectedConocimientoProperty() {
		return this.selectedConocimiento;
	}
	

	public final int getSelectedConocimiento() {
		return this.selectedConocimientoProperty().get();
	}
	

	public final void setSelectedConocimiento(final int selectedConocimiento) {
		this.selectedConocimientoProperty().set(selectedConocimiento);
	}
	
    
    

}
