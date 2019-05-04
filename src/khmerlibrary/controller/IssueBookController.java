/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import khmerlibrary.DbConnection;
import khmerlibrary.InfoDialog;
import khmerlibrary.model.Book;
import khmerlibrary.model.Member;

/**
 * FXML Controller class
 *
 * @author SRUN VANNARA
 */
public class IssueBookController implements Initializable {

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
    private TextField txtSearchBook;
    @FXML
    private TableView<Book> tblBook;
    @FXML
    private TableColumn<Book, String> cBid;
    @FXML
    private TableColumn<Book, String> cTitle;
    @FXML
    private TableColumn<Book, String> cSubtitle;
    @FXML
    private TableColumn<Book, String> cCategory;
    @FXML
    private TableColumn<Book, String> cAuthor;
    @FXML
    private MaterialIconView iconInfo;
    @FXML
    private Label lblInfo;
    @FXML
    private Button btnIssue;
    @FXML
    private Label lblDate;

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
        getSelectedRowData();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        lblDate.setText("កាលបរិច្ឆេទខ្ចីៈ " + dtf.format(now) + "\n" + "កាលបរិច្ឆេទត្រូវសងៈ " + dtf.format(now.plusDays(14)));
    }

    private void initTable() {
        cMid.setCellValueFactory(new PropertyValueFactory("m_id"));
        cName.setCellValueFactory(new PropertyValueFactory("name"));
        cLatin.setCellValueFactory(new PropertyValueFactory("latin"));
        cGender.setCellValueFactory(new PropertyValueFactory("gender"));
        cPhone.setCellValueFactory(new PropertyValueFactory("phone"));

        cBid.setCellValueFactory(new PropertyValueFactory("b_id"));
        cTitle.setCellValueFactory(new PropertyValueFactory("title"));
        cSubtitle.setCellValueFactory(new PropertyValueFactory("sub_title"));
        cCategory.setCellValueFactory(new PropertyValueFactory("cat"));
        cAuthor.setCellValueFactory(new PropertyValueFactory("author"));
    }

    @FXML
    private void clickSearchMember(MouseEvent event) {
        lblInfo.setVisible(false);
        iconInfo.setVisible(false);
        tblBook.getItems().clear();
        btnIssue.setVisible(false);
        lblDate.setVisible(false);
        tblMember.getItems().clear();
        txtSearchBook.setText("");
        if (!txtSearchMember.getText().isEmpty()) {
            ObservableList<Member> memberList = FXCollections.observableArrayList();
            String sql = "SELECT m_id, name, latin, gender,phone FROM tb_member WHERE name LIKE ?"
                    + "UNION SELECT m_id, name, latin, gender,phone FROM tb_member WHERE latin LIKE ? ORDER BY latin  LIMIT 100";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtSearchMember.getText().trim() + "%");
                pst.setString(2, txtSearchMember.getText().toUpperCase().trim() + "%");
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
    }

    @FXML
    private void clickSearchBook(MouseEvent event) {
        tblBook.getItems().clear();
        lblDate.setVisible(false);
        btnIssue.setVisible(false);
        if (txtSearchBook.getText().trim().isEmpty()) {
            return;
        }
        int row = tblMember.getSelectionModel().getSelectedIndex();
        if (row >= 0) {
            ObservableList<Book> bookList = FXCollections.observableArrayList();
            String sql = "SELECT * FROM tb_book WHERE b_id LIKE ?"
                    + "UNION SELECT * FROM tb_book WHERE title LIKE ? ORDER BY title  LIMIT 50";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtSearchBook.getText().toUpperCase().trim() + "%");
                pst.setString(2, txtSearchBook.getText().toUpperCase().trim() + "%");
                rs = pst.executeQuery();
                while (rs.next()) {
                    bookList.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }
                tblBook.getItems().setAll(bookList);
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
        } else {

            new InfoDialog().show("ស្វែងរកសៀវភៅ", "មិនអាចស្វែងរកសៀវភៅបានឡើយ។\nសូមជ្រើសរើសអ្នកដែលចង់ឱ្យខ្ចីសៀវភៅជាមុនសិន។");
        }
    }
    private void getSelectedRowData() {
        tblMember.setOnMouseClicked(e -> {
            if (tblMember.getSelectionModel().getSelectedItem() != null) {
                lblInfo.setVisible(true);
                iconInfo.setVisible(true);
                tblBook.getSelectionModel().clearSelection();
                lblDate.setVisible(false);
                loadNumBorrow();
            }
        });

        tblBook.setOnMouseClicked(e -> {
            int row = tblBook.getSelectionModel().getSelectedIndex();
            if (row >= 0) {
                btnIssue.setVisible(true);
                lblDate.setVisible(true);
            }

        });
    }

    private void loadNumBorrow() throws NumberFormatException {
        String sql = "SELECT COUNT(*), MAX(CURRENT_DATE-tb_issue.issue_date)  FROM tb_issue WHERE m_id=?";
        btnIssue.setVisible(false);
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, tblMember.getSelectionModel().getSelectedItem().getM_id());
            rs = pst.executeQuery();
            int count = 0;
            int max = 0;
            while (rs.next()) {
                count = rs.getInt(1);
                max = rs.getInt(2);
            }
            if (max >= 14) {
                iconInfo.setStyle("-fx-fill:red");
                lblInfo.setStyle("-fx-text-fill:red");
                lblInfo.setText("បានខ្ចីសៀវភៅ" + max + "ថ្ងៃហើយ ត្រូវសងសៀវភៅសិន។");
                txtSearchBook.setEditable(false);
                txtSearchBook.setText("");
                tblBook.setDisable(true);
                System.out.println(">=14days");
            } else if (count >= 2) {
                iconInfo.setStyle("-fx-fill:red");
                lblInfo.setStyle("-fx-text-fill:red");
                lblInfo.setText("បានខ្ចីសៀវភៅចំនួន" + count + "ក្បាលហើយ មិនអាចឱ្យខ្ចីទៀតទេ។");
                txtSearchBook.setEditable(false);
                txtSearchBook.setText("");
                tblBook.setDisable(true);
                System.out.println("=2books");

            } else {
                iconInfo.setStyle("-fx-fill:#4c787e");
                lblInfo.setStyle("-fx-text-fill:#4c787e");
                lblInfo.setText("បានខ្ចីសៀវភៅចំនួន" + count + "ក្បាល។");
                txtSearchBook.setEditable(true);
                tblBook.setDisable(false);
            }
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
    private void clickIssue(MouseEvent event) {
        Button close = new Button("ទេ");
        Button ok = new Button("បាទ");
        close.setStyle("-fx-cursor:hand ; -fx-font-color:red ; -fx-border-color:white; -fx-background-color:white");
        ok.setStyle("-fx-cursor:hand; -fx-font-color:red ; -fx-border-color:white; -fx-background-color:white ; -fx-text-fill:red");
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(MainController.stackPane, content, JFXDialog.DialogTransition.CENTER, true);
        content.setHeading(new Text("ឱ្យខ្ចីសៀវភៅ"));
        content.setBody(new Text("តើអ្នកពិតជាចង់ឱ្យខ្ចីសៀវភៅនេះមែនទេ?"));
        content.setStyle("-fx-font-size: 15; -fx-font-family: 'Kh System'");
        content.setActions(close, ok);
        close.setOnAction(e -> {
            dialog.close();
        });
        ok.setOnAction(e -> {
            dialog.close();
            issueBook();
        });
        dialog.show();
    }
    private void issueBook() throws NumberFormatException {
        int mId = tblMember.getSelectionModel().getSelectedItem().getM_id();
        String bId = tblBook.getSelectionModel().getSelectedItem().getB_id();
        try {
            String sql = "INSERT INTO tb_issue(m_id, b_id, issue_date) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, mId);
            pst.setString(2, bId);
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            pst.executeUpdate();
            tblMember.getSelectionModel().clearSelection();
            tblBook.getItems().clear();
            lblDate.setVisible(false);
            btnIssue.setVisible(false);
            lblDate.setVisible(false);
            new InfoDialog().show("ឱ្យខ្ចីសៀវភៅ", "បានឱ្យខ្ចីសៀវភៅដោយជោគជ័យ។");
        } catch (SQLException ex) {
            new InfoDialog().show("ឱ្យខ្ចីសៀវភៅ", "សៀវភៅនេះបានខ្ចីរួចហើយ មិនអាចឱ្យខ្ចីម្ដងទៀតទេ។");
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
