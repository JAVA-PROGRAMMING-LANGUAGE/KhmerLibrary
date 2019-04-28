/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInUp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author SRUN VANNARA
 */
public class MainController implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane borderPane;

    Parent registerPane, addBookPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            registerPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/Register.fxml"));
            addBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/AddBook.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void RegisterClick(MouseEvent event) throws IOException {
        borderPane.setCenter(registerPane);
        new SlideInDown(registerPane).play();

    }

    @FXML
    private void addBookClick(MouseEvent event) {
        borderPane.setCenter(addBookPane);
        new SlideInUp(addBookPane).play();
    }

}
