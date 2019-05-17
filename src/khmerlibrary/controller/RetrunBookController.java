/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

import com.jfoenix.controls.JFXCheckBox;
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
import javafx.event.ActionEvent;
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
import khmerlibrary.model.Book;
import khmerlibrary.model.Member;

/**
 * FXML Controller class
 *
 * @author SRUN VANNARA
 */
public class RetrunBookController implements Initializable {

    @FXML
    private TextField txtSearchMember;
    @FXML
    private TableView<Member> tblMember;
    @FXML
    private TableColumn<Member, Integer> cMid;
    @FXML
    private TableColumn<Member, String> cName;
    @FXML
    private TableColumn<Member, String> cLatin;
    @FXML
    private TableColumn<Member, String> cGender;
    @FXML
    private TableColumn<Member, String> cPhone;
    @FXML
    private TableView<Book> tblBook;
    @FXML
    private TableColumn<Book, String> cBid;
    @FXML
    private TableColumn<Book, String> cTitle;
    @FXML
    private TableColumn<Book, String> cSub;
    @FXML
    private TableColumn<Book, Integer> cNumDay;
    @FXML
    private Button btnReturn;

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    @FXML
    private VBox vbRegisterMember;
    @FXML
    private JFXCheckBox checkBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DbConnection.connect();
        initTable();
        loadMemberBorrow();
        getSelectedRowMember();
    }
    private void initTable() {
        cMid.setCellValueFactory(new PropertyValueFactory("m_id"));
        cName.setCellValueFactory(new PropertyValueFactory("name"));
        cLatin.setCellValueFactory(new PropertyValueFactory("latin"));
        cGender.setCellValueFactory(new PropertyValueFactory("gender"));
        cPhone.setCellValueFactory(new PropertyValueFactory("phone"));

        cBid.setCellValueFactory(new PropertyValueFactory("b_id"));
        cTitle.setCellValueFactory(new PropertyValueFactory("title"));
        cSub.setCellValueFactory(new PropertyValueFactory("sub_title"));
        cNumDay.setCellValueFactory(new PropertyValueFactory("num_day"));
    }

    /**
     * Load all members who borrow book.
     */
    private void loadMemberBorrow() {
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT tb_member.m_id,name,latin,gender,phone FROM tb_member JOIN tb_issue ON tb_member.m_id=tb_issue.m_id";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                memberList.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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

    /**
     * Load members who borrowed book more than 14 days.
     */
    private void loadMemberExpire() {
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT tb_member.m_id,name,latin,gender,phone,issue_date FROM tb_member JOIN tb_issue ON tb_member.m_id=tb_issue.m_id WHERE (CURRENT_DATE-issue_date )>14 ORDER BY issue_date ";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                memberList.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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

    private void getSelectedRowMember() {
        tblMember.setOnMouseClicked(e -> {
            int row = tblMember.getSelectionModel().getSelectedIndex();
            btnReturn.setVisible(false);
            if (row >= 0) {
                loadBookBorrow();
            }
        });

        tblBook.setOnMouseClicked(e -> {
            int row = tblBook.getSelectionModel().getSelectedIndex();
            if (row >= 0) {
                btnReturn.setVisible(true);
            }
        });
    }

    private void loadBookBorrow() {
        String sql = "SELECT b.b_id, b.title, b.sub_title,(CURRENT_DATE-i.issue_date) FROM tb_book as b INNER JOIN tb_issue as i ON b.b_id=i.b_id WHERE i.m_id=?";
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, tblMember.getSelectionModel().getSelectedItem().getM_id());
            rs = pst.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
            tblBook.getItems().setAll(bookList);
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
    @FXML
    private void clickClear(MouseEvent event) {
        txtSearchMember.setText("");
        tblBook.getItems().clear();
        btnReturn.setVisible(false);
        loadMemberBorrow();
    }

    @FXML
    private void clickReturn(MouseEvent event) {
        Button close = new Button("ទេ");
        Button yes = new Button("បាទ");
        close.setStyle("-fx-cursor:hand;  -fx-text-fill:red ; -fx-border-color:white; -fx-background-color:white ");
        yes.setStyle("-fx-cursor:hand;  -fx-text-fill:teal ; -fx-border-color:white; -fx-background-color:white ");
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(MainController.stackPane, content, JFXDialog.DialogTransition.CENTER, true);
        content.setHeading(new Text("សងសៀវភៅ"));
        content.setBody(new Text("តើអ្នកពិតជាចង់សងសៀវភៅនេះមែនទេ?"));
        content.setStyle("-fx-font-size: 17; -fx-font-family: 'Nokora'");
        content.setActions(close, yes);
        close.setOnAction(e -> {
            dialog.close();
        });
        yes.setOnAction(e -> {
            dialog.close();
            returnBook();
        });
        dialog.show();
    }

    private void returnBook() {
        try {
            String sql = "DELETE FROM tb_issue WHERE m_id=? AND b_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, tblMember.getSelectionModel().getSelectedItem().getM_id());
            pst.setString(2, tblBook.getSelectionModel().getSelectedItem().getB_id());
            pst.executeUpdate();
            loadBookBorrow();
        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void clickSearch(MouseEvent event) {
        tblBook.getItems().clear();
        if (txtSearchMember.getText().trim().isEmpty()) {
            return;
        }
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT tb_member.m_id,name,latin,gender,phone FROM tb_member  JOIN tb_issue ON tb_member.m_id=tb_issue.m_id WHERE name LIKE ?"
                + "UNION SELECT DISTINCT tb_member.m_id,name,latin,gender,phone FROM tb_member  JOIN tb_issue ON tb_member.m_id=tb_issue.m_id WHERE latin LIKE ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtSearchMember.getText().trim() + "%");
            pst.setString(2, txtSearchMember.getText().trim().toUpperCase() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                memberList.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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

    @FXML
    private void checkBoxClick(ActionEvent event) {
        tblMember.getItems().clear();
        tblBook.getItems().clear();
        btnReturn.setVisible(false);
        if (checkBox.isSelected()) {
            loadMemberExpire();
        } else {
            loadMemberBorrow();
        }

    }

}
