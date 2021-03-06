/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import khmerlibrary.DbConnection;
import khmerlibrary.InfoDialog;
import khmerlibrary.animate.SlideInDown;
import khmerlibrary.animate.SlideInUp;
import khmerlibrary.prohibitDialog;

/**
 * FXML Controller class
 *
 * @author SRUN VANNARA
 */
public class MainController implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane borderPane;

    public static StackPane stackPane;

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    Parent registerPane, addBookPane, issueBookPane, retunBookPane, addMaterialPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DbConnection.connect();

        this.stackPane = mainPane;
        login();
    }

    @FXML
    private void RegisterClick(MouseEvent event) throws IOException {
        Parent registerPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/Register.fxml"));
        borderPane.setCenter(registerPane);
        new SlideInDown(registerPane).play();

    }

    @FXML
    private void addBookClick(MouseEvent event) throws IOException {
        Parent addBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/AddBook.fxml"));
        borderPane.setCenter(addBookPane);
        new SlideInUp(addBookPane).play();
    }

    @FXML
    private void issueBookClick(MouseEvent event) throws IOException {
        Parent issueBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/IssueBook.fxml"));
        borderPane.setCenter(issueBookPane);
        new SlideInUp(issueBookPane).play();
    }

    @FXML
    private void returnBookClick(MouseEvent event) throws IOException {
        Parent retunBookPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/ReturnBook.fxml"));
        borderPane.setCenter(retunBookPane);
        new SlideInDown(retunBookPane).play();
    }

    @FXML
    private void addMaterialClick(MouseEvent event) throws IOException {
        Parent addMaterialPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/AddMaterial.fxml"));
        borderPane.setCenter(addMaterialPane);
        new SlideInDown(addMaterialPane).play();
    }

    @FXML
    private void viewStatisticClick(MouseEvent event) throws IOException {
        Parent viewStatistic = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/ViewStatistic.fxml"));
        borderPane.setCenter(viewStatistic);
        new SlideInUp(viewStatistic).play();
    }

    private void viewHelpClick(MouseEvent event) throws IOException {
        Parent help = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/help.fxml"));
        borderPane.setCenter(help);
        new SlideInDown(help).play();
    }

    @FXML
    private void changePwdClick(MouseEvent event) {
        Button close = new Button("បោះបង់");
        Button change = new Button("ប្ដូរ");
        close.setStyle("-fx-cursor:hand ; -fx-border-color:white; -fx-background-color:white; -fx-text-fill:red");
        change.setStyle("-fx-cursor:hand; -fx-border-color:white; -fx-background-color:white ; -fx-text-fill:teal");
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(MainController.stackPane, content, JFXDialog.DialogTransition.TOP, true);

        content.setHeading(new Text("ប្ដូរពាក្យសម្ងាត់"));
        PasswordField txtOldPass = new PasswordField();
        txtOldPass.setPromptText("សូមវាយពាក្យសម្ងាត់បច្ចុប្បន្ន");
        PasswordField txtNewPass = new PasswordField();
        txtNewPass.setPromptText("សូមវាយពាក្យសម្ងាត់ថ្មី");

        VBox vb = new VBox();
        vb.getChildren().setAll(txtOldPass, txtNewPass);

        content.setBody(vb);
        content.setStyle("-fx-font-size: 16; -fx-font-family: 'Nokora'");
        content.setActions(close, change);
        close.setOnAction(e -> {
            dialog.close();
        });
        change.setOnAction(e -> {
            String sql = "SELECT * FROM tb_pwd WHERE pwd=?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtOldPass.getText());
                rs = pst.executeQuery();

                if (!rs.next()) {
                    new InfoDialog().show("ប្ដូរពាក្យសម្ងាត់", "ពាក្យសម្ងាត់បច្ចុប្បន្នមិនត្រឹមត្រូវ។");
                    return;
                } else if (txtNewPass.getText().toCharArray().length < 4) {
                    new InfoDialog().show("ប្ដូរពាក្យសម្ងាត់", "ពាក្យសម្ងាត់ថ្មីត្រូវមានចាប់ពី៤តួឡើងទៅ។");
                    return;
                } else {
                    updatePwd(txtNewPass.getText());
                }
                rs.close();
                pst.close();

            } catch (SQLException ex) {
                new InfoDialog().show("មានបញ្ហា", ex.getMessage());
            }

            dialog.close();
        });
        dialog.show();
    }

    private void updatePwd(String pwd) {
        try {
            String sql = "UPDATE tb_pwd SET pwd=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, pwd);
            pst.executeUpdate();
            new InfoDialog().show("ប្ដូរពាក្យសម្ងាត់", "ពាក្យសម្ងាត់ត្រូវបានប្ដូរ។\nនៅពេលបើកកម្មវិធីលើកក្រោយត្រូវវាយពាក្យសម្ងាត់ថ្មីនេះ។");
            pst.close();
        } catch (SQLException ex) {
            new InfoDialog().show("មានបញ្ហា", ex.getMessage());
        }
    }

    @FXML
    private void lockProgramClick(MouseEvent event) {
        login();
    }

    private void login() {
        Button close = new Button("ចាកចេញពីកម្មវិធី");
        Button login = new Button("ចូលប្រើ");
        close.setStyle("-fx-cursor:hand ; -fx-border-color:white; -fx-background-color:white; -fx-text-fill:red");
        login.setStyle("-fx-cursor:hand; -fx-border-color:white; -fx-background-color:white ; -fx-text-fill:teal");
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(MainController.stackPane, content, JFXDialog.DialogTransition.TOP, false);

        content.setHeading(new Text("ចូលប្រើកម្មវិធី"));
        PasswordField txtPwd = new PasswordField();
        txtPwd.setPromptText("សូមវាយពាក្យសម្ងាត់ដើម្បីចូលប្រើ");
        content.setBody(txtPwd);
        content.setActions(close, login);
        content.setStyle("-fx-font-size: 16; -fx-font-family: 'Nokora'");
        close.setOnAction(e -> {
            Platform.exit();
        });
        login.setOnAction(e -> {
            String sql = "SELECT * FROM tb_pwd WHERE pwd=?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtPwd.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    dialog.close();
                    evaluateDate();
                } else {
                    txtPwd.setPromptText("ពាក្យសម្ងាត់មិនត្រឹមត្រូវ!");
                    txtPwd.setText("");
                }

                rs.close();
                pst.close();
            } catch (SQLException ex) {
                new InfoDialog().show("មានបញ្ហា", ex.getMessage());
            }
        });
        dialog.show();
        try {
            Parent homePane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/Home.fxml"));
            borderPane.setCenter(homePane);
            new SlideInUp(homePane).play();
        } catch (IOException ex) {
            new InfoDialog().show("មានបញ្ហា", ex.getMessage());
        }
    }

    //alert ofter trial
    private void evaluateDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date alertDate = sdf.parse("2019-07-08");
            Date blockDate = sdf.parse("2019-07-10");
            Date currentDate = new Date();
            if (currentDate.compareTo(blockDate) > 0) {
                new prohibitDialog().show("ចំណាំ", "លោកអ្នកមិនអាចប្រើកម្មវិធីនេះទៀតបានឡើយ។\n"
                        + "សូមទាក់ទងទិញកម្មវិធីនេះតាមលេខទូរសព្ទ ០១០ ៨៣២ ០០៥។");
                return;
            }
            if (currentDate.compareTo(alertDate) > 0) {
                new InfoDialog().show("ចំណាំ", "សូមទាក់ទងទិញកម្មវិធីនេះតាមលេខទូរសព្ទ ០១០ ៨៣២ ០០៥។");
            }
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void closeClick(MouseEvent event) {
        Button close = new Button("បិទ");
        close.setStyle("-fx-cursor:hand;  -fx-text-fill:red ; -fx-border-color:white; -fx-background-color:white ");
        Button exit = new Button("ចាកចេញ");
        exit.setStyle("-fx-cursor:hand;  -fx-text-fill:teal ; -fx-border-color:white; -fx-background-color:white ");
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(MainController.stackPane, content, JFXDialog.DialogTransition.CENTER, true);
        content.setHeading(new Text("ចាកចេញពីកម្មវិធី"));
        content.setBody(new Text("តើអ្នកពិតជាចង់ចាកចេញពីកម្មវិធីមែនទេ?"));
        content.setStyle("-fx-font-size: 17; -fx-font-family: 'Nokora'");
        content.setActions(close, exit);
        close.setOnAction(e -> {
            dialog.close();
        });
        exit.setOnAction(e -> {
            Platform.exit();
        });
        dialog.show();
    }

    @FXML
    private void aboutClick(MouseEvent event) {
        try {
            Parent aboutPane = FXMLLoader.load(getClass().getResource("/khmerlibrary/view/About.fxml"));
            borderPane.setCenter(aboutPane);
            new SlideInDown(aboutPane).play();
        } catch (IOException ex) {
            new InfoDialog().show("មានបញ្ហា", ex.getMessage());
        }
    }

}
