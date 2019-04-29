/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

import animatefx.animation.FadeInUp;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideInUp;
import animatefx.animation.ZoomIn;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

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

    //Parent registerPane, addBookPane, issueBookPane, retunBookPane, addMaterialPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Parent registerPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/Register.fxml"));
            Parent addBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/AddBook.fxml"));
            Parent issueBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/IssueBook.fxml"));
            Parent retunBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/ReturnBook.fxml"));
            Parent addMaterialPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/AddMaterial.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void RegisterClick(MouseEvent event) throws IOException {
        Parent registerPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/Register.fxml"));
        borderPane.setCenter(registerPane);
        new SlideInDown(registerPane).play();

    }

    @FXML
    private void addBookClick(MouseEvent event) throws IOException {
        Parent addBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/AddBook.fxml"));
        borderPane.setCenter(addBookPane);
        new SlideInUp(addBookPane).play();
    }

    @FXML
    private void issueBookClick(MouseEvent event) throws IOException {
        Parent issueBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/IssueBook.fxml"));
        borderPane.setCenter(issueBookPane);
        new ZoomIn(issueBookPane).play();
    }

    @FXML
    private void returnBookClick(MouseEvent event) throws IOException {
        Parent retunBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/ReturnBook.fxml"));
        borderPane.setCenter(retunBookPane);
        new SlideInRight(retunBookPane).play();
    }

    @FXML
    private void addMaterialClick(MouseEvent event) throws IOException {
        Parent addMaterialPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/AddMaterial.fxml"));
        borderPane.setCenter(addMaterialPane);
        new FadeInUp(addMaterialPane).play();
    }

    @FXML
    private void viewStatisticClick(MouseEvent event) {
    }

}
