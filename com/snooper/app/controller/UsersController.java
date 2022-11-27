/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snooper.app.controller;

import Server.server.Server;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.snooper.app.DB;
import com.snooper.app.KSApplication;
import com.snooper.app.U_sers_class;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author WORK1
 */
public class UsersController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField idU_ser;
    @FXML
    private JFXTextField nameU_ser;
    @FXML
    private JFXTextField infoU_ser;
    @FXML
    private JFXComboBox typeCon;
    @FXML
    private JFXTextField infoCon;
    @FXML
    private JFXTextField deviceName;
    @FXML
    private JFXTextField pathFileLogs;
    @FXML
    private JFXTextField dataAdd;
    @FXML
    private JFXTextField dateGetLogs;

    ///////////
    private JFXTextField countU_sers;
    /////////////
    private TextField SearchU_sers;
    ///////////
    @FXML
    Label doneG;
    @FXML
    TableView Table_G;
    int indexG = -1;

    ///////////
    @FXML
    TableColumn<U_sers_class, Integer> T_idU_ser;
    @FXML
    TableColumn<U_sers_class, String> T_nameU_ser;
    @FXML
    TableColumn<U_sers_class, String> T_infoU_ser;
    @FXML
    TableColumn<U_sers_class, String> T_typeCon;
    @FXML
    TableColumn<U_sers_class, String> T_infoCon;
    @FXML
    TableColumn<U_sers_class, String> T_deviceName;
    @FXML
    TableColumn<U_sers_class, String> T_pathFileLogs;
    @FXML
    TableColumn<U_sers_class, String> T_dataAdd;
    @FXML
    TableColumn<U_sers_class, String> T_dateGetLogs;

    ObservableList<U_sers_class> ListG;

    Connection con;
//////////////////////////////////////////////////////////

    @FXML
    private void goSnoopLogs() {
        KSApplication.switchHome();
    }

    @FXML
    private void goWebControllerController() {
        KSApplication.switcahWebControllerController();
    }

    @FXML
    private void getServerform() {
        Server main = new Server();
        main.getserver();
    }

    ///////////////Mune Muose
    public void entred(Event e) {
        ((Button) e.getSource()).setScaleX(1.1);
        ((Button) e.getSource()).setScaleY(1.1);
        ((Button) e.getSource()).setTextFill(Color.BLUE);
        if (((Button) e.getSource()).getText().equals("خروج")) {
            ((Button) e.getSource()).setTextFill(Color.RED);
        }
    }

    public void exited(Event e) {
        countU_sers.setText(DB.count("idU_ser", "U_sers_DB") + "");

        doneG.setText("...");
        ListG = DB.getU_sers_DB();
        Table_G.setItems(ListG);

        ((Button) e.getSource()).setScaleX(1);
        ((Button) e.getSource()).setScaleY(1);
        ((Button) e.getSource()).setTextFill(Color.BLACK);
        if (((Button) e.getSource()).getText().equals("خروج")) {
            ((Button) e.getSource()).setTextFill(Color.BLACK);
        }
    }

    //////////////////////////////////////////////////////
    public void insert_U_sers() {
        int g1 = Integer.parseInt(idU_ser.getText()); //جلب  رقم التمام اليومي
        String g2 = nameU_ser.getText();
        String g3 = typeCon.getSelectionModel().getSelectedItem().toString();//// 

        String g4 = infoU_ser.getText();
        String g5 = infoCon.getText();

        String g6 = deviceName.getText();
        String g7 = pathFileLogs.getText();
        String g8 = dataAdd.getText();

        String g9 = dateGetLogs.getText();

        if (!DB.insert_U_sers_DB("U_sers_DB", g1, g2, g3, g4, g5, g6, g7, g8, g9)) {

            JOptionPane.showMessageDialog(null, "لم تتم اضافة المستخدم");

        } else {
            doneG.setText("تم الاضافة");
            doneG.setVisible(true);
            Clear();

        }

    }

    ///////////////////
    public void updateU_sers() {
        int g1 = Integer.parseInt(idU_ser.getText()); //
        String g2 = nameU_ser.getText();
        String g3 = typeCon.getSelectionModel().getSelectedItem().toString();//// 

        String g4 = infoU_ser.getText();
        String g5 = infoCon.getText();

        String g6 = deviceName.getText();
        String g7 = pathFileLogs.getText();
        String g8 = dataAdd.getText();

        String g9 = dateGetLogs.getText();

        if (DB.updateU_sers_DB("U_sers_DB", "where idU_ser = " + g1, g1, g2, g3, g4, g5, g6, g7, g8, g9)) {
            U_sers_class set = ListG.set(indexG, new U_sers_class(g1, g2, g3, g4, g5, g6, g7, g8, g9));

            doneG.setText("تم التعديل");
            doneG.setVisible(true);
            Clear();
        }
    }

    ///////////////////
    public void getselectedU_sers() {

        indexG = Table_G.getSelectionModel().getSelectedIndex();

        if (indexG <= -1) {
            return;
        }

        idU_ser.setText(T_idU_ser.getCellData(indexG).toString());
        nameU_ser.setText(T_nameU_ser.getCellData(indexG));
        infoU_ser.setText(T_infoU_ser.getCellData(indexG));
        typeCon.getSelectionModel().select(T_typeCon.getCellData(indexG));

        infoCon.setText(T_infoCon.getCellData(indexG));
        deviceName.setText(T_deviceName.getCellData(indexG));
        pathFileLogs.setText(T_pathFileLogs.getCellData(indexG));
        dataAdd.setText(T_dataAdd.getCellData(indexG));
        dateGetLogs.setText(T_dateGetLogs.getCellData(indexG));

    }

    ///////////////////////////////
    public void SearchU_sers() {
        String Text = SearchU_sers.getText();

//              textArea_display.setText(DB.Search(num)+"");
        ListG = DB.SearchU_sers_DB(Text);
        Table_G.setItems(ListG);
    }

    //////////////////////////////
    public void deleteU_sers() {
        if (indexG == -1) {
            return;
        }

        if (DB.DeleteU_sers_DB("U_sers_DB", "nameU_ser =" + T_nameU_ser.getCellData(indexG))) {
            doneG.setText("تم الحذف");
            doneG.setVisible(true);
//                    M_ALL_malqe.setText(Integer.parseInt(M_ALL_malqe.getText())-1+"");
            ListG.remove(indexG);
            indexG = -1;
            Clear();
        }
    }

    ////////////////////////////////
    public void Clear() {
        idU_ser.setText("00");
        nameU_ser.clear();
        infoU_ser.clear();
        typeCon.getSelectionModel().select(-1);
        infoCon.clear();

        deviceName.clear();
        pathFileLogs.clear();
        dataAdd.clear();
        dateGetLogs.clear();

    }

///////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        T_idU_ser.setCellValueFactory(new PropertyValueFactory<U_sers_class, Integer>("idU_ser"));
        T_nameU_ser.setCellValueFactory(new PropertyValueFactory<U_sers_class, String>("nameU_ser"));
        T_infoU_ser.setCellValueFactory(new PropertyValueFactory<U_sers_class, String>("infoU_ser"));
        T_typeCon.setCellValueFactory(new PropertyValueFactory<U_sers_class, String>("typeCon"));
        T_infoCon.setCellValueFactory(new PropertyValueFactory<U_sers_class, String>("infoCon"));
        T_deviceName.setCellValueFactory(new PropertyValueFactory<U_sers_class, String>("deviceName"));
        T_pathFileLogs.setCellValueFactory(new PropertyValueFactory<U_sers_class, String>("pathFileLogs"));
        T_dataAdd.setCellValueFactory(new PropertyValueFactory<U_sers_class, String>("dataAdd"));
        T_dateGetLogs.setCellValueFactory(new PropertyValueFactory<U_sers_class, String>("dateGetLogs"));

        ListG = DB.getU_sers_DB();
        Table_G.setItems(ListG);

        ObservableList listqesm = FXCollections.observableArrayList("Email", "IP_Network");
        typeCon.setItems(listqesm);
    }

}
