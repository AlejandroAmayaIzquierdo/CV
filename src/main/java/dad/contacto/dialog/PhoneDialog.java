package dad.contacto.dialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.sun.javafx.scene.control.skin.resources.ControlResources;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class PhoneDialog<T> extends Dialog<String[]> {
	
	
    private final GridPane grid;
    private final Label  headerlabel;
    private final ComboBox<T> comboBox;
    private final T defaultChoice;
    private final TextField textField;

    public PhoneDialog(T defaultChoice, Collection<T> choices) {
    	super();
    	final DialogPane dialogPane = getDialogPane();
    	
    	//TextField
    	
        this.textField = new TextField();
        this.textField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        GridPane.setFillWidth(textField, true);
        
        textField.textProperty().addListener((o,ov,nv) -> {
            if (!nv.matches("\\d*")) {
                textField.setText(nv.replaceAll("[^\\d]", ""));
            }
            if(nv.length() > 15) {
            	textField.setText(textField.getText().substring(0,textField.getText().length() - 1));
            }
        });
    	
    	//Grid
        
        this.grid = new GridPane();
        this.grid.setHgap(10);
        this.grid.setVgap(10);
        this.grid.setMaxWidth(Double.MAX_VALUE);
        this.grid.setAlignment(Pos.CENTER_LEFT);
        
        //header
        
        headerlabel = new Label(dialogPane.getContentText());
        headerlabel.setMaxWidth(Double.MAX_VALUE);
        headerlabel.setMaxHeight(Double.MAX_VALUE);
        headerlabel.getStyleClass().add("content");
        headerlabel.setWrapText(true);
        headerlabel.setPrefWidth(360);
        headerlabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        headerlabel.textProperty().bind(dialogPane.contentTextProperty());
        
        dialogPane.contentTextProperty().addListener(o -> updateGrid());
        
        setTitle(ControlResources.getString("Dialog.confirm.title"));
        dialogPane.setHeaderText(ControlResources.getString("Dialog.confirm.header"));
        dialogPane.getStyleClass().addAll("text-input-dialog","choice-dialog");
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    	
        final double MIN_WIDTH = 150;

        comboBox = new ComboBox<T>();
        comboBox.setMinWidth(MIN_WIDTH);
        if (choices != null) {
            comboBox.getItems().addAll(choices);
        }
        comboBox.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(comboBox, Priority.ALWAYS);
//        GridPane.setFillWidth(comboBox, true);

        this.defaultChoice = comboBox.getItems().contains(defaultChoice) ? defaultChoice : null;

        if (defaultChoice == null) {
            comboBox.getSelectionModel().selectFirst();
        } else {
            comboBox.getSelectionModel().select(defaultChoice);
        }
        


        updateGrid();

        setResultConverter((dialogButton) -> {
            ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
            return data == ButtonData.OK_DONE ?  getSelectedItem() : null;
        });
    }
    
    public final String[] getSelectedItem() {
    	return new String[] {comboBox.getSelectionModel().getSelectedItem().toString(),textField.getText()};
    }
    
    private void updateGrid() {
        grid.getChildren().clear();

        grid.add(headerlabel, 0, 0);
        grid.add(new Label("Número:"), 1, 0);
        grid.add(textField, 2, 0);
        grid.add(new Label("Tipo:"), 1, 1);
        grid.add(comboBox, 2, 1);
        getDialogPane().setContent(grid);

        Platform.runLater(() -> comboBox.requestFocus());
    }

}
