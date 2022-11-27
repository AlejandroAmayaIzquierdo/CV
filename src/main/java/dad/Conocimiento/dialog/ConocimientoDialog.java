package dad.Conocimiento.dialog;

import java.time.LocalDateTime;
import java.util.Collection;

import com.sun.javafx.scene.control.skin.resources.ControlResources;

import dad.Conocimiento.Conocimiento;
import dad.Conocimiento.Nivel;
import dad.Formacion.Titulo;
import dad.main.App;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ConocimientoDialog extends Dialog<Conocimiento> {
	
	private final GridPane grid;
	private final TextField denominacion;
	private final ComboBox<Nivel> nivelComboBox;
	private final Button clearButton;
	
	
	public ConocimientoDialog(Nivel defaultChoice, Collection<Nivel> choices) {
		final DialogPane dialogPane = getDialogPane();
		
		//Grid
		
        this.grid = new GridPane();
        this.grid.setHgap(10);
        this.grid.setVgap(10);
        this.grid.setMaxWidth(Double.MAX_VALUE);
        this.grid.setAlignment(Pos.CENTER_LEFT);
        
        dialogPane.setPrefWidth(500);
        dialogPane.setPrefHeight(150);
        
        
        //TextFields
        
        this.denominacion = new TextField();
        this.denominacion.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(denominacion, Priority.ALWAYS);
        GridPane.setFillWidth(denominacion, true);
        
        //Combobox
        
        final double MIN_WIDTH = 150;

        nivelComboBox = new ComboBox<Nivel>();
        nivelComboBox.setMinWidth(MIN_WIDTH);
        if (choices != null) {
        	nivelComboBox.getItems().addAll(choices);
        }
        nivelComboBox.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(nivelComboBox, Priority.ALWAYS);
        GridPane.setFillWidth(nivelComboBox, true);
        
        dialogPane.contentTextProperty().addListener(o -> updateGrid());
        
        setTitle(ControlResources.getString("Dialog.confirm.title"));
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        //Button
        
        clearButton = new Button("X");
        
        clearButton.setOnAction(e -> {
        	nivelComboBox.getSelectionModel().clearSelection();
        });
        
        updateGrid();
        
        setResultConverter((dialogButton) -> {
        	 ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
        	 return data == ButtonData.OK_DONE ?  getSelectedItem() : null;
        });
	}
	
    public final Conocimiento getSelectedItem() {
    	return new Conocimiento(denominacion.getText(), nivelComboBox.getValue());
    }


	private void updateGrid() {
        grid.getChildren().clear();

        
        grid.add(new Label("Denominación:"), 0, 0);
        grid.add(denominacion, 1, 0);
        grid.add(new Label("Nivel:"), 0, 1);
        grid.add(nivelComboBox, 1, 1);
        grid.add(clearButton, 2, 1);
        getDialogPane().setContent(grid);
	}

}
