package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Контроллер администрирования
 */
public class AdministratorController {
    /**
     * Кнопка выхода из админ окна
     */
    @FXML
    private Button SignOutButton;


    @FXML
    void initialize(){
        /**
         * Обработчик кнопки выхода
         */
        SignOutButton.setOnAction(event -> {
            SetNewScene setNewScene=new SetNewScene();
            setNewScene.getScene(SignOutButton,"/sample/sample.fxml");
        });
    }

}
