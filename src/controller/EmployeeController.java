package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import model.Employee;
import model.tm.EmployeeTm;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private DatePicker EmployeeDOB;

    @FXML
    private AnchorPane EmployeePage;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TreeTableColumn<?, ?> colAddress;

    @FXML
    private TreeTableColumn<?, ?> colB_ACC;

    @FXML
    private TreeTableColumn<?, ?> colBranch;

    @FXML
    private TreeTableColumn<?, ?> colContact;

    @FXML
    private TreeTableColumn<?, ?> colDOB;

    @FXML
    private TreeTableColumn<?, ?> colEmpId;

    @FXML
    private TreeTableColumn<?, ?> colNIC;

    @FXML
    private TreeTableColumn<?, ?> colName;
    @FXML
    private JFXTreeTableView<EmployeeTm> tblEmployeeDetails;

    @FXML
    private JFXTextField txtB_AccountNO;

    @FXML
    private JFXTextField txtB_Branch;

    @FXML
    private JFXTextField txtEmployeeAddress;

    @FXML
    private JFXTextField txtEmployeeContact;

    @FXML
    private TextArea txtEmployeeDetails;

    @FXML
    private JFXTextField txtEmployeeID;

    @FXML
    private JFXTextField txtEmployeeNIC;

    @FXML
    private JFXTextField txtEmployeeName;

    private String lastID;

    @FXML
    void btnBackOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) EmployeePage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Selector.fxml"))));
        stage.show();
    }

    @FXML
    void btnClearOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) EmployeePage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Employee.fxml"))));
        stage.show();
    }

    @FXML
    void btnSaveOnClickAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(txtEmployeeName.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Employee Name..!").show();
        }else if(txtEmployeeNIC.getText().length()==0||txtEmployeeNIC.getText().length()>12){
            new Alert(Alert.AlertType.INFORMATION, "Enter Employee NIC..!").show();
            txtEmployeeNIC.setText(null);
        }else if(txtEmployeeAddress.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Employee Address..!").show();
            txtEmployeeAddress.setText(null);
        }else if(txtEmployeeContact.getText().length()!=10){
            new Alert(Alert.AlertType.INFORMATION, "Enter Employee Contact..!").show();
            txtEmployeeContact.setText(null);
        }else{
            System.out.println("done");
            String dob = EmployeeDOB.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Connection connection = DBConnection.getInstance().getConnetion();
            String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, txtEmployeeID.getText());
            pstm.setString(2, txtEmployeeName.getText());
            pstm.setString(3, txtEmployeeNIC.getText());
            pstm.setString(4, txtEmployeeAddress.getText());
            pstm.setString(5, dob);
            pstm.setString(6, txtEmployeeContact.getText());
            pstm.setString(7, txtB_AccountNO.getText());
            pstm.setString(8, txtB_Branch.getText());
            pstm.executeUpdate();


        }

    }

    public void genarateID(){
        String str=""+lastID.charAt(4)+lastID.charAt(5) +lastID.charAt(6) + lastID.charAt(7);
        int i=Integer.parseInt(str)+1;
        if(i<10) {
            txtEmployeeID.setText("EMP-000" + i);
        }else if(i<100){
            txtEmployeeID.setText("EMP-00" + i);
        }else if(i<1000){
            txtEmployeeID.setText("EMP-0" + i);
        }else {
            txtEmployeeID.setText("EMP-" + i);
        }
    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList<EmployeeTm> tmList= FXCollections.observableArrayList();
        List<Employee> list=new ArrayList<>();

        Connection connection=DBConnection.getInstance().getConnetion();
        PreparedStatement pstm=connection.prepareStatement("SELECT * FROM employee");
        ResultSet resultSet=pstm.executeQuery();

        while(resultSet.next()){
            lastID=resultSet.getString(1);
            list.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        for(Employee employee:list){
            JFXButton btn =new JFXButton("Delete");
            tmList.add(new EmployeeTm(
                    employee.getId(),
                    employee.getName(),
                    employee.getNic(),
                    employee.getAddress(),
                    employee.getDob(),
                    employee.getContact(),
                    employee.getAcc_no(),
                    employee.getAcc_branch()
            ));


        }
        TreeItem<EmployeeTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblEmployeeDetails.setRoot(treeItem);
        tblEmployeeDetails.setShowRoot(false);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            colEmpId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
            colNIC.setCellValueFactory(new TreeItemPropertyValueFactory<>("nic"));
            colAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
            colContact.setCellValueFactory(new TreeItemPropertyValueFactory<>("contact"));
            colDOB.setCellValueFactory(new TreeItemPropertyValueFactory<>("dob"));
            colB_ACC.setCellValueFactory(new TreeItemPropertyValueFactory<>("acc_no"));
            colBranch.setCellValueFactory(new TreeItemPropertyValueFactory<>("acc_branch"));
            loadTable();
            genarateID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
