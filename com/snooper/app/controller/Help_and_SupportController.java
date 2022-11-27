/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snooper.app.controller;

import com.snooper.app.KSApplication;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author WORK1
 */
public class Help_and_SupportController implements Initializable {

       @FXML
	private void goSnoopLogs() {
		KSApplication.switchHome();
	}
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
