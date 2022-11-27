package com.snooper;

import com.snooper.app.controller.*;
import com.snooper.app.*;

import javafx.beans.property.*;
//https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
//property
//تلعب الخاصية والربط في JavaFX دورًا مهمًا جدًا في تطبيقات MVC.
//الخاصية هي غلاف كائن يجعل الكائن قابلا للملاحظة ولها وظيفة ربط
//الربط هو واجهة تسمح بمزامنة قيمتين  خصائص./لذلك إذا تغيرت قيمة واحدة  ستتغير قيمة الأخرى أيضاًً


import java.io.*;
import java.awt.*;



//Serializable
//https://www.tutorialspoint.com/java/java_serialization.htm
//https://www.tutorialspoint.com/jackson/jackson_object_serialization.htm
//https://www.google.com/search?q=serializable+java+json&ei=rUigYLuCOYuoUufmiegI&oq=serializable+java+and+j&gs_lcp=Cgdnd3Mtd2l6EAMYADIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjoHCAAQRxCwAzoHCAAQsAMQQzoCCAA6BAgAEBM6CAgAEBYQHhATUPtGWOBdYPhqaAFwAngAgAHVAYgBlAuSAQUwLjIuNZgBAKABAaoBB2d3cy13aXrIAQrAAQE&sclient=gws-wiz

public class Pref implements Serializable {// Serializable and json and goda
    //////////////////////////////////////////////كلاس التفضيلات

	
    
    //https://howtodoinjava.com/java/serialization/serialversionuid/
    //https://www.google.com/search?q=private+static+final+long+serialVersionUID+%3D+0x10L%3B&oq=private+static+final+long+serialVersionUID+%3D+0x10L%3B&aqs=chrome..69i57j33i160l2.479j0j7&sourceid=chrome&ie=UTF-8
	private static final long serialVersionUID = 0x10L;
	
	private String nickname;// json >>robertoguazon
	private boolean iconImageAutoSize; // true
	private boolean notifBalloon; //false
	private File stylesheetFile;//"stylesheetFile":{"path":"css\\deep_red.css"}}
	
        //Transit هو تنسيق بيانات ومجموعة من المكتبات لنقل القيم بين التطبيقات المكتوبة بلغات مختلفة.
        //transient >>Non Access Modifiers >> used in serialization >>https://www.geeksforgeeks.org/transient-keyword-java/
        // يتجاهل القيمة الأصلية للمتغير ويحفظ القيمة الافتراضية لنوع البيانات المتغير هذا.
        // يستخدم هذا المتغير لنقل البتات لقيمة المتغير بين جافا وجيسون
        private transient PrefController prefController;
	private transient BooleanProperty iconImageAutoSizeProperty;
	private transient BooleanProperty notifBalloonProperty;
	
	public Pref() {//كونستراكتور
		nickname = System.getProperty("user.name");
		iconImageAutoSize = true;
		notifBalloon = true;
		stylesheetFile = new File(KSApplication.STYLE_FOLDER + "/" + KSApplication.DEFAULT_STYLE);
	}
	
	public void init() { // الاشعارات
            
            //import javafx.beans.property.*;

		iconImageAutoSizeProperty = new SimpleBooleanProperty(iconImageAutoSize); // انشاء ايقونة شريط سطح المكتب
		notifBalloonProperty = new SimpleBooleanProperty(notifBalloon);// انشاء الاشعار
	}
	
	public void setStylesheetFile(File stylesheetFile) {
		this.stylesheetFile = stylesheetFile;
	}
	
	public File getStylesheetFile() {
		return stylesheetFile;
	}
	
	public String getStylesheet() {
		return getStylesheetFile().getPath().replace('\\','/'); //من اجل عدم حدوث مشكلة في مسار الملف//important to replace slash//المهم ليحل محل الخط المائل
	}
	
	public void setController(PrefController prefController) {
		this.prefController = prefController;
		prefController.setPref(this);//بعد استرجاع تمهيد ملف السجلات وسترجاع اعدادات الواجهه بمزامنه جيسن يتم بدء الواجهه وتكوينها
		
		bind();
	}
	
	public BooleanProperty iconImageAutoSizeProperty() {//icon خاصية الحجم التلقائي للصورة
		return iconImageAutoSizeProperty;
	}
	
	public BooleanProperty notifBalloonProperty() {//Balloon اشعار
		return notifBalloonProperty;
	}
	
	public void bind() {
		//remember the order of binding bidirectional as it is important in this program, the start values
		prefController.getIconAutoSizeCheckBox().selectedProperty().bindBidirectional(iconImageAutoSizeProperty);
		prefController.getNotificationBalloonCheckBox().selectedProperty().bindBidirectional(notifBalloonProperty);
		
		//TODO delete
		iconImageAutoSizeProperty.addListener((o,ov,nv) -> {
			System.out.println("icon image: " + nv);
			iconImageAutoSize = nv;
		});
		
		//TODO delete
		notifBalloonProperty.addListener((o,ov,nv) -> {
			System.out.println("notif balloon: " + nv);
			notifBalloon = nv;
		});
	}
	
	public void notif(TrayIcon trayIcon, String title, String message) {
		notif(trayIcon,title,message,TrayIcon.MessageType.NONE);
	}
	
	public void notif(TrayIcon trayIcon, String title, String message, TrayIcon.MessageType messageType) {
		if (notifBalloon) {
			trayIcon.displayMessage(title,message,messageType);
		} else {
			System.out.println("notif: " + title + " - " + message);
		}
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setIconImageAutoSize(boolean b) {
		this.iconImageAutoSize = b;
	}
	
	public void setNotifBalloon(boolean b) {
		this.notifBalloon = b;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public boolean isIconImageAutoSize() {
		return iconImageAutoSize;
	}
	
	public boolean isNotifBalloon() {
		return notifBalloon;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Pref:{\n");
		sb.append("\tnickname: " + nickname + "\n");
		sb.append("\ticonImageAutoSize: " + iconImageAutoSize + "\n");
		sb.append("\tnotifBalloon: " + notifBalloon + "\n");
		sb.append("\tcss: " + stylesheetFile.getName() + "\n");
		sb.append("}");
		return sb.toString();
	}
}