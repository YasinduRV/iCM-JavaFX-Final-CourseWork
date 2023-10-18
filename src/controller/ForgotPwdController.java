package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.Session;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ForgotPwdController implements Initializable {

    @FXML
    private AnchorPane ForgotPwd;

    @FXML
    private JFXCheckBox boxShowPwd;

    @FXML
    private JFXButton btnOK;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSendOPT;

    @FXML
    private JFXTextField txtConfirmPassword;

    @FXML
    private JFXTextField txtOPT;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    void boxShowPwdOnClickAction(ActionEvent event) {

    }

    @FXML
    void btnOKOnClickAction(ActionEvent event) {
        if(txtOPT.getText().length()==4){
            txtPassword.setDisable(false);
            txtConfirmPassword.setDisable(false);
            btnSave.setDisable(false);
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Enter OTP..!").show();
        }
    }

    @FXML
    void btnSaveOnClickAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if(txtPassword.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Password..!").show();
        }else if(txtConfirmPassword.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Confirm Password..!").show();
        }else if(!isValidPassword(txtPassword.getText(),txtConfirmPassword.getText())){
            new Alert(Alert.AlertType.INFORMATION, "Re-enter Confirm Password..!").show();
        }else{
            String passWord = txtPassword.getText();

            Connection connection = DBConnection.getInstance().getConnetion();
            String sql = "UPDATE users SET password= ? WHERE username='Yasindu'";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, passWord);
            pstm.executeUpdate();
        }
        Stage stage=(Stage) ForgotPwd.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Homepage.fxml"))));
        stage.show();
    }

    @FXML
    void btnSendOPTOOnClickAction(ActionEvent event) {
        txtOPT.setDisable(false);
        btnOK.setDisable(false);


    }

    public boolean isValidPassword(String passWord, String rePassword){
        int lengthOfPassword = passWord.length();
        int lengthOfReEnteredPassword = rePassword.length();

        if(lengthOfPassword != lengthOfReEnteredPassword){
            return false;
        }else{
            int count = 0;

            for(int i = 0; i<lengthOfPassword; i++){
                if(passWord.charAt(i) == rePassword.charAt(i)){
                    count++;
                }
            }

            return (count == lengthOfPassword) ? true : false;
        }
    }

    public void setVisible(){
        btnOK.setDisable(true);
        btnSave.setDisable(true);
        txtOPT.setDisable(true);
        txtPassword.setDisable(true);
        txtConfirmPassword.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setVisible();
    }
}
