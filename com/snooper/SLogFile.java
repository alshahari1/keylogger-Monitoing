package com.snooper;

import com.snooper.*;
import com.snooper.tray.*;

import java.util.*;
import java.io.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.control.*;

import java.nio.file.*;

public class SLogFile {
	
	private String filepath; //متغير مسار ملف السجل
	private String filename; //متغير اسم ملف السجل
	private String dateString; // متغيير نص تاريخ السجل
	private Date date; // متغيير التاريخ والوقت
	private String dateStringPretty;
	
	public SLogFile(String filepath) {
		this(new File(filepath));
	}
	
	public SLogFile(File file) {
		this.filepath = file.getPath();
		this.filename = file.getName();
		findDate();
	}
	
	private void findDate() {
		if (filename.contains("temp")) return;
		dateString = filename.substring(filename.lastIndexOf('_') + 1, filename.lastIndexOf('.'));
		date = Util.toDate(dateString,Snooper.fileNameFormat);
		
		dateStringPretty = Util.toString(date, Snooper.perHourFormat);
	}
	
	public String getFilepath() {
		return filepath;
	}
	
	public File getFile() {
		return new File(filepath);
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getDateString() {
		return dateString;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String toString() {
		if (dateStringPretty == null || dateStringPretty.isEmpty()) return "SLOG: temp";
		return "SLOG: " + dateStringPretty;
	}
	
	public static Image getImage() {
		return Util.createJavaFXImage("images/slog.png");
	}
	
	public static Image getSelectedImage() {
		return Util.createJavaFXImage("images/slog_selected.png");
	}
	
	public static ImageView getImageView() {
		return new ImageView(getImage());
	}
	
	public static ImageView getSelectedImageView() {
		return new ImageView(getSelectedImage());
	}
        
	// لتعيين تخطيط الخلايا (لاستخدامها في مصنع الخلايا لإنشاء خلايا بها صورة وتسمية)
	//for setting the layout of the cells (to be used in cell factory to create cells with image and label)
	public HBox getHBox(boolean selected) {
		ImageView imageView = (selected) ? getSelectedImageView() : getImageView();
		HBox bar = new HBox(imageView, new Label(toString()));
		bar.setAlignment(Pos.CENTER_LEFT);
		return bar;
	}
	
	public void copyTo(File targetFile) {
		try {
			Files.copy(getFile().toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isTemp() {
		if (filename.contains("temp")) return true;
		else return false;
	}
	
	public boolean isValid() {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)))) {
			String line;
			int counter = 0;
			String separator = SnoopReader.DEFAULT_SEPARATOR;
			while((line = reader.readLine()) != null) {
				String[] s = line.split(separator);
				
				if (line.contains(separator) && s.length == 2) {
					return true;
				}
				
				counter++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.notif(Snooper.TITLE, "Problem loading file (SnoopReader): " + filepath);
		}
		return false;
	}
}