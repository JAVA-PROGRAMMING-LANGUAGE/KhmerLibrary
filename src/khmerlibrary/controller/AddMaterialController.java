/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import khmerlibrary.DbConnection;
import khmerlibrary.InfoDialog;
import khmerlibrary.model.Material;

/**
 * FXML Controller class
 *
 * @author SRUN VANNARA
 */
public class AddMaterialController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtDonate;
    @FXML
    private TextField txtOther;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<Material> tblMaterial;
    @FXML
    private TableColumn<Material, Integer> cId;
    @FXML
    private TableColumn<Material, String> cName;
    @FXML
    private TableColumn<Material, String> cPrice;
    @FXML
    private TableColumn<Material, String> cNum;
    @FXML
    private TableColumn<Material, String> cDonate;
    @FXML
    private TableColumn<Material, String> cOther;
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DbConnection.connect();
        initTable();
        loadMaterial();
        getSelectedRowData();
    }
    private void initTable() {
        cId.setCellValueFactory(new PropertyValueFactory("id"));
        cName.setCellValueFactory(new PropertyValueFactory("name"));
        cPrice.setCellValueFactory(new PropertyValueFactory("price"));
        cNum.setCellValueFactory(new PropertyValueFactory("qty"));
        cDonate.setCellValueFactory(new PropertyValueFactory("donate"));
        cOther.setCellValueFactory(new PropertyValueFactory("other"));
    }

    private void loadMaterial() {
        String sql = "SELECT * FROM tb_material ORDER BY name";
        ObservableList<Material> materialList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                materialList.add(new Material(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6)));
            }
            tblMaterial.getItems().setAll(materialList);
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void getSelectedRowData() {
        tblMaterial.setOnMouseClicked(e -> {
            if (tblMaterial.getSelectionModel().getSelectedItem() != null) {
                Material material = tblMaterial.getItems().get(tblMaterial.getSelectionModel().getSelectedIndex());
                txtName.setText(material.getName());
                txtPrice.setText(material.getPrice() + "");
                txtQty.setText(material.getQty() + "");
                txtDonate.setText(material.getDonate());
                txtOther.setText(material.getOther());
                btnSave.setText("កែប្រែ");
                btnSave.setVisible(true);
            }
        });
    }

    private Boolean parseNum(String qty, String price) {
        try {
            Integer.parseInt(qty);
            Double.parseDouble(price);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private Boolean isEmptyField() {
        return txtName.getText().trim().equals("") || !parseNum(txtQty.getText().trim(), txtPrice.getText().trim());
    }
    @FXML
    private void clickSave(MouseEvent event) {
        if (!isEmptyField()) {
            if (btnSave.getText().equals("រក្សាទុក")) {
                save();
            } else {
                update();
            }
        } else {
            new InfoDialog().show("រក្សាទុក", "សូមបំពេញឈ្មោះ តម្លៃ និងចំនួនសម្ភារឱ្យបានត្រឹមត្រូវ។");
        }
    }

    private void save() {
        try {
            String sql = "INSERT INTO tb_material(name,price,qty,donate,other) values(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtName.getText().trim().toUpperCase());
            pst.setDouble(2, Double.parseDouble(txtPrice.getText().trim()));
            pst.setInt(3, Integer.parseInt(txtQty.getText()));
            pst.setString(4, txtDonate.getText().trim());
            pst.setString(5, txtOther.getText().trim());
            pst.executeUpdate();
            new InfoDialog().show("រក្សាទុក", "ការរក្សាទុកបានជោគជ័យ។");
            clearInputField();
            loadMaterial();
        } catch (SQLException ex) {
            new InfoDialog().show("ការរក្សាទុកមានបញ្ហា", ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void update() {
        int id = tblMaterial.getSelectionModel().getSelectedItem().getId();
        try {
            String sql = "UPDATE tb_material SET name=?, price=?, qty=?, donate=?, other=? WHERE id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtName.getText().trim().toUpperCase());
            pst.setDouble(2, Double.parseDouble(txtPrice.getText().trim()));
            pst.setInt(3, Integer.parseInt(txtQty.getText()));
            pst.setString(4, txtDonate.getText().trim());
            pst.setString(5, txtOther.getText().trim());
            pst.setInt(6, id);
            pst.executeUpdate();
            new InfoDialog().show("កែប្រែ", "ការកែប្រែបានជោគជ័យ។");
            clearInputField();
            loadMaterial();
        } catch (SQLException ex) {
            new InfoDialog().show("ការកែប្រែមានបញ្ហា", ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @FXML
    private void clickNew(MouseEvent event) {
        clearInputField();
    }
    private void clearInputField() {
        txtName.setText("");
        txtPrice.setText("");
        txtQty.setText("");
        txtDonate.setText("");
        txtOther.setText("");
        btnSave.setText("រក្សាទុក");
        tblMaterial.getSelectionModel().clearSelection();
    }

    @FXML
    private void clickDelete(MouseEvent event) {
        int row = tblMaterial.getSelectionModel().getSelectedIndex();
        if (row < 0) {
            new InfoDialog().show("លុបទិន្នន័យ", "សូមជ្រើសរើសសម្ភារអ្នកដែលចង់លុបពីក្នុងតារាងរួចចុចប៊ូតុងលុប។");
        } else {
            Button close = new Button("ទេ");
            Button ok = new Button("បាទ");
            close.setStyle("-fx-cursor:hand ; -fx-font-color:red ; -fx-border-color:white; -fx-background-color:white");
            ok.setStyle("-fx-cursor:hand; -fx-font-color:red ; -fx-border-color:white; -fx-background-color:white ; -fx-text-fill:red");
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(MainController.stackPane, content, JFXDialog.DialogTransition.CENTER, true);
            content.setHeading(new Text("លុបទិន្នន័យ"));
            content.setBody(new Text("តើអ្នកពិតជាចង់លុបមែនទេ?"));
            content.setStyle("-fx-font-size: 15; -fx-font-family: 'Kh System'");
            content.setActions(close, ok);
            close.setOnAction(e -> {
                dialog.close();
            });
            ok.setOnAction(e -> {
                delete();
                dialog.close();
            });
            dialog.show();
        }
    }

    private void delete() {
        int id = tblMaterial.getSelectionModel().getSelectedItem().getId();
        try {
            String sql = "DELETE FROM tb_material WHERE id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            new InfoDialog().show("លុបទិន្នន័យ", "ការលុបបានជោគជ័យ។");
            clearInputField();
            loadMaterial();
        } catch (SQLException ex) {
            new InfoDialog().show("លុបទិន្នន័យ", ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
