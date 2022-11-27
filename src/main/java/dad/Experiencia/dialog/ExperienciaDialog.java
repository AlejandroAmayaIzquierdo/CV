package dad.Experiencia.dialog;

import java.time.LocalDateTime;

import com.sun.javafx.scene.control.skin.resources.ControlResources;

import dad.Experiencia.Experiencia;
import dad.Formacion.Titulo;
import dad.main.App;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExperienciaDialog extends Dialog<Experiencia> {
	
	private final GridPane grid;
	private final TextField denominacion;
	private final TextField empleador;
	private final DatePicker desde;
	private final DatePicker hasta;
	
	
	public ExperienciaDialog() {
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
        
        this.empleador = new TextField();
        this.empleador.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(denominacion, Priority.ALWAYS);
        GridPane.setFillWidth(denominacion, true);
        
        
        this.desde = new DatePicker();
        
        this.hasta = new DatePicker();
        
        dialogPane.contentTextProperty().addListener(o -> updateGrid());
        
        setTitle(ControlResources.getString("Dialog.confirm.title"));
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        
        updateGrid();
        
        setResultConverter((dialogButton) -> {
        	 ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
        	 return data == ButtonData.OK_DONE ?  getSelectedItem() : null;
        });
	}
	
    public final Experiencia getSelectedItem() {
    	return new Experiencia(desde.getValue().atStartOfDay(),hasta.getValue().atStartOfDay(), denominacion.getText(), empleador.getText());
    }


	private void updateGrid() {
        grid.getChildren().clear();

        
        grid.add(new Label("Denominación:"), 0, 0);
        grid.add(denominacion, 1, 0);
        grid.add(new Label("Empleador:"), 0, 1);
        grid.add(empleador, 1, 1);
        grid.add(new Label("Desde:"), 0, 2);
        grid.add(desde, 1, 2);
        grid.add(new Label("Desde:"), 0, 3);
        grid.add(hasta, 1, 3);
        getDialogPane().setContent(grid);
	}

}
