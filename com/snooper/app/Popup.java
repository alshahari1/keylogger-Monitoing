package com.snooper.app;

import com.snooper.app.controller.*;
import com.snooper.tray.*;
import com.snooper.*;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import javafx.scene.paint.*;
////////////////////////////////////////////////كلاس شريط الخيارات عند الضغط على سجل 
public class Popup {
	
	private Stage stage;
	private Parent root;
	private Controller controller;
	
	public static final String ANALYZE_POPUP_FXML = "/fxml/AnalyzePopup.fxml";
	public static final String EMAIL_POPUP_FXML = "/fxml/EmailPopup.fxml";
	public static final String SENDIP_POPUP_FXML = "/Client/client/Client.form";
	
	public Popup(FXMLLoader loader) {
		try {
			stage = new Stage(StageStyle.TRANSPARENT);//TRANSPARENT شفاف
			root = loader.load();
			controller = loader.getController();
			controller.setStage(stage);
			
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
	
			KSApplication.addStylesheet(scene, Snooper.getInstance().getPref().getStylesheet());
			
			stage.setScene(scene);
			stage.setResizable(false);
			stage.sizeToScene();
			
			stage.initOwner(KSApplication.getStage());
		} catch (Exception e) {
			Util.notif(Snooper.TITLE, "Problem with popup...");
		}
	}
	
	public void showAndWait() {
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}
	
	public void show() {
		stage.setAlwaysOnTop(false);
		stage.initModality(Modality.NONE);
		stage.show();
	}
	
	public void close() {
		stage.close();
	}
	
	public void sendToController(Object object) {
		controller.send(object);
	}
}