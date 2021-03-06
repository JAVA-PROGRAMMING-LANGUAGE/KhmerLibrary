/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary;

import java.io.IOException;
import java.text.ParseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author SRUN VANNARA
 */
public class KhmerLibrary extends Application {

    @Override
    public void start(Stage stage) throws IOException, ParseException {
        Parent root = FXMLLoader.load(getClass().getResource("view/MainForm.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("YCODE LIBRARY");
        stage.setMaximized(true);
        stage.setScene(scene);
        Image img = new Image("khmerlibrary/resource/logo.png");
        stage.getIcons().add(img);
        stage.show();
        //System.out.println(java.sql.Date.valueOf(LocalDate.now()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
