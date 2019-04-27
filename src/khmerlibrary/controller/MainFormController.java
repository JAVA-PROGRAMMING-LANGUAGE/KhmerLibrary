/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author SRUN VANNARA
 */
public class MainFormController implements Initializable {

    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void registerClick(MouseEvent event) throws IOException {
//        AnchorPane p1 = new AnchorPane();
//
//        borderPane.setMargin(p1, new javafx.geometry.Insets(-5, 0, 0, 0));
//
//        p1.setStyle("-fx-background-color:red");
//
//        borderPane.setCenter(p1);
        Parent registerMember = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/Register.fxml"));
        borderPane.setCenter(registerMember);

    }

    @FXML
    private void issueBookClick(MouseEvent event) {
        borderPane.setCenter(new Button("Issue Book"));
    }

    @FXML
    private void returBookClick(MouseEvent event) {
        borderPane.setCenter(new Button("Return Book"));
    }

}
