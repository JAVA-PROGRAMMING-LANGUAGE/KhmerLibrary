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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import khmerlibrary.DbConnection;
import khmerlibrary.InfoDialog;
import khmerlibrary.model.Member;

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
    private ComboBox<String> cboGender;
    @FXML
    private TextField txtBd;
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
    private TableView<Member> tblMember;
    @FXML
    private TableColumn<Member, Integer> cId;
    @FXML
    private TableColumn<Member, String> cName;
    @FXML
    private TableColumn<Member, String> cLatin;
    @FXML
    private TableColumn<Member, String> cGender;
    @FXML
    private TableColumn<Member, String> cBd;
    @FXML
    private TableColumn<Member, String> cVillage;
    @FXML
    private TableColumn<Member, String> cCommune;
    @FXML
    private TableColumn<Member, String> cDistrict;
    @FXML
    private TableColumn<Member, String> cProvince;
    @FXML
    private TableColumn<Member, String> cPhone;

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;



    private void initTable() {
        cId.setCellValueFactory(new PropertyValueFactory("m_id"));
        cName.setCellValueFactory(new PropertyValueFactory("name"));
        cLatin.setCellValueFactory(new PropertyValueFactory("latin"));
        cGender.setCellValueFactory(new PropertyValueFactory("gender"));
        cBd.setCellValueFactory(new PropertyValueFactory("bd"));
        cVillage.setCellValueFactory(new PropertyValueFactory("village"));
        cCommune.setCellValueFactory(new PropertyValueFactory("commune"));
        cDistrict.setCellValueFactory(new PropertyValueFactory("district"));
        cProvince.setCellValueFactory(new PropertyValueFactory("province"));
        cPhone.setCellValueFactory(new PropertyValueFactory("phone"));
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DbConnection.connect();
        initTable();
        getSelectedRowData();
        loadMember();
        cboGender.getItems().addAll("ប្រុស", "ស្រី");
    }


    private void showDeleteDialog(String head, String body) {
        int row = tblMember.getSelectionModel().getSelectedIndex();
        if (row >= 0) {
            Button close = new Button("បិទ");
            close.setStyle("-fx-cursor:hand;  -fx-text-fill:red ; -fx-border-color:white; -fx-background-color:white ");
            Button delete = new Button("លុប");
            delete.setStyle("-fx-cursor:hand;  -fx-text-fill:teal ; -fx-border-color:white; -fx-background-color:white ");
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(MainController.stackPane, content, JFXDialog.DialogTransition.TOP, true);
            content.setHeading(new Text(head));
            content.setBody(new Text(body));
            content.setStyle("-fx-font-size: 17; -fx-font-family: 'Nokora'");
            content.setActions(close, delete);
            close.setOnAction(e -> {
                dialog.close();
            });
            delete.setOnAction(e -> {
                //code for deleting data here
                dialog.close();
                delete();
            });
            dialog.show();
        }

    }
    private void save() {
        try {
            String sql = "INSERT INTO tb_member(name, latin,gender,bd,village,commune,district,province,phone) values(?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtName.getText().trim());
            pst.setString(2, txtLatin.getText().toUpperCase());
            pst.setString(3, cboGender.getSelectionModel().getSelectedItem());
            pst.setString(4, txtBd.getText().trim());
            pst.setString(5, txtVillage.getText().trim());
            pst.setString(6, txtCommune.getText().trim());
            pst.setString(7, txtDistrict.getText().trim());
            pst.setString(8, txtProvince.getText().trim());
            pst.setString(9, txtPhone.getText().trim());
            pst.executeUpdate();
            clearInputField();
            loadMember();
            new InfoDialog().show("ចុះឈ្មោះ", "ការចុះឈ្មោះបានជោគជ័យ។");
        } catch (SQLException ex) {
            new InfoDialog().show("ចុះឈ្មោះ", ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void update() {
        try {
            String sql = "UPDATE tb_member SET name=?, latin=?,gender=?,bd=?,village=?,commune=?,district=?,province=?,phone=? WHERE m_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtName.getText().trim());
            pst.setString(2, txtLatin.getText().toUpperCase());
            pst.setString(3, cboGender.getSelectionModel().getSelectedItem());
            pst.setString(4, txtBd.getText().trim());
            pst.setString(5, txtVillage.getText().trim());
            pst.setString(6, txtCommune.getText().trim());
            pst.setString(7, txtDistrict.getText().trim());
            pst.setString(8, txtProvince.getText().trim());
            pst.setString(9, txtPhone.getText().trim());
            pst.setInt(10, Integer.parseInt(txtId.getText()));
            pst.executeUpdate();
            clearInputField();
            loadMember();
            new InfoDialog().show("កែប្រែ", "ការកែប្រែបានជោគជ័យ។");
        } catch (SQLException ex) {
            new InfoDialog().show("កែប្រែ", ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    private Boolean isEmptyField() {
        return txtName.getText().trim().equals("") || txtLatin.getText().trim().equals("")
                || cboGender.getSelectionModel().isEmpty()
                || txtBd.getText().trim().equals("") || txtVillage.getText().trim().equals("")
                || txtCommune.getText().trim().equals("") || txtDistrict.getText().trim().equals("")
                || txtProvince.getText().trim().equals("") || txtPhone.getText().trim().equals("");
    }

    private void loadMember() {
        try {
            ObservableList<Member> memberList = FXCollections.observableArrayList();
            String sql = "SELECT * FROM tb_member ORDER BY m_id LIMIT 100";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                memberList.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
            }
            tblMember.getItems().setAll(memberList);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
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
        tblMember.setOnMouseClicked(e -> {
            if (tblMember.getSelectionModel().getSelectedItem() != null) {
                Member member = tblMember.getItems().get(tblMember.getSelectionModel().getSelectedIndex());
                txtId.setText(member.getM_id() + "");
                txtName.setText(member.getName());
                txtLatin.setText(member.getLatin());
                cboGender.getSelectionModel().select(member.getGender());
                txtBd.setText(member.getBd());
                txtVillage.setText(member.getVillage());
                txtCommune.setText(member.getCommune());
                txtDistrict.setText(member.getDistrict());
                txtProvince.setText(member.getProvince());
                txtPhone.setText(member.getPhone());
                btnSave.setText("កែប្រែ");
            }
        });
    }
    @FXML
    private void clickSave(MouseEvent event) {
        if (isEmptyField().equals(false)) {
            if (btnSave.getText().equals("រក្សាទុក")) {
                save();
            } else {
                update();
            }
        } else {
            new InfoDialog().show("រក្សាទុក", "សូមបំពេញទិន្នន័យរាល់ចន្លោះនីមួយៗ។");
        }
    }


    @FXML
    private void clickNew(MouseEvent event) {
        clearInputField();
    }

    @FXML
    private void clickDelete(MouseEvent event) {
        showDeleteDialog("លុបទិន្នន័យ", "តើអ្នកពិតជាចង់លុបទិន្នន័យនេះមែនទេ?");
    }

    @FXML
    private void clickClearSearch(MouseEvent event) {
        clearInputField();
        txtSearch.setText("");
        loadMember();
    }

    @FXML
    private void clickSearch(MouseEvent event) {
        clearInputField();
        if (!txtSearch.getText().isEmpty()) {
            ObservableList<Member> memberList = FXCollections.observableArrayList();
            String sql = "SELECT * FROM tb_member WHERE name LIKE ?"
                    + "UNION SELECT * FROM tb_member WHERE latin LIKE ?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtSearch.getText().trim() + "%");
                pst.setString(2, txtSearch.getText().trim().toUpperCase() + "%");
                rs = pst.executeQuery();
                while (rs.next()) {
                    memberList.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
                }
                tblMember.getItems().setAll(memberList);
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    rs.close();
                    pst.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void clearInputField() {
        txtId.setText("");
        txtName.setText("");
        txtLatin.setText("");
        cboGender.getSelectionModel().clearSelection();
        txtBd.setText("");
        txtVillage.setText("");
        txtCommune.setText("");
        txtDistrict.setText("");
        txtProvince.setText("");
        txtPhone.setText("");
        btnSave.setText("រក្សាទុក");
        tblMember.getSelectionModel().clearSelection();
    }

    private void delete() {
        try {
            String sql = "DELETE FROM tb_member WHERE m_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtId.getText()));
            pst.executeUpdate();
            clearInputField();
            loadMember();
            new InfoDialog().show("លុបទិន្នន័យ", "ទិន្នន័យត្រូវបានលុប។");
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
