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
public class AddMaterialController implements Initializable {

    @FXML
    private VBox vbRegisterMember;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtNum;
    @FXML
    private TextField txtDonate;
    @FXML
    private TextField txtOther;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<?> tblMaterial;
    @FXML
    private TableColumn<?, ?> cId;
    @FXML
    private TableColumn<?, ?> cName;
    @FXML
    private TableColumn<?, ?> cPrice;
    @FXML
    private TableColumn<?, ?> cNum;
    @FXML
    private TableColumn<?, ?> cDonate;
    @FXML
    private TableColumn<?, ?> cOther;

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

}
