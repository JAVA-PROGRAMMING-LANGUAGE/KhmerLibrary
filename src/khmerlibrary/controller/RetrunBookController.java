/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author SRUN VANNARA
 */
public class RetrunBookController implements Initializable {

    @FXML
    private VBox vbRegisterMember;
    @FXML
    private TextField txtSearchMember;
    @FXML
    private TableView<?> tblMember;
    @FXML
    private TableColumn<?, ?> cMid;
    @FXML
    private TableColumn<?, ?> cName;
    @FXML
    private TableColumn<?, ?> cLatin;
    @FXML
    private TableColumn<?, ?> cGender;
    @FXML
    private TableColumn<?, ?> cPhone;
    @FXML
    private TableView<?> tblBook;
    @FXML
    private TableColumn<?, ?> cBid;
    @FXML
    private TableColumn<?, ?> cTitle;
    @FXML
    private TableColumn<?, ?> cSub;
    @FXML
    private TableColumn<?, ?> cNumDay;
    @FXML
    private Button btnReturn;
    @FXML
    private ComboBox<?> cboCatBorrow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickClear(MouseEvent event) {
    }

    @FXML
    private void clickReturn(MouseEvent event) {
    }

    @FXML
    private void clickSearch(MouseEvent event) {
    }

}
