package dad.Experiencia;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.Experiencia.dialog.ExperienciaDialog;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateTimeStringConverter;

public class ExperienciaController implements Initializable {
	
	//Model
	
	private ListProperty<Experiencia> experiencias = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty selectedExperiencia = new SimpleIntegerProperty();
	
	//View
	

    @FXML
    private TableColumn<Experiencia, String> denominacionTableCollumn;

    @FXML
    private TableColumn<Experiencia, LocalDateTime> desdeTableCollumn;

    @FXML
    private TableColumn<Experiencia, String> empleadorTableCollumn;

    @FXML
    private TableView<Experiencia> experienciaTableView;

    @FXML
    private TableColumn<Experiencia, LocalDateTime> hastaTableCollumn;

    @FXML
    private BorderPane view;


	
	public ExperienciaController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Bindings
		
		experienciaTableView.itemsProperty().bind(experiencias);
		selectedExperiencia.bind(experienciaTableView.getSelectionModel().selectedIndexProperty());
		
		//Cell value factory
		
		desdeTableCollumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaTableCollumn.setCellValueFactory(v -> v.getValue().hastaProperty());
		denominacionTableCollumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		empleadorTableCollumn.setCellValueFactory(v -> v.getValue().empleadorProperty());
		
		//Cell factory
		
		desdeTableCollumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
		hastaTableCollumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
		
	}
	
    @FXML
    void onAddAction(ActionEvent event) {
    	ExperienciaDialog experienciaDialog = new ExperienciaDialog();
    	experienciaDialog.setTitle("Nueva experiencia");
    	Optional<Experiencia> result = experienciaDialog.showAndWait();
    	
    	if(result.isPresent()) {
    		experiencias.add(result.get());
    	}
    }

    @FXML
    void onRemoveAction(ActionEvent event) {
    	if(selectedExperiencia.get() > -1) {
    		experiencias.remove(selectedExperiencia.get());
    	}
    }
    
    public BorderPane getView() {
    	return this.view;
    }

	public final ListProperty<Experiencia> experienciasProperty() {
		return this.experiencias;
	}
	

	public final ObservableList<Experiencia> getExperiencias() {
		return this.experienciasProperty().get();
	}
	

	public final void setExperiencias(final List<Experiencia> experiencias) {
		this.experiencias.clear();
		
		for(Experiencia i : experiencias) {
			this.experiencias.add(i);
		}
	}
	

	public final IntegerProperty selectedExperienciaProperty() {
		return this.selectedExperiencia;
	}
	

	public final int getSelectedExperiencia() {
		return this.selectedExperienciaProperty().get();
	}
	

	public final void setSelectedExperiencia(final int selectedExperiencia) {
		this.selectedExperienciaProperty().set(selectedExperiencia);
	}
	
    

}
