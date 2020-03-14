package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class RaportTemaController {

    @FXML
    private TextArea txtArea;

    public void setTxtArea(String text){
        txtArea.setText(text);
        txtArea.setEditable(false);
    }
}
