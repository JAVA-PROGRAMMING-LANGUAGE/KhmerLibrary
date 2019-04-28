/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    private TextField txtTitle;
    @FXML
    private TextField txtSubTitle;
    @FXML
    private ComboBox<?> cboCategory;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtPrintYear;
    @FXML
    private TextField txtNumBook;
    @FXML
    private TextField txtOther;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<?> tblBook;
    @FXML
    private TableColumn<?, ?> cId;
    @FXML
    private TableColumn<?, ?> cTitle;
    @FXML
    private TableColumn<?, ?> cSubTitle;
    @FXML
    private TableColumn<?, ?> cCategory;
    @FXML
    private TableColumn<?, ?> cAuthor;
    @FXML
    private TableColumn<?, ?> cPrintYear;
    @FXML
    private TableColumn<?, ?> cBookNum;
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
    private void idTextChange(KeyEvent event) {
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
