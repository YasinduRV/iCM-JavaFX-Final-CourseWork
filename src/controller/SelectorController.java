package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ResourceBundle;

public class SelectorController implements Initializable {

    @FXML
    private AnchorPane SelectorPage;

    @FXML
    private JFXButton btnEmployees;

    @FXML
    private JFXButton btnItems;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnOrderDetails;

    @FXML
    private JFXButton btnSalesReprt;

    @FXML
    private JFXButton btnSupllliers;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private PieChart pie_chart;

    public void currentDateAndTime(){
        Timeline date = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        ), new KeyFrame(Duration.seconds(1)));
        date.setCycleCount(Animation.INDEFINITE);
        date.play();

        Timeline time = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent ->lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }



    @FXML
    void btnEmployeesOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) SelectorPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Employee.fxml"))));
        stage.show();
    }

    @FXML
    void btnItemsOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) SelectorPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Item.fxml"))));
        stage.show();
    }

    @FXML
    void btnLogOutOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) SelectorPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"))));
        stage.show();
    }

    @FXML
    void btnOrderDetailsOnClickAction(ActionEvent event) {

    }

    @FXML
    void btnOrderOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) SelectorPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Order.fxml"))));
        stage.show();
    }

    @FXML
    void btnSalesReportOnClickAction(ActionEvent event) {

    }

    @FXML
    void btnSuplliersOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) SelectorPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Supplier.fxml"))));
        stage.show();
    }
    public void getPercentages() throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnetion();
        PreparedStatement pstm=connection.prepareStatement("SELECT type FROM orderdetails");
        ResultSet resultSet=pstm.executeQuery();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentDateAndTime();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Gents",25),
                        new PieChart.Data("Ladies",40),
                        new PieChart.Data("Kids",25),
                        new PieChart.Data("Other",10));

        pie_chart.setData(pieChartData);
        pie_chart.setTitle("Sales Percentage Types");
    }
}
