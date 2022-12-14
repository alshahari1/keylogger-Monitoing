package com.snooper.app.controller;

import com.snooper.app.*;

import javafx.fxml.FXML;

public class MainController extends Controller {
	
	@FXML
	public void initialize() {
		System.out.println("INIT: MainController.java");
	}
	
	@FXML
	private void goPref() {
		KSApplication.switchPref();
	}
	
	@FXML
	private void goAbout() {
		KSApplication.switchAbout();
	}
	
	@FXML
	private void goSnoopLogs() {
		KSApplication.switchSnoopLogs();
	}
        
        // تجربه
        @FXML
	private void goUsers() {
		KSApplication.switchUser();
	}
        
        // تجربه
              @FXML
	private void goswitcahanalyzePopup() {
		KSApplication.switcahanalyzePopup();
	}
        
              // تجربه
              @FXML
	private void goswitcahHelp_and_SupportController() {
		KSApplication.switcahHelp_and_SupportController();
	}
}