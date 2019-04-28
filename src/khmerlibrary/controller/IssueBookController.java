/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class IssueBookController implements Initializable {

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
    private TextField txtSearchBook;
    @FXML
    private TableView<?> tblBook;
    @FXML
    private TableColumn<?, ?> cBid;
    @FXML
    private TableColumn<?, ?> cTitle;
    @FXML
    private TableColumn<?, ?> cSubtitle;
    @FXML
    private TableColumn<?, ?> cCategory;
    @FXML
    private TableColumn<?, ?> cAuthor;
    @FXML
    private MaterialIconView iconInfo;
    @FXML
    private Label lblInfo;
    @FXML
    private Button btnIssue;
    @FXML
    private Label lblDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clickSearchMember(MouseEvent event) {
    }

    @FXML
    private void clickSearchBook(MouseEvent event) {
    }

    @FXML
    private void clickIssue(MouseEvent event) {
    }

}
