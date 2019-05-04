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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import khmerlibrary.DbConnection;
import khmerlibrary.InfoDialog;
import khmerlibrary.model.Book;

/**
 * FXML Controller class
 *
 * @author SRUN VANNARA
 */
public class AddBookController implements Initializable {

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
    private ComboBox<String> cboCategory;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtPrintYear;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtOther;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<Book> tblBook;
    @FXML
    private TableColumn<Book, String> cId;
    @FXML
    private TableColumn<Book, String> cTitle;
    @FXML
    private TableColumn<Book, String> cSubTitle;
    @FXML
    private TableColumn<Book, String> cCat;
    @FXML
    private TableColumn<Book, String> cAuthor;
    @FXML
    private TableColumn<Book, String> cPrintYear;
    @FXML
    private TableColumn<Book, Integer> cQty;
    @FXML
    private TableColumn<Book, String> cOther;

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
        loadCategory();
        loadBook();
        getSelectedRowData();
        unfocusId();
    }

    private void initTable() {
        cId.setCellValueFactory(new PropertyValueFactory("b_id"));
        cTitle.setCellValueFactory(new PropertyValueFactory("title"));
        cSubTitle.setCellValueFactory(new PropertyValueFactory("sub_title"));
        cCat.setCellValueFactory(new PropertyValueFactory("cat"));
        cAuthor.setCellValueFactory(new PropertyValueFactory("author"));
        cPrintYear.setCellValueFactory(new PropertyValueFactory("print_year"));
        cQty.setCellValueFactory(new PropertyValueFactory("qty"));
        cOther.setCellValueFactory(new PropertyValueFactory("other"));
    }

    private void loadBook() {
        String sql = "SELECT * FROM tb_book LIMIT 100";
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8)));
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
    private void loadCategory() {
        String sql = "SELECT cat FROM tb_category";
        ObservableList<String> category = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                category.add(rs.getString(1));
            }
            cboCategory.getItems().setAll(category);
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

    private void clearInputField() {
        txtId.setText("");
        txtTitle.setText("");
        txtSubTitle.setText("");
        cboCategory.getSelectionModel().clearSelection();
        txtAuthor.setText("");
        txtPrintYear.setText("");
        txtQty.setText("");
        txtOther.setText("");
        tblBook.getSelectionModel().clearSelection();
        btnSave.setText("រក្សាទុក");
    }

    private void clearInputField2() {
        txtTitle.setText("");
        txtSubTitle.setText("");
        cboCategory.getSelectionModel().clearSelection();
        txtAuthor.setText("");
        txtPrintYear.setText("");
        txtQty.setText("");
        txtOther.setText("");
        txtSearch.setText("");
        tblBook.getSelectionModel().clearSelection();
        btnSave.setText("រក្សាទុក");
    }
    private void getSelectedRowData() {
        tblBook.setOnMouseClicked(e -> {
            if (tblBook.getSelectionModel().getSelectedItem() != null) {
                Book book = tblBook.getItems().get(tblBook.getSelectionModel().getSelectedIndex());
                txtId.setText(book.getB_id());
                txtTitle.setText(book.getTitle());
                txtSubTitle.setText(book.getSub_title());
                cboCategory.getSelectionModel().select(book.getCat());
                txtAuthor.setText(book.getAuthor());
                txtPrintYear.setText(book.getPrint_year());
                txtQty.setText(book.getQty() + "");
                txtOther.setText(book.getOther());
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
            new InfoDialog().show("រក្សាទុក", "ការរក្សាទុកមិនជោគជ័យ។​\nកូដ/ISBN ចំណងជើង ប្រភេទ និងចំនួនសៀវភៅ(ចំនួនគត់)មិនអាចទទេ។");
        }
    }

    private Boolean parseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    private Boolean isEmptyField() {
        return txtId.getText().trim().equals("") || txtId.isFocused() || txtTitle.getText().trim().equals("")
                || cboCategory.getSelectionModel().isEmpty() || !parseInt(txtQty.getText().trim());
    }

    private void save() {
        try {
            String sql = "INSERT INTO tb_book(b_id, title, sub_title, cat, author, print_year, qty, other) values(?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtId.getText().toUpperCase().trim());
            pst.setString(2, txtTitle.getText().trim().toUpperCase());
            pst.setString(3, txtSubTitle.getText().trim());
            pst.setString(4, cboCategory.getSelectionModel().getSelectedItem());
            pst.setString(5, txtAuthor.getText().trim());
            pst.setString(6, txtPrintYear.getText().trim());
            pst.setInt(7, Integer.parseInt(txtQty.getText().trim()));
            pst.setString(8, txtOther.getText().trim());
            pst.executeUpdate();
            new InfoDialog().show("រក្សាទុក", "ការរក្សាទុកបានជោគជ័យ។");
            clearInputField();
            loadBook();
        } catch (SQLException ex) {
            new InfoDialog().show("មានបញ្ហា!", ex.getMessage());
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
            String sql = "UPDATE tb_book SET title=? , sub_title=? , cat=? , author=? , print_year=? , qty=? , other=? WHERE b_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtTitle.getText().trim().toUpperCase());
            pst.setString(2, txtSubTitle.getText().trim());
            pst.setString(3, cboCategory.getSelectionModel().getSelectedItem());
            pst.setString(4, txtAuthor.getText().trim());
            pst.setString(5, txtPrintYear.getText().trim());
            pst.setInt(6, Integer.parseInt(txtQty.getText().trim()));
            pst.setString(7, txtOther.getText().trim());
            pst.setString(8, txtId.getText().toUpperCase().trim());
            pst.executeUpdate();
            new InfoDialog().show("កែប្រែ", "ការកែប្រែបានជោគជ័យ។");
            clearInputField();
            loadBook();
        } catch (SQLException ex) {
            new InfoDialog().show("មានបញ្ហា!", ex.getMessage());
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

    @FXML
    private void clickDelete(MouseEvent event) {
        showDeleteDialog("លុបទិន្នន័យ", "តើអ្នកពិតជាចង់លុបទិន្នន័យនេះមែនទេ?");
    }

    @FXML
    private void clickClearSearch(MouseEvent event) {
        clearInputField();
        loadBook();
    }

    @FXML
    private void clickSearch(MouseEvent event) {
        clearInputField();
        if (!txtSearch.getText().isEmpty()) {
            ObservableList<Book> bookList = FXCollections.observableArrayList();
            String sql = "SELECT * FROM tb_book WHERE b_id LIKE ?"
                    + "UNION SELECT * FROM tb_book WHERE title LIKE ? ORDER BY title  LIMIT 100";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtSearch.getText().trim() + "%");
                pst.setString(2, txtSearch.getText().toUpperCase().trim() + "%");
                rs = pst.executeQuery();
                while (rs.next()) {
                    bookList.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8)));
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
        }
    }

    private void unfocusId() {
        txtId.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                clearInputField2();
                String sql = "SELECT * FROM tb_book WHERE b_id=?";
                ObservableList<Book> book = FXCollections.observableArrayList();
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, txtId.getText().toUpperCase().trim());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        txtTitle.setText(rs.getString(2));
                        txtSubTitle.setText(rs.getString(3));
                        cboCategory.getSelectionModel().select(rs.getString(4));
                        txtAuthor.setText(rs.getString(5));
                        txtPrintYear.setText(rs.getString(6));
                        txtQty.setText(rs.getString(7));
                        txtOther.setText(rs.getString(8));
                        btnSave.setText("កែប្រែ");
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
        });
    }

    private void showDeleteDialog(String head, String body) {
        int row = tblBook.getSelectionModel().getSelectedIndex();
        if (row >= 0) {
            Button close = new Button("បិទ");
            close.setStyle("-fx-cursor:hand;  -fx-text-fill:red ; -fx-border-color:white; -fx-background-color:white ");
            Button delete = new Button("លុប");
            delete.setStyle("-fx-cursor:hand;  -fx-text-fill:teal ; -fx-border-color:white; -fx-background-color:white ");
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(MainController.stackPane, content, JFXDialog.DialogTransition.TOP, true);
            content.setHeading(new Text(head));
            content.setBody(new Text(body));
            content.setStyle("-fx-font-size: 15; -fx-font-family: 'Kh System'");
            content.setActions(close, delete);
            close.setOnAction(e -> {
                dialog.close();
            });
            delete.setOnAction(e -> {
                dialog.close();
                delete();
            });
            dialog.show();
        }
}

    private void delete() {
        try {
            String sql = "DELETE FROM tb_book WHERE b_id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtId.getText().trim());
            pst.executeUpdate();
            new InfoDialog().show("លុបទិន្នន័យ", "ការលុបបានជោគជ័យ។");
            clearInputField();
            loadBook();
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
