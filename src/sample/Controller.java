package sample;
import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import org.usb4java.*;

/**
 * Главный класс-контроллер с основной логикой аутентификации администратора
 * @author Paul Chuk. Free code for you
 * @version 0.0.1
 */

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LoginSignInTextfield;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button SignInButton;

    /**
     * Метод выхода из области администрирования при таймауте
     */
    private Stage stage1=null;

    public void setWindow()
    {
        /**
         * Загрузка основной сцены и выход из админ сцены
         */
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/sample.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        if(stage1!=null){
            System.out.println(stage1);
            stage1.hide();
        }
    }



    /**
     * инициализация компонентов и логика контроллера
     */
    @FXML
    void initialize() {
        int vid=1423;
        int pid=25479;


/**
 * Event обработчик кнопки аутентификации
 */
        SignInButton.setOnAction(event -> {
/**
 * Запрос логина и пароля администратора
 */
            if (LoginSignInTextfield.getText().equals("admin") && PasswordField.getText().equals("123") ){
                System.out.println("success");
/**
 * Поиск нужного UsbKey при помощи загруженной библиотеки Usb4Java
 */
                final Context context = new Context();
                int result = LibUsb.init(context);
                if(result != LibUsb.SUCCESS)
                {
                    throw new LibUsbException("Unable to initialize the usb device",result);
                }
                DeviceList list = new DeviceList();
                result = LibUsb.getDeviceList(context, list);
                if(result < 0 )throw new LibUsbException("Unable to get device list",result);
                try
                {
                    for(Device device : list)
                    {
                        DeviceDescriptor device_descriptor = new DeviceDescriptor();
                        result = LibUsb.getDeviceDescriptor(device, device_descriptor);
                        if(result != LibUsb.SUCCESS)
                            throw new LibUsbException("Unable to get device descriptor : ",result);
                        System.out.println("Product id is : "+device_descriptor.idProduct()+" "
                                +"Vendor id is : "+device_descriptor.idVendor());
                        if(device_descriptor.idProduct()==pid && device_descriptor.idVendor()==vid) {
                            /**
                             * Запуск генератора токенов при условии существования нужного UsbKey
                             */
                            try {
                                Process process = Runtime.getRuntime().exec(
                                        "cmd /c Admin.bat", null, new File("K:\\Admin_jar\\"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            /**
                             * Получаем первый токен
                             */

                            FileReader fileReader = null;
                            try {
                                fileReader = new FileReader("D:\\passlog.txt");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Scanner scanner = new Scanner(fileReader);
                            String strPass = scanner.nextLine();
                            scanner.close();
                            System.out.println(strPass);

                            if (!strPass.equals("NULL")) {
                                System.out.println(strPass);
                                SetNewScene setNewScene = new SetNewScene();

                                stage1=setNewScene.getScene(SignInButton,"/sample/Administrator.fxml");
                                System.out.println(stage1);
                                /**
                                 * выполняем проверку на пустоту
                                 * @see (scanner).hasNextLine()
                                 * Запоминаем токен и зписываем в файл
                                 * @see FileWriter
                                 */
                                FileReader fr= null;
                                try {
                                    fr = new FileReader("D:\\oldpass.txt");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                Scanner sc=new Scanner(fr);
                                if(sc.hasNextLine()==false) {
                                    sc.close();
                                    try {
                                        fr.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    FileWriter fw = null;
                                    try {
                                        fw = new FileWriter("D:\\oldpass.txt");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        fw.write(strPass);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        fw.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        fileReader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    /**
                                     * запускаем Timeline с проверкой существования токена
                                     * @see Checker
                                     * Callback-ом вызываем метод перехода на главное окно при таймауте
                                     * @see setWindow()
                                     */
                                    Checker checker = new Checker();
                                    checker.registerCallBack(this::setWindow);
                                    checker.checker();
                                }
                            }
                        }
                    }
                }
                finally
                {
                    LibUsb.freeDeviceList(list, true);
                }
            }

        });
    }

}


