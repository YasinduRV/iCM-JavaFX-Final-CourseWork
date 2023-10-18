package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
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
import javafx.stage.Stage;
import model.Item;
import model.Supplier;
import model.tm.ItemTm;
import model.tm.SupplierTm;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    private String lastID;

    @FXML
    private AnchorPane ItemPage;

    @FXML
    private JFXComboBox<String> boxSize;

    @FXML
    private JFXComboBox<String> boxSupplier;

    @FXML
    private JFXComboBox<String> boxType;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TreeTableColumn<?, ?> colItemId;

    @FXML
    private TreeTableColumn<?, ?> colDeleteBtn;

    @FXML
    private TreeTableColumn<?, ?> colDescription;

    @FXML
    private TreeTableColumn<?, ?> colQtyOnHand;

    @FXML
    private TreeTableColumn<?, ?> colSize;

    @FXML
    private TreeTableColumn<?, ?> colType;

    @FXML
    private TreeTableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXTreeTableView<ItemTm> tblItemDetals;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private TextArea txtItemDetails;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void boxSupplierOnClickAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(boxSupplier.getValue()!=null){
            String id=boxSupplier.getValue();
            Connection connection= DBConnection.getInstance().getConnetion();
            PreparedStatement pstm2=connection.prepareStatement("SELECT * FROM supplier WHERE id=?");
            pstm2.setObject(1,id);
            ResultSet resultSet=pstm2.executeQuery();
            while (resultSet.next()){
                txtSupplierName.setText(resultSet.getString(2));
            }

        }
    }

    @FXML
    void btnBackOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) ItemPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Selector.fxml"))));
        stage.show();
    }

    @FXML
    void btnClearOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) ItemPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Item.fxml"))));
        stage.show();
    }

    @FXML
    void btnSaveOnClickAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if(txtDescription.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Description..!").show();
        }else if(boxType.getValue()==null){
            new Alert(Alert.AlertType.INFORMATION, "Select Type..!").show();
        }else if(boxSupplier.getValue()==null){
            new Alert(Alert.AlertType.INFORMATION, "Select Suppllier..!").show();
        }else if(txtUnitPrice.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter tUnit Price..!").show();
        }else if(boxSize.getValue()==null){
            new Alert(Alert.AlertType.INFORMATION, "Select Size..!").show();
        }else {
            Connection connection = DBConnection.getInstance().getConnetion();
            String sql = "INSERT INTO items VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, txtItemCode.getText());
            pstm.setString(2, txtDescription.getText());
            pstm.setString(3, boxType.getValue());
            pstm.setString(4, boxSupplier.getValue());
            pstm.setString(5, txtUnitPrice.getText());
            pstm.setString(6, txtQtyOnHand.getText());
            pstm.setString(7, boxSize.getValue());
            pstm.setString(8, txtDiscount.getText());
            pstm.executeUpdate();

            Stage stage=(Stage) ItemPage.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Item.fxml"))));
            stage.show();

        }
    }


    public void genarateID(){
        String str=""+lastID.charAt(4)+lastID.charAt(5) +lastID.charAt(6) + lastID.charAt(7)+lastID.charAt(8);
        System.out.println(str);
        int i=Integer.parseInt(str)+1;
        if(i<10) {
            txtItemCode.setText("ITM-0000" + i);
        }else if(i<100){
            txtItemCode.setText("ITM-000" + i);
        }else if(i<1000){
            txtItemCode.setText("ITM-00" + i);
        }else if(i<10000){
            txtItemCode.setText("ITM-0" + i);
        }else {
            txtItemCode.setText("ITM-" + i);
        }
    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTm> tmList= FXCollections.observableArrayList();
        List<Item> list=new ArrayList<>();

        Connection connection=DBConnection.getInstance().getConnetion();
        PreparedStatement pstm=connection.prepareStatement("SELECT * FROM items");
        ResultSet resultSet=pstm.executeQuery();

        while(resultSet.next()){
            lastID=resultSet.getString(1);
            list.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getDouble(8)
            ));
        }
        for(Item item:list){
            JFXButton btn =new JFXButton("Delete");
            tmList.add(new ItemTm(
                    item.getId(),
                    item.getDescription(),
                    item.getType(),
                    item.getSuppId(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getSize()
            ));


        }
        TreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblItemDetals.setRoot(treeItem);
        tblItemDetals.setShowRoot(false);


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boxType.getItems().addAll("Gents", "Ladies", "Kids","Other");
        boxSize.getItems().addAll("2XS","XS", "S", "M","L","XL","2XL");

        try {
            colItemId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            colDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
            colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
            colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("UnitPrice"));
            colSize.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
            colQtyOnHand.setCellValueFactory(new TreeItemPropertyValueFactory<>("qtyOnHand"));

            addItemstoComboBOx();
            loadTable();
            genarateID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public void addItemstoComboBOx() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnetion();
        PreparedStatement pstm=connection.prepareStatement("SELECT * FROM supplier");
        ResultSet resultSet = pstm.executeQuery();
        while(resultSet.next()) {
            boxSupplier.getItems().add(resultSet.getString(1));
        }
    }
}
