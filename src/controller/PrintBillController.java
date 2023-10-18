package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import model.OrderDetails;
import model.tm.OrderDetailsTm;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrintBillController implements Initializable {

    List<OrderDetails> list=new ArrayList<>();

    @FXML
    private TreeTableColumn<?, ?> colDesc;

    @FXML
    private TreeTableColumn<?, ?> colId;

    @FXML
    private TreeTableColumn<?, ?> colPrice;

    @FXML
    private TreeTableColumn<?, ?> colQty;

    @FXML
    private JFXTreeTableView<OrderDetailsTm> tblOrderDetails;

    public void getOrderDetails() throws SQLException, ClassNotFoundException {
        String orderId = "";
        String empId = "";
        String custId = "";

        ObservableList<OrderDetailsTm> tmList = FXCollections.observableArrayList();

        Connection connection = DBConnection.getInstance().getConnetion();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orders ORDER BY id DESC LIMIT 1");
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            orderId = resultSet.getString(1);
            empId = resultSet.getString(2);
            custId = resultSet.getString(4);
        }

        PreparedStatement pstm2 = connection.prepareStatement("SELECT * FROM orderdetails WHERE order_id=?");
        pstm2.setString(1, orderId);
        ResultSet resultSet1 = pstm2.executeQuery();

        while (resultSet1.next()) {
            String desc="";
            PreparedStatement pstm3=connection.prepareStatement("SELECT description FROM items WHERE id=?");
            pstm3.setString(1,resultSet1.getString(2));
            ResultSet resultSet3=pstm3.executeQuery();
            while (resultSet3.next()){
                desc=resultSet3.getString(1);
            }
            list.add(new OrderDetails(
                    orderId,
                    resultSet1.getString(2),
                    desc,
                    Integer.parseInt(resultSet1.getString(3)),
                    Double.parseDouble(resultSet1.getString(6))
            ));

            for (OrderDetails orderDetails : list) {
                tmList.add(new OrderDetailsTm(
                        orderDetails.getOrderID(),
                        orderDetails.getIntemCode(),
                        orderDetails.getDescription(),
                        orderDetails.getQty(),
                        orderDetails.getPrice()
                ));

                System.out.println(orderDetails.getOrderID()+" "+
                        orderDetails.getIntemCode()+" "+
                        orderDetails.getQty()+" "+
                        orderDetails.getUnitPrice()+" "+
                        orderDetails.getDescription()+" "+
                        orderDetails.getPrice());

            }
            TreeItem<OrderDetailsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblOrderDetails.setRoot(treeItem);
            tblOrderDetails.setShowRoot(false);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
            colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
            colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
            colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
            getOrderDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
