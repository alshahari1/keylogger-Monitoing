///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.snooper.app;
//
//import com.snooper.app.controller.WebControllerController;
//import java.io.IOException;
//
//import javafx.application.Application;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.stage.Stage;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.scene.input.KeyEvent;
//
//public class webmain extends Application {
//	
//	@Override
//	public void start(Stage stage) throws IOException {
//		
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene.fxml"));
//		Parent root = loader.load();	
//		WebControllerController controller = loader.getController();
//		Scene scene = new Scene(root);		
//		
//		//stage.getIcons().add(new Image("icon.png"));
//		//stage.setTitle("Bro web browser");
//		stage.setScene(scene);
//		stage.show();
//	}	
//
//	public static void main(String[] args) {
//		
//		launch(args);
//	}
//}