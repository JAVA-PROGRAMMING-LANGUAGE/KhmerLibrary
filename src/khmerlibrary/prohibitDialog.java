/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import khmerlibrary.controller.MainController;

/**
 *
 * @author SRUN VANNARA
 */
public class prohibitDialog {

    public void show(String head, String body) {
        Button close = new Button("បិទកម្មវិធី");
        close.setStyle("-fx-cursor:hand;  -fx-text-fill:red ; -fx-border-color:white; -fx-background-color:white ");
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(MainController.stackPane, content, JFXDialog.DialogTransition.CENTER, false);
        content.setHeading(new Text(head));
        content.setBody(new Text(body));
        content.setStyle("-fx-font-size: 17; -fx-font-family: 'Nokora'");
        content.setActions(close);
        close.setOnAction(e -> {
            Platform.exit();
        });
        dialog.show();
    }

}
