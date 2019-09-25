package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс перехода на новую сцену
 */
public class SetNewScene
{
    public Stage getScene(Button button, String s)
    {
        button.getScene().getWindow().hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(s));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        return stage;
    }



}
