package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    @FXML
    private JFXButton btnForgotPwd;

    @FXML
    private JFXButton btnLogIn;

    @FXML
    private CheckBox btnShowPwd;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private AnchorPane homePage;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtPassword2;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    void btnForgotPwdOnClickAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ForgotPwd.fxml"))));
        stage.show();
    }

    @FXML
    void btnLogInOnClickAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String userName=txtUserName.getText();
        String passWord=txtPassword.getText();

        if(userName.length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter UserName..!").show();
        }else if(passWord.length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter the Password..!").show();
        }else {
            Connection connection = DBConnection.getInstance().getConnetion();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM USERS");
            ResultSet resultSet = pstm.executeQuery();

            for (int i = 0; ; i++) {
                if (resultSet.next()) {
                    if (checkUserName(resultSet.getString(1)) && checkPassword(resultSet.getString(3))) {
                        System.out.println("Success");
                        Stage stage = (Stage) homePage.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Selector.fxml"))));
                        stage.show();
                        break;
                    } else {
                        System.out.println("Failed");
                        new Alert(Alert.AlertType.INFORMATION, "Enter the Correct Password..!").show();
                        txtPassword.setText(null);
                    }
                }
            }
        }
    }

    @FXML
    void btnShowPwdOnClickAction(ActionEvent event) {
        String password = txtPassword.getText();
        txtPassword2.setText(password);

        if(btnShowPwd.isSelected()){
            txtPassword.setVisible(false);
            txtPassword2.setVisible(true);
        }else{
            txtPassword.setVisible(true);
            txtPassword2.setVisible(false);
        }
    }

    @FXML
    void btnSignInClickOnAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) homePage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Signin.fxml"))));
        stage.show();
    }

   public boolean checkUserName(String userName){
        if(userName.length()!=txtUserName.getText().length()){
            return false;
        }else if(userName.length()==txtUserName.getText().length()){
            for(int i=0;i<userName.length();i++){
                if(userName.charAt(i)!=txtUserName.getText().charAt(i)){
                    return false;
                }
            }
            return true;
        }else{
            return true;
        }

   }

    public boolean checkPassword(String passWord){
        if(passWord.length()!=txtPassword.getText().length()){
            return false;
        }else if(passWord.length()==txtPassword.getText().length()){
            for(int i=0;i<passWord.length();i++){
                if(passWord.charAt(i)!=txtPassword.getText().charAt(i)){
                    return false;
                }
            }
            return true;
        }else{
            return true;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPassword2.setVisible(false);
    }
}
