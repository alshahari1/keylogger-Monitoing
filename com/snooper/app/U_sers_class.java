/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snooper.app;

/**
 *
 * @author WORK1
 */
public class U_sers_class {

    public U_sers_class(int idU_ser, String nameU_ser, String infoU_ser, String typeCon, String infoCon, String deviceName, String pathFileLogs, String dataAdd, String dateGetLogs) {
        this.idU_ser = idU_ser;
        this.nameU_ser = nameU_ser;
        this.infoU_ser = infoU_ser;
        this.typeCon = typeCon;
        this.infoCon = infoCon;
        this.deviceName = deviceName;
        this.pathFileLogs = pathFileLogs;
        this.dataAdd = dataAdd;
        this.dateGetLogs = dateGetLogs;
    }

    public int getIdU_ser() {
        return idU_ser;
    }

    public String getNameU_ser() {
        return nameU_ser;
    }

    public String getInfoU_ser() {
        return infoU_ser;
    }

    public String getTypeCon() {
        return typeCon;
    }

    public String getInfoCon() {
        return infoCon;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getPathFileLogs() {
        return pathFileLogs;
    }

    public String getDataAdd() {
        return dataAdd;
    }

    public String getDateGetLogs() {
        return dateGetLogs;
    }

    public void setIdU_ser(int idU_ser) {
        this.idU_ser = idU_ser;
    }

    public void setNameU_ser(String nameU_ser) {
        this.nameU_ser = nameU_ser;
    }

    public void setInfoU_ser(String infoU_ser) {
        this.infoU_ser = infoU_ser;
    }

    public void setTypeCon(String typeCon) {
        this.typeCon = typeCon;
    }

    public void setInfoCon(String infoCon) {
        this.infoCon = infoCon;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setPathFileLogs(String pathFileLogs) {
        this.pathFileLogs = pathFileLogs;
    }

    public void setDataAdd(String dataAdd) {
        this.dataAdd = dataAdd;
    }

    public void setDateGetLogs(String dateGetLogs) {
        this.dateGetLogs = dateGetLogs;
    }

    @Override
    public String toString() {
        return "U_sers_class{" + "idU_ser=" + idU_ser + ", nameU_ser=" + nameU_ser + ", infoU_ser=" + infoU_ser + ", typeCon=" + typeCon + ", infoCon=" + infoCon + ", deviceName=" + deviceName + ", pathFileLogs=" + pathFileLogs + ", dataAdd=" + dataAdd + ", dateGetLogs=" + dateGetLogs + '}';
    }
    
      private int idU_ser;
    private String nameU_ser;
    private String infoU_ser;
    private String typeCon;
    private String infoCon;
    private String deviceName;
    private String pathFileLogs;
    private String dataAdd;
    private String dateGetLogs;
}