package texteditor;

import texteditor.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage myStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/texteditor/view/editor.fxml"));

        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.init(myStage);

        myStage.setTitle("Text Editor");
        myStage.setScene(new Scene(root, 1294, 697));
        myStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
