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
public class RegisterController implements Initializable {

    @FXML
    private VBox vbRegisterMember;
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLatin;
    @FXML
    private ComboBox<?> cboGender;
    @FXML
    private TextField txtBirth;
    @FXML
    private TextField txtVillage;
    @FXML
    private TextField txtCommune;
    @FXML
    private TextField txtDistrict;
    @FXML
    private TextField txtProvince;
    @FXML
    private TextField txtPhone;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<?> tblMember;
    @FXML
    private TableColumn<?, ?> cId;
    @FXML
    private TableColumn<?, ?> cName;
    @FXML
    private TableColumn<?, ?> cLatin;
    @FXML
    private TableColumn<?, ?> cGender;
    @FXML
    private TableColumn<?, ?> cBirth;
    @FXML
    private TableColumn<?, ?> cVillage;
    @FXML
    private TableColumn<?, ?> cCommune;
    @FXML
    private TableColumn<?, ?> cDistrict;
    @FXML
    private TableColumn<?, ?> cProvince;
    @FXML
    private TableColumn<?, ?> cPhone;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickSave(MouseEvent event) {
    }

    @FXML
    private void clickNew(MouseEvent event) {
    }

    @FXML
    private void clickDelete(MouseEvent event) {
    }

    @FXML
    private void clickClearSearch(MouseEvent event) {
    }

    @FXML
    private void clickSearch(MouseEvent event) {
    }

}
