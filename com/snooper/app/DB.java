/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snooper.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.JOptionPane;

public class DB {

    public static Connection connent() {
        Connection con = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection("jdbc:ucanaccess://Database2.accdb");
            JOptionPane.showMessageDialog(null, "Done");

        } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null,e.getMessage());
            JOptionPane.showMessageDialog(null, "لا يوجد اتصال بقاعدة البيانات");

        }
        return con;
    }

//    public static void main(String[] args) {
//        connent();
//    }
    public static boolean insert_U_sers_DB(String table, int idU_ser, String nameU_ser, String infoU_ser, String typeCon, String infoCon, String deviceName, String pathFileLogs, String dataAdd, String dateGetLogs) {

        Connection con = connent();

        try {

//             PreparedStatement ps =con.prepareStatement("INSERT INTO "+table+" VALUES('"+unique_number+"','"+title+"','"+cost+"','"+authat+"','"+type_fiction+"','"+type_non_fiction+"')");
            PreparedStatement ps = con.prepareStatement("INSERT INTO " + table + " (idU_ser, nameU_ser, infoU_ser, typeCon, infoCon, deviceName, pathFileLogs, dataAdd, dateGetLogs) VALUES  ('" + idU_ser + "','" + nameU_ser + "','" + infoU_ser + "','" + typeCon + "','" + infoCon + "','" + deviceName + "','" + pathFileLogs + "',SYSDATE,'" + dateGetLogs + "')");
            ps.execute();

        } catch (Exception e) {
            if (table.equals("U_sers_DB")) {
                JOptionPane.showMessageDialog(null, e.getMessage());

            } else {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        return true;
    }

    public static ObservableList<U_sers_class> getU_sers_DB() {
        Connection con = connent();
        ObservableList<U_sers_class> list = FXCollections.observableArrayList();

        try {
//                   PreparedStatement ps =con.prepareStatement("select * from Mtabah");

            PreparedStatement ps = con.prepareStatement("select * from U_sers_DB ");
//         PreparedStatement ps =con.prepareStatement("select * from Mtabah ORDER BY num_M");
//DESC  ASC
//
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //     list.add(new Mtabah());
                list.add(new U_sers_class(Integer.parseInt(rs.getString("idU_ser")), rs.getString("nameU_ser"), rs.getString("infoU_ser"), rs.getString("typeCon"), rs.getString("infoCon"), rs.getString("deviceName"), rs.getString("pathFileLogs"), rs.getString("dataAdd"), rs.getString("dateGetLogs")));

            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        return list;
    }

    public static boolean DeleteU_sers_DB(String table, String wh) {
        Connection con = connent();

        try {

            PreparedStatement ps = con.prepareStatement("Delete from " + table + " where " + wh);
            return !ps.execute();

        } catch (Exception e) {
            if (table.equals("U_sers_DB")) {
                JOptionPane.showMessageDialog(null, e.getMessage());

            } else {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        return false;
    }

    public static int count(String col, String table) {

        Connection con = connent();

        try {

            PreparedStatement ps = con.prepareStatement("select count(" + col + ") from " + table);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString(1));
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        return 0;
    }

       public static boolean updateU_sers_DB(String table,String Where ,int idU_ser,String nameU_ser,String infoU_ser,String typeCon,String infoCon,String deviceName,String pathFileLogs,String dataAdd,String dateGetLogs){
  
     Connection con =connent();
     String Sql = null ;
     if(table.equals("U_sers_DB")){
//         Sql = "update "+table+" set idU_ser ='"+idU_ser+"', nameU_ser = '"+nameU_ser+"', infoU_ser ='"+infoU_ser+"', typeCon ='"+typeCon+"', infoCon ='"+infoCon+"', deviceName ='"+deviceName+"', pathFileLogs ='"+pathFileLogs+"', dataAdd ='"+dataAdd+"', dateGetLogs ='"+dateGetLogs+"' "+Where;
         Sql = "update "+table+" set idU_ser ='"+idU_ser+"', nameU_ser = '"+nameU_ser+"', infoU_ser ='"+infoU_ser+"', typeCon ='"+typeCon+"', infoCon ='"+infoCon+"', deviceName ='"+deviceName+"', pathFileLogs ='"+pathFileLogs+"', dataAdd ='"+dataAdd+"', dateGetLogs ='"+dateGetLogs+"' "+Where;
     }
       
       try {
          
//             PreparedStatement ps =con.prepareStatement("INSERT INTO "+table+" VALUES('"+unique_number+"','"+title+"','"+cost+"','"+authat+"','"+type_fiction+"','"+type_non_fiction+"')");
         PreparedStatement ps =con.prepareStatement(Sql);
            return !ps.execute();
           
           }catch(Exception e){    
             if(table.equals("U_sers_DB")){
                             JOptionPane.showMessageDialog(null,e.getMessage());
    
               }else{                      
                                  JOptionPane.showMessageDialog(null,e.getMessage());
}
           }finally{
              try {
                  con.close();
                  }catch(Exception e){
                  }
                  }
    
     return true;
 } 
       
    public static ObservableList<U_sers_class> SearchU_sers_DB(String ST) {
        Connection con = connent();
        ObservableList<U_sers_class> list = FXCollections.observableArrayList();

        try {

            PreparedStatement ps = con.prepareStatement("select idU_ser,nameU_ser,infoU_ser,typeCon,infoCon,deviceName,pathFileLogs,dataAdd,dateGetLogs from U_sers_DB WHERE nameU_ser LIKE '%" + ST + "%'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new U_sers_class(Integer.parseInt(rs.getString("idU_ser")), rs.getString("nameU_ser"), rs.getString("infoU_ser"), rs.getString("typeCon"), rs.getString("infoCon"), rs.getString("deviceName"), rs.getString("pathFileLogs"), rs.getString("dataAdd"), rs.getString("dateGetLogs")));
            }

        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        return list;
    }

    
}
