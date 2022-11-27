package dad.Formacion;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.Formacion.titulo.TituloDialog;
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

public class FormacionController implements Initializable {
	
	//Model
	
	
	private ListProperty<Titulo> titulos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty selectedTitulo = new SimpleIntegerProperty();
	
	//View
	
    @FXML
    private TableView<Titulo> formacionTableView;
    @FXML
    private TableColumn<Titulo, LocalDateTime> desdeTableCollumn;
    @FXML
    private TableColumn<Titulo, LocalDateTime> hastaTableCollumn;
    @FXML
    private TableColumn<Titulo, String> denominacionTableCollumn;
    @FXML
    private TableColumn<Titulo, String> organizadorTableCollumn;

    @FXML
    private BorderPane view;
    
    
	
	public FormacionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Bindings
		formacionTableView.itemsProperty().bindBidirectional(titulos);
		selectedTitulo.bind(formacionTableView.getSelectionModel().selectedIndexProperty());
		
		//Cell value factory
		
		desdeTableCollumn.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaTableCollumn.setCellValueFactory(v -> v.getValue().hastaProperty());
		denominacionTableCollumn.setCellValueFactory(v -> v.getValue().denominacionProperty());
		organizadorTableCollumn.setCellValueFactory(v -> v.getValue().organizadorProperty());
		
		//Cell factory
		
		desdeTableCollumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
		hastaTableCollumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
	}
	
	
    @FXML
    void onAddAction(ActionEvent event) {
    	TituloDialog tituloDialog = new TituloDialog();
    	tituloDialog.setTitle("Nuevo título");
    	Optional<Titulo> result = tituloDialog.showAndWait();
    	
    	if(result.isPresent()) {
    		titulos.add(result.get());
    	}
    }

    @FXML
    void onRemoveAction(ActionEvent event) {
    	if(selectedTitulo.get() > -1) {
    		titulos.remove(selectedTitulo.get());
    	}
    }
    
    public BorderPane getView() {
    	return this.view;
    }

	public final ListProperty<Titulo> titulosProperty() {
		return this.titulos;
	}
	

	public final ObservableList<Titulo> getTitulos() {
		return this.titulosProperty().get();
	}
	

	public final void setTitulos(final List<Titulo> titulos) {
		this.titulos.clear();
		for(Titulo i : titulos) {
			this.titulos.add(i);
		}
	}
	

	public final IntegerProperty selectedTituloProperty() {
		return this.selectedTitulo;
	}
	

	public final int getSelectedTitulo() {
		return this.selectedTituloProperty().get();
	}
	

	public final void setSelectedTitulo(final int selectedTitulo) {
		this.selectedTituloProperty().set(selectedTitulo);
	}
	
    
    

}
