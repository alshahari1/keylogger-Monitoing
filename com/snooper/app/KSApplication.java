package com.snooper.app;

import com.snooper.app.controller.*;
import com.snooper.*;
import com.snooper.tray.*;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.application.*;
import java.net.*;

import javafx.scene.effect.*;
import javafx.scene.paint.*;

public class KSApplication extends Application { // الكلاس الخاص بتهيئة الواجهات يرث من كلاسApplication 
	
	public static final int WIDTH = 820, HEIGHT = 420; // طول وعرض الواجهة متغير من نوع ستاتيك لايمكن تغييره
	public static final String STYLE_FOLDER = "css"; // مجلد التنسيقات
	public static final String DEFAULT_STYLE = "black.css"; // التنسيق المحدد هو الافتراضي
	public static final String STYLE_EXTENSION = ".css"; // صيغة ملف التنسيق
	
	private static Stage primaryStage;// الواجهة الاساسية
	private static Scene mainScene, prefScene, aboutScene, snoopLogsScene, emailPopupScene, analyzePopupScene, userScene, LoginScene, Help_and_SupportScene, WebControllerScene;  //تجربه  emailPopupScene // AnalyzePopup
	
	private static MainController mainController; //كائنات من كلاسات التحكم بالواجهات
	private static PrefController prefController;
	private static AboutController aboutController;
	private static SnoopLogsController snoopLogsController;
        
        //تجربه
	private static EmailPopupController emailPopupController;
	private static UsersController usersController;
        private static AnalyzePopupController analyzePopupController;
        private static Help_and_SupportController help_and_SupportController;
        private static WebControllerController webControllerController;
         ///////////
         
	private static Snooper snooper;
	
	public static MainController getMainController() {
		return mainController;
	}
	
	public static PrefController getPrefController() { // استدعاء الواجهة الاساسية عند الضغط على الشريط
		return prefController;
	}
	
	public static AboutController getAboutController() {
		return aboutController;
	}
	
	public static SnoopLogsController getSnoopLogsController() {
		return snoopLogsController;
	}
        
        // تجربه
        public static EmailPopupController getEmailPopupController() {
		return emailPopupController;
	}

    public static AnalyzePopupController getAnalyzePopupController() {
        return analyzePopupController;
    }   
    
     

    public static Help_and_SupportController getHelp_and_SupportController() {
        return help_and_SupportController;
    }

    public static UsersController getUsersController() {
        return usersController;
    }
        
        
    public static WebControllerController getWebControllerController() {
        return webControllerController;
    }////////

        
	
	public static Stage getStage() {
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
        //الكلمة throws تستخدم لتحديد نوع الإستثناء الذي قد تقوم الدالة برميه في حال كنت تريد تجربة الكود و معالجته في مكان إستدعاء الدالة.
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		//load fxmls
		
		//Parent rootMain = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
		//Parent rootPref = FXMLLoader.load(getClass().getResource("/fxml/Pref.fxml"));
		//Parent rootAbout = FXMLLoader.load(getClass().getResource("/fxml/About.fxml"));
		
		FXMLLoader loader = createLoader("/fxml/Main.fxml");
		Parent rootMain = loader.load();
		mainController = loader.getController();
		
		loader = createLoader("/fxml/Pref.fxml");
		Parent rootPref = loader.load();
		prefController = loader.getController();
		
		loader = createLoader("/fxml/About.fxml");
		Parent rootAbout = loader.load();
		aboutController = loader.getController();
		
		loader = createLoader("/fxml/SnoopLogs.fxml");
		Parent rootSnoopLogs = loader.load();
		snoopLogsController = loader.getController();
                
                //  تجربة استدعاء واجهة الايميل
                loader = createLoader("/fxml/EmailPopup.fxml");
		Parent rootEmailPopups = loader.load();
		emailPopupController = loader.getController();
                
                                //  تجربة استدعاء واجهة التحليل
                loader = createLoader("/fxml/AnalyzePopup.fxml");
		Parent rootanalyzePopup = loader.load();
		analyzePopupController = loader.getController();
                
                 loader = createLoader("/fxml/Users.fxml");
		Parent rootUsers = loader.load();
		usersController = loader.getController();
                

            loader = createLoader("/fxml/Help_and_Support.fxml");
            Parent rootHelp_and_SupportController = loader.load();
            help_and_SupportController = loader.getController();

            loader = createLoader("/fxml/WebController.fxml");
            Parent rootWebControllerController = loader.load();
            webControllerController = loader.getController();

		//make scenes
		mainScene = new Scene(rootMain, WIDTH, HEIGHT);
		prefScene = new Scene(rootPref, WIDTH, HEIGHT);
		aboutScene = new Scene(rootAbout, WIDTH, HEIGHT);
		snoopLogsScene = new Scene(rootSnoopLogs, WIDTH, HEIGHT);
            //تجربه  
                emailPopupScene = new Scene(rootEmailPopups);
                analyzePopupScene = new Scene(rootanalyzePopup);
                userScene = new Scene(rootUsers);
//                LoginScene = new Scene(rootLogin);
                Help_and_SupportScene = new Scene(rootHelp_and_SupportController);
                WebControllerScene = new Scene(rootWebControllerController);

		//stage configs
		primaryStage.initStyle(StageStyle.TRANSPARENT);//TRANSPARENT استايل مكان الواجهات الاساسية يكون شفاف
		primaryStage.getIcons().add(Util.createJavaFXImage("images/snooper.png"));//هنا وضعنا ايقونه للعلبه التي ستظهر في شريط المهام
		primaryStage.setResizable(false);//لا يمكن للمستخدم تغيير حجم الإطار.
		
		//assigne default scene
                //هنا يتم تعيين المشهد الافتراضي وهي واجهةالاولى واجهة الماين
		setScene(mainScene);
		
                //تستخدم للإخفاء فقط عند الضغط على اكس في شريط القائمة
		Platform.setImplicitExit(false); //use to hide only when x is pressed in the menu bar
		
		setSnooper();//هنا يتم استدعاء وضع قيم كونستراكتور الكلاس الاساسي الخاص بالالتقاط 
		loadStyles();
	}
	
	private static void loadStyles() {
		loadStylesheets();
		
		//for the drop shadow
		mainScene.setFill(Color.TRANSPARENT);//يتم جعل كل الواجهات شفافة في حال ضهور الواجهة ال
		prefScene.setFill(Color.TRANSPARENT);
		aboutScene.setFill(Color.TRANSPARENT);
		snoopLogsScene.setFill(Color.TRANSPARENT);
	}
	
	public static void loadStylesheets() {
		String css = snooper.getPref().getStylesheet();
		addStylesheet(mainScene,css);
		addStylesheet(prefScene,css);
		addStylesheet(aboutScene,css);
		addStylesheet(snoopLogsScene,css);
	}
	
	public static void removeStylesheets() {
		mainScene.getStylesheets().clear();
		prefScene.getStylesheets().clear();
		aboutScene.getStylesheets().clear();
		snoopLogsScene.getStylesheets().clear();
	}
	
	public static void addStylesheet(Scene scene, String filepath) {
		scene.getStylesheets().add(filepath);
	}
	
        //سيستخدم لاستدغاء الواجهه الرئيسية عند الضغط على الايقونه التي في شريط المهام
	public void setSnooper() { //دالة استدعاء وو وضع قيم كونستراكتور الكلاس الاساسي الخاص بالالتقاط
		snooper = snooper.getInstance(); // جلب المثيل الحالي لكائن الملتقط للوحة المفاتيح
		snooper.getPref().setController(getPrefController());// المزامنه بين متغيرات العناصر في الملتقط والواجهه
	}
	
        // FXMLLoader loader = createLoader("/fxml/Main.fxml");//filepath
	public static FXMLLoader createLoader(String filepath) {
		URL url = KSApplication.class.getResource(filepath);
		FXMLLoader loader = new FXMLLoader(url);
		return loader;
	}
	
	public static void show() { ////
            
            // Lambda Expressions -> اسلوب لتقليل حجم الكود هنا ممررنا للداله اكثر من قيمة واكثر من امر
		run(() -> primaryStage.show());
	}
	
	public static void hide() {
		run(() -> primaryStage.hide());
	}
	
	public static void run(Runnable runnable) {
		Platform.runLater(runnable);
	}
	
	public static void setScene(Scene scene) {
		if (scene == null) return;
		run(() -> primaryStage.setScene(scene));
	}
	
        
        // دوال التبديل في الواجهات
	public static void switchHome() {
		setScene(mainScene);
	}
	
	public static void switchPref() {
		setScene(prefScene);
	}
	
	public static void switchAbout() {
		setScene(aboutScene);
	}
	
	public static void switchSnoopLogs() {
        setScene(snoopLogsScene);
    }

    // تجربه
    public static void switchUser() {
        setScene(userScene);
    }

    // تجربه
    public static void switcahanalyzePopup() {
        setScene(analyzePopupScene);
    }

    public static void switcahHelp_and_SupportController() {
        setScene(Help_and_SupportScene);
    }

    public static void switcahWebControllerController() {
        setScene(WebControllerScene);
    }
}
