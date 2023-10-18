package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Employee;
import model.OrderDetails;
import model.tm.EmployeeTm;
import model.tm.OrderDetailsTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    private String lastCustId;

    private String oderId;

    private double total=0.00;
    List<OrderDetails> list=new ArrayList<>();
    private double discount;
    private double cash;
    private double balance;

    @FXML
    private AnchorPane OrderPage;

    @FXML
    private JFXComboBox<String> boxEmployer;

    @FXML
    private JFXComboBox<String> boxItemCode;

    @FXML
    private JFXComboBox<String> boxSize;

    @FXML
    private JFXComboBox<String> boxType;

    @FXML
    private JFXButton btnAddtoCart;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnRemoveOrder;

    @FXML
    private CheckBox checkboxCard;

    @FXML
    private CheckBox checkboxCash;

    @FXML
    private TreeTableColumn<?, ?> colDeleteBtn;

    @FXML
    private TreeTableColumn<?, ?> colDescription;

    @FXML
    private TreeTableColumn<?, ?> colDiscount;

    @FXML
    private TreeTableColumn<?, ?> colItmId;

    @FXML
    private TreeTableColumn<?, ?> colPrice;

    @FXML
    private TreeTableColumn<?, ?> colQty;

    @FXML
    private TreeTableColumn<?, ?> colSize;

    @FXML
    private TreeTableColumn<?, ?> colType;

    @FXML
    private TreeTableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXTreeTableView<OrderDetailsTm> tblOrderDetais;

    @FXML
    private Label txtBalance;

    @FXML
    private JFXTextField txtCash;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerContact;

    @FXML
    private JFXTextField txtCustomertName;

    @FXML
    private DatePicker txtDate;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private Label txtDiscountTotal;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private Label txtTotal;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void boxItemCodeOnCilckAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(boxItemCode.getValue()!=null){
            String id=boxItemCode.getValue();
            Connection connection= DBConnection.getInstance().getConnetion();
            PreparedStatement pstm2=connection.prepareStatement("SELECT * FROM items WHERE id=?");
            pstm2.setObject(1,id);
            ResultSet resultSet=pstm2.executeQuery();
            while (resultSet.next()){
                txtDescription.setText(resultSet.getString(2));
                //txtQtyOnHand.setText(resultSet.getString(6));
                txtUnitPrice.setText(resultSet.getString(5));
                boxType.setValue(resultSet.getString(3));
                boxSize.setValue(resultSet.getString(7));

            }

        }
    }

    @FXML
    void btnAddtoCartClickOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(txtCustomertName.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Customer Name..!").show();
        }else if(!isValidContact(txtCustomerContact.getText())){
            new Alert(Alert.AlertType.INFORMATION, "Enter Customer Contact..!").show();
        }else if(boxItemCode.getValue()==null){
            new Alert(Alert.AlertType.INFORMATION, "Select Item..!").show();
        }else if(txtQty.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Enter Quantity..!").show();
        }else{
            int qty=Integer.parseInt(txtQty.getText().toString());
            if(txtDiscount.getText().length()!=0) {
                discount = Double.parseDouble(txtDiscount.getText().toString());
            }else{
                discount=0.00;
            }
            total+=Double.parseDouble(txtUnitPrice.getText().toString())*qty-discount;
            txtDiscount.setText(""+discount);
            txtTotal.setText(""+total);

            loadTable();

            boxItemCode.setValue(null);
            boxSize.setValue(null);
            boxType.setValue(null);
            txtUnitPrice.setText(null);
            txtDescription.setText(null);
            txtQty.setText(null);
            //txtQtyOnHand.setText(null);
            txtDiscount.setText("0.00");
        }

    }

    @FXML
    void btnBackOnClickAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) OrderPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Selector.fxml"))));
        stage.show();
    }

    @FXML
    void btnClearClickOnAction(ActionEvent event) throws IOException {
        boxItemCode.setValue(null);
        boxSize.setValue(null);
        boxType.setValue(null);
        txtUnitPrice.setText(null);
        txtDescription.setText(null);
        txtQty.setText(null);
        //txtQtyOnHand.setText(null);
        txtDiscount.setText("0.00");
    }

    @FXML
    void btnPlaceOrderClickOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, JRException {
        if(list.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Add Items to cart..!").show();
        }else if(txtCash.getText().length()==0){
            new Alert(Alert.AlertType.INFORMATION, "Input Cash..!").show();
        }else if(checkboxCard.isSelected()==false && checkboxCash.isSelected()==false){
            new Alert(Alert.AlertType.INFORMATION, "Select Payment Method..!").show();
        }else {
            Connection connection = DBConnection.getInstance().getConnetion();

            PreparedStatement pstm2 = connection.prepareStatement("SELECT * FROM customer ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = pstm2.executeQuery();
            while (resultSet.next()) {
                lastCustId = resultSet.getString(1);
            }

            if(checkCustomer()) {
                String sql2 = "INSERT INTO customer VALUES(?,?,?,?)";
                PreparedStatement pstm3 = connection.prepareStatement(sql2);
                pstm3.setString(1, generateCustomerId());
                pstm3.setString(2, txtCustomertName.getText());
                pstm3.setString(3, txtCustomerContact.getText());
                pstm3.setString(4, txtCustomerAddress.getText());
                pstm3.executeUpdate();
            }

            String sql = "INSERT INTO orders VALUES(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, txtOrderId.getText());
            pstm.setString(2, boxEmployer.getValue().toString());
            pstm.setString(3, txtDate.getValue().toString());
            pstm.setString(4, lastCustId);
            pstm.executeUpdate();


            for (OrderDetails orderDetails : list) {
                String sql3 = "INSERT INTO orderdetails VALUES(?,?,?,?,?,?)";
                PreparedStatement pstm4 = connection.prepareStatement(sql3);
                pstm4.setString(1, orderDetails.getOrderID());
                pstm4.setString(2, orderDetails.getIntemCode());
                pstm4.setString(3, Integer.toString(orderDetails.getQty()));
                pstm4.setString(4, Double.toString(orderDetails.getUnitPrice()));
                pstm4.setString(5, Double.toString(orderDetails.getDiscount()));
                pstm4.setString(6, Double.toString(orderDetails.getPrice()));
                pstm4.executeUpdate();

                PreparedStatement pstm6 = connection.prepareStatement("SELECT qtyOnHand FROM items WHERE id=?");
                pstm6.setString(1, orderDetails.getIntemCode());
                ResultSet resultSet6=pstm6.executeQuery();

                int qty=0;

                while(resultSet6.next()){
                    qty=Integer.parseInt(resultSet6.getString(1));
                }

                String newQty=""+(qty-orderDetails.getQty());

                String sql5 = "UPDATE items SET qtyOnHand= ? WHERE id= ?";
                PreparedStatement pstm5 = connection.prepareStatement(sql5);
                pstm5.setString(1, newQty);
                pstm5.setString(2, orderDetails.getIntemCode());
                pstm5.executeUpdate();


                Stage stage = (Stage) OrderPage.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Order.fxml"))));
                stage.show();
            }

            JasperDesign design = JRXmlLoader.load("Reports/Bill2.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnetion());
            JasperViewer.viewReport(jasperPrint,false);
        }


    }

    @FXML
    void btnRemoveOrderClickOnAction(ActionEvent event) throws IOException {
        Stage stage=(Stage) OrderPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Order.fxml"))));
        stage.show();
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        qtyOnHand();
    }

    public void setComboBoxItems() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnetion();
        PreparedStatement pstm=connection.prepareStatement("SELECT * FROM employee");
        PreparedStatement pstm2=connection.prepareStatement("SELECT * FROM items");
        ResultSet resultSet = pstm.executeQuery();
        ResultSet resultSet2= pstm2.executeQuery();
        while(resultSet.next()) {
            boxEmployer.getItems().add(resultSet.getString(1));
        }
        while(resultSet2.next()) {
            boxItemCode.getItems().add(resultSet2.getString(1));
        }
    }

    public void currentDate(){
        Timeline date = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> txtDate.setValue(LocalDate.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
        ), new KeyFrame(Duration.seconds(1)));
        date.setCycleCount(Animation.INDEFINITE);
        date.play();

    }

    public boolean checkCustomer() throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnetion();
        PreparedStatement pstm2 = connection.prepareStatement("SELECT name FROM customer");
        ResultSet resultSet=pstm2.executeQuery();

        String customer=txtCustomertName.getText();

        while(resultSet.next()){
            String name= resultSet.getString(1);
             if(name.length()!=customer.length()){
                 return false;
             }else{
                 for(int i=0;i<customer.length();i++){
                     if(name.charAt(i)!=customer.charAt(i)){
                         return false;
                     }
                 }
             }
        }
        return true;

    }

    public void clearPage(){
        txtCustomertName.setText(null);
        txtCustomerContact.setText(null);
        txtCustomerAddress.setText(null);
        boxItemCode.setValue(null);
        boxSize.setValue(null);
        boxType.setValue(null);
        txtUnitPrice.setText(null);
        txtDescription.setText(null);
        txtQty.setText(null);
        //txtQtyOnHand.setText(null);
        txtDiscount.setText("0.00");
        checkboxCash=null;
        checkboxCard=null;
    }

    public String generateCustomerId() throws SQLException, ClassNotFoundException {
        String str=""+lastCustId.charAt(1)+lastCustId.charAt(2) +lastCustId.charAt(3) + lastCustId.charAt(4);
        int i=Integer.parseInt(str)+1;
        if(i<10) {
            lastCustId="C000" + i;
        }else if(i<100){
            lastCustId="C00" + i;
        }else if(i<1000){
            lastCustId="C0" + i;
        }else {
            lastCustId="C" + i;
        }
        return lastCustId;
    }

    public void generateOrderId() throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnetion();
        PreparedStatement pstm=connection.prepareStatement("SELECT * FROM orders ORDER BY id DESC LIMIT 1");
        ResultSet resultSet=pstm.executeQuery();
        while(resultSet.next()){
            oderId=resultSet.getString(1);
        }
        String str=""+oderId.charAt(4)+oderId.charAt(5) +oderId.charAt(6) + oderId.charAt(7)+ oderId.charAt(8);
        int i=Integer.parseInt(str)+1;
        if(i<10) {
            oderId="ODR-0000" + i;
        }else if(i<100){
            oderId="ODR-000" + i;
        }else if(i<1000){
            oderId="ODR-00" + i;
        }else if(i<10000){
            oderId="ODR-0" + i;
        }else{
            oderId="ODR-" + i;
        }
        txtOrderId.setText(oderId);
    }

    public void qtyOnHand(){
        if(txtQty.getText().length()!=0){
            int i=Integer.parseInt(txtQty.getText().toString());
            int qtyOnHand=Integer.parseInt(txtQtyOnHand.getText().toString());
            txtQtyOnHand.setText(""+(qtyOnHand-i));
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

    /*public void getBalance(){
        for(int i=1;i>0;i++){
            if(txtCash.getText().length()!=0){
                double cash=Double.parseDouble(txtCash.getText());
                double total=Double.parseDouble(txtTotal.getText());
                txtBalance.setText(""+(cash-total));
            }
        }
    }*/
    public void loadTable() throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetailsTm> tmList= FXCollections.observableArrayList();

        String desc="";
        Connection connection=DBConnection.getInstance().getConnetion();
        PreparedStatement pstm3=connection.prepareStatement("SELECT description FROM items WHERE id=?");
        pstm3.setString(1,boxItemCode.getValue().toString());
        ResultSet resultSet3=pstm3.executeQuery();
        while (resultSet3.next()){
            desc=resultSet3.getString(1);
        }
        //Connection connection=DBConnection.getInstance().getConnetion();
        //PreparedStatement pstm=connection.prepareStatement("SELECT * FROM employee");
        //ResultSet resultSet=pstm.executeQuery();

        //while(resultSet.next()){
            //lastID=resultSet.getString(1);
            int qty=Integer.parseInt(txtQty.getText().toString());
            double discount=-Double.parseDouble(txtDiscount.getText().toString());
            double unitPrice=Double.parseDouble(txtUnitPrice.getText().toString());
            double price=unitPrice*qty-discount;
            list.add(new OrderDetails(
                    txtOrderId.getText().toString(),
                    boxItemCode.getValue().toString(),
                    desc,
                    Integer.parseInt(txtQty.getText().toString()),
                    Double.parseDouble(txtUnitPrice.getText().toString()),
                    boxType.getValue().toString(),
                    boxSize.getValue().toString(),
                    Double.parseDouble(txtDiscount.getText().toString()),
                    price
            ));
        //}
        for(OrderDetails orderDetails:list){
            JFXButton btn =new JFXButton("Delete");
            tmList.add(new OrderDetailsTm(
                    orderDetails.getOrderID(),
                    orderDetails.getIntemCode(),
                    orderDetails.getDescription(),
                    orderDetails.getQty(),
                    orderDetails.getUnitPrice(),
                    orderDetails.getType(),
                    orderDetails.getSize(),
                    orderDetails.getDiscount(),
                    orderDetails.getPrice()
            ));

        }
        TreeItem<OrderDetailsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblOrderDetais.setRoot(treeItem);
        tblOrderDetais.setShowRoot(false);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boxType.getItems().addAll("Gents", "Ladies", "Kids","Other");
        boxSize.getItems().addAll("2XS","XS", "S", "M","L","XL","2XL");

        try {
            colItmId.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
            colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
            colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
            colSize.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
            colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
            colDiscount.setCellValueFactory(new TreeItemPropertyValueFactory<>("discount"));
            colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));

            generateOrderId();
            setComboBoxItems();
            currentDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
