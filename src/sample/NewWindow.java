package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NewWindow {

    @FXML
    private Button stepBackButton;
    @FXML
    void initialize() {
        SetNewScene setNewScene=new SetNewScene();
        setNewScene.getScene(stepBackButton,"/sample/Administrator.fxml");
    }
    }


