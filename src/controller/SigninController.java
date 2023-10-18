package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SigninController {

    @FXML
    private AnchorPane SignIn;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXPasswordField txtReEnteredPwd;

    @FXML
    private JFXTextField txtUserEmail;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtUserPassword;

    @FXML
    void btnBackOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) SignIn.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Homepage.fxml"))));
        stage.show();
    }

    @FXML
    void btnClearOnClickAction(ActionEvent event) {
        txtUserEmail.setText("");
        txtUserName.setText("");
        txtUserPassword.setText("");
        txtReEnteredPwd.setText("");
    }

    @FXML
    void btnSignInOnClickAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        boolean isValid= isValidSignIn();
        if(isValid) {
            String userName = txtUserName.getText();
            String userEmail = txtUserEmail.getText();
            String passWord = txtUserPassword.getText();

            Connection connection = DBConnection.getInstance().getConnetion();
            String sql = "INSERT INTO users VALUES(?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, userName);
            pstm.setString(2, userEmail);
            pstm.setString(3, passWord);
            pstm.executeUpdate();

            Stage stage=(Stage) SignIn.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Homepage.fxml"))));
            stage.show();
        }else {

        }

    }

    public boolean isValidSignIn(){
        String userName= txtUserName.getText();
        String userEmail= txtUserEmail.getText();
        String passWord= txtUserPassword.getText();
        String re_passWord= txtReEnteredPwd.getText();



        if(userName.length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter UserName..!").show();
            return false;
        }else if(userEmail.length()==0 || !isValidEmail(userEmail)){
            txtUserEmail.setText(null);
            new Alert(Alert.AlertType.INFORMATION, "Enter UserEmail..!").show();
            return false;
        }else if(passWord.length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter the Password..!").show();
            return false;
        }else if(re_passWord.length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Re-Enter the Password..!").show();
            return false;
        }else if(!isValidPassword(passWord,re_passWord)){
            txtReEnteredPwd.setText(null);
            new Alert(Alert.AlertType.INFORMATION, "Re-Enter Password..!").show();
            return false;
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Successfully Signed In..!").show();
            return true;
        }
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

    public boolean isValidEmail(String email){
        if(email.length()<=10){
            return false;
        }else if(email.charAt(email.length()-1)!='m'|| email.charAt(email.length()-2)!='o' || email.charAt(email.length()-3)!='c' || email.charAt(email.length()-4)!='.'|| email.charAt(email.length()-10)!='@'){
            return false;
        }else {
            return true;
        }
    }

}
