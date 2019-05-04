/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.controller;

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
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import khmerlibrary.DbConnection;

/**
 * FXML Controller class
 *
 * @author SRUN VANNARA
 */
public class viewStatisticController implements Initializable {

    @FXML
    private VBox paneMemberByGender;
    @FXML
    private VBox paneBookByCat;
    @FXML
    private VBox paneBookBorrowing;

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DbConnection.connect();
        loadMemberByGender();
        loadBookByCategory();
        loadBookBorrowing();
    }
    private void loadMemberByGender() {
        String sql = "SELECT gender, COUNT(m_id) FROM tb_member GROUP BY gender";
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                pieChartData.add(new PieChart.Data(rs.getString(1) + "សរុបៈ " + rs.getInt(2) + "នាក់", rs.getInt(2)));
            };
            PieChart chart = new PieChart(pieChartData);
            chart.setLegendSide(Side.BOTTOM);
            chart.setLabelsVisible(false);
            paneMemberByGender.getChildren().add(chart);
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

    private void loadBookByCategory() {
        String sql = "SELECT cat, SUM(qty) FROM tb_book GROUP BY cat";
        int n = 0;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                paneBookByCat.getChildren().add(new Label("  " + rs.getString(1) + " មាន" + rs.getInt(2) + "ក្បាល"));
                n = n + rs.getInt(2);
            }
            paneBookByCat.getChildren().add(new Separator(Orientation.HORIZONTAL));
            paneBookByCat.getChildren().add(new Label("  សរុប " + n + "ក្បាល"));
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

    private void loadBookBorrowing() {
        String sql = "SELECT COUNT(*), (SELECT COUNT(b_id) FROM tb_issue WHERE CURRENT_DATE-issue_date<14) FROM tb_issue ";
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            int n = 0;
            while (rs.next()) {
                n = rs.getInt(1) - rs.getInt(2);
                pieChartData.add(new PieChart.Data("ខ្ចីលើស14ថ្ងៃមានចំនួន " + n + "ក្បាល", n));
                pieChartData.add(new PieChart.Data("ខ្ចីមិនលើស14ថ្ងៃមានចំនួន " + rs.getInt(2) + "ក្បាល", rs.getInt(2)));
            }
            PieChart chart = new PieChart(pieChartData);
            chart.setLegendSide(Side.BOTTOM);
            chart.setLabelsVisible(false);
            paneBookBorrowing.getChildren().add(chart);
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
}
