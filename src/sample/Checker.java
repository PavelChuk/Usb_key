package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Класс проверяющий подлинность токенов
 */
public class Checker {
    /**
     * Интерфейс Callback
     */
        interface Callback{
        void callingBack();
    }

    Callback callback;

    public void registerCallBack(Callback callback){
        this.callback=callback;
    }

    /**
     * Запуск Timeline обработчика
     * @see Timeline
     */
    private Timeline TimelineChecker = new Timeline();
    public void checker(){
        TimelineChecker = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileReader fr1 = null;
                try {
                    fr1 = new FileReader("D:\\passlog.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Scanner scan1 = new Scanner(fr1);
                String str1=scan1.nextLine();
                try {
                    fr1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scan1.close();

                FileReader fr2 = null;
                try {
                    fr2 = new FileReader("D:\\oldpass.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Scanner scan2 = new Scanner(fr2);
                String str2=scan2.nextLine();
                try {
                    fr2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scan2.close();
                FileWriter fileWriter= null;
                try {
                    fileWriter = new FileWriter("D:\\oldpass.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /**
                 * Условие выхода из Checker-а
                 * @see Checker
                 */

                if(str1.equals(str2)) {
                    System.out.println("Str1 equals Str2. Exit Checker");
                    FileWriter fileWriter1=null;
                    try {
                        fileWriter1=new FileWriter("D:\\passlog.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fileWriter1.write("NULL");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fileWriter1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /**
                     * Вызов метода с помощью Callback
                     * @see Callback
                     * Закрытие Timeline
                     * @see Timeline
                     * при выполнении условий
                     */
                    callback.callingBack();
                    TimelineChecker.stop();
                }
                else
                {
                    try {
                        fileWriter.write(str1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Continue Checker process");
                }

            }
        }));
        /**
         * Задание параметров работы Timeline
         * @see Timeline
         * и воспроизведение
         */
        TimelineChecker.setCycleCount(Timeline.INDEFINITE);
        TimelineChecker.play();

    }
}
