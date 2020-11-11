package texteditor.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private TextArea textArea;

    @FXML
    private MenuItem cutButton;

    @FXML
    private MenuItem copyButton;

    @FXML
    private MenuItem pasteButton;

    @FXML
    private MenuItem undoButton;

    private Stage stage;

    private final FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser
                .getExtensionFilters()
                .addAll(
                        new FileChooser.ExtensionFilter("Text", "*.txt"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));

        shortCommands();


    }


    public void init(Stage myStage) {
        this.stage = myStage;
    }

    @FXML
    public void exit() {
        if (textArea.getText().isEmpty()) {
            Platform.exit();
            return;
        }

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Exit without saving?",
                ButtonType.YES,
                ButtonType.NO,
                ButtonType.CANCEL
        );

        alert.setTitle("Confirm");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
        if (alert.getResult() == ButtonType.NO) {
            save();
            Platform.exit();
        }
    }

    @FXML
    public void shortCommands(){
        copy();
        cut();
        paste();
        undo();
    }

    public void timeOut(){

    }

    @FXML
    private void save() {
        try {
            fileChooser.setTitle("Save As");
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PrintWriter savedText = new PrintWriter(file);
                BufferedWriter out = new BufferedWriter(savedText);
                out.write(textArea.getText());
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openFile() {
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            textArea.clear();
            readText(file);
        }
    }


    // sets the textArea to the text of the opened file
    private void readText(File file) {
        String text;

        try (BufferedReader buffReader = new BufferedReader(new FileReader(file))) {
            while ((text = buffReader.readLine()) != null) {
                textArea.appendText(text + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO add confirmation window if text editor has text and wasn't saved

    @FXML
    public void newFile() {
        textArea.clear();
    }

    @FXML
    public void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("About");
        alert.setHeaderText("A project just for fun written by abrand.");
        alert.setContentText("github.com/abrandell");
        alert.showAndWait();
    }

    // TODO Add a proper font-size menu && color selection

    @FXML
    public void fontSize(ActionEvent e) {
        String choice = ((CheckMenuItem) e.getSource()).getId();

        switch (choice) {
            case "small":
                textArea.setStyle("-fx-font-size: 14px");
                break;
            case "default":
                textArea.setStyle("-fx-font-size: 22px");
                break;
            case "large":
                textArea.setStyle("-fx-font-size: 30px");
                break;
            default:
                textArea.setStyle("-fx-font-size: 22px");
        }
    }

    @FXML
    public void copy(){
        copyButton.setOnAction(event -> {
            textArea.copy();
        });
    }

    @FXML
    public void cut(){
        cutButton.setOnAction(event -> {
            textArea.cut();
        });
    }

    @FXML
    public void paste(){
        pasteButton.setOnAction(event -> {
            textArea.paste();
        });
    }

    @FXML
    public void undo(){
        undoButton.setOnAction(event -> {
            textArea.undo();
        });
    }
}
