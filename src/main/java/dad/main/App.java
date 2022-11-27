package dad.main;

import java.io.IOException;

import dad.cv.root.RootController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
	
	public static Stage mainStage;

	@Override
	public void start(Stage stage) throws Exception {
		
		createInstance();
		
		
	}
	
	public static void createInstance() throws IOException {
		Stage myStage = new Stage();
		
		App.mainStage = myStage;
		
		RootController rootController = new RootController();
		
		myStage.getIcons().add(new Image(App.class.getResourceAsStream("/images/cv64x64.png")));
		
		myStage.setOnCloseRequest(e -> {
			ButtonType b = rootController.salirAction(null);
			
			if(b == rootController.buttonTypeCancel) {
				e.consume();
			}
		});
		
		myStage.setTitle("MiCV");
		myStage.setScene(new Scene(rootController.getView()));
		myStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
