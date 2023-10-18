package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.Supplier;
import model.tm.EmployeeTm;
import model.tm.SupplierTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    private String lastID;

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
    private TreeTableColumn<?, ?> colCompany;

    @FXML
    private TreeTableColumn<?, ?> colContact;

    @FXML
    private TreeTableColumn<?, ?> colDelete;

    @FXML
    private TreeTableColumn<?, ?> colName;

    @FXML
    private TreeTableColumn<?, ?> colSuppId;

    @FXML
    private JFXTreeTableView<SupplierTm> tblSupplierDetails;

    @FXML
    private JFXTextField txtB_AccountNO;

    @FXML
    private JFXTextField txtB_Branch;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private TextArea txtEmployeeDetails;

    @FXML
    private JFXTextField txtSupplierAddress;

    @FXML
    private JFXTextField txtSupplierContact;

    @FXML
    private JFXTextField txtSupplierID;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    void btnBackOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) EmployeePage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Selector.fxml"))));
        stage.show();
    }

    @FXML
    void btnClearOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) EmployeePage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Supplier.fxml"))));
        stage.show();
    }

    @FXML
    void btnSaveOnClickAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if(txtSupplierName.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Supplier Name..!").show();
        }else if(txtCompany.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Company..!").show();
        }else if(txtSupplierAddress.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Supplier Address..!").show();
        }else if(!isValidContact(txtSupplierContact.getText())){
            new Alert(Alert.AlertType.INFORMATION, "Enter Supplier Contact..!").show();
            txtSupplierContact.setText(null);
        }else {
            Connection connection = DBConnection.getInstance().getConnetion();
            String sql = "INSERT INTO supplier VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, txtSupplierID.getText());
            pstm.setString(2, txtSupplierName.getText());
            pstm.setString(3, txtCompany.getText());
            pstm.setString(4, txtSupplierAddress.getText());
            pstm.setString(5, txtSupplierContact.getText());
            pstm.setString(6, txtB_AccountNO.getText());
            pstm.setString(7, txtB_Branch.getText());
            pstm.executeUpdate();

            Stage stage=(Stage) EmployeePage.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Supplier.fxml"))));
            stage.show();

        }
    }

    public boolean isValidContact(String contact){
        if(contact.length()!=10){
            return false;
        }else{
            for(int i=0;i<10;i++){
                if(contact.charAt(i)<48 && contact.charAt(i)>57){
                    return false;
                }
            }
        }
        return true;
    }
    public void genarateID(){

        String str=""+lastID.charAt(4)+lastID.charAt(5) +lastID.charAt(6) + lastID.charAt(7);
        int i=Integer.parseInt(str)+1;
        if(i<10) {
            txtSupplierID.setText("SUP-000" + i);
        }else if(i<100){
            txtSupplierID.setText("SUP-00" + i);
        }else if(i<1000){
            txtSupplierID.setText("SUP-0" + i);
        }else {
            txtSupplierID.setText("SUP-" + i);
        }
    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList<SupplierTm> tmList= FXCollections.observableArrayList();
        List<Supplier> list=new ArrayList<>();

        Connection connection=DBConnection.getInstance().getConnetion();
        PreparedStatement pstm=connection.prepareStatement("SELECT * FROM supplier");
        ResultSet resultSet=pstm.executeQuery();

        while(resultSet.next()){
            lastID=resultSet.getString(1);
            list.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        for(Supplier supplier:list){
            JFXButton btn =new JFXButton("Delete");
            tmList.add(new SupplierTm(
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getCompany(),
                    supplier.getAddress(),
                    supplier.getContact(),
                    supplier.getAcc_no(),
                    supplier.getAcc_branch()
            ));


        }
        TreeItem<SupplierTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblSupplierDetails.setRoot(treeItem);
        tblSupplierDetails.setShowRoot(false);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            colSuppId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
            colCompany.setCellValueFactory(new TreeItemPropertyValueFactory<>("company"));
            colAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
            colContact.setCellValueFactory(new TreeItemPropertyValueFactory<>("contact"));
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
