/**
 * 
 */
package com.viewhigh.libs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author Huntero
 */
public class HLog {
	public static boolean LOGI = true;
	public static boolean LOGD = true;
	public static boolean LOGE = true;

	private static String LOG_FILENAME_PREFIX = "log_";
	private static String LOG_PATH = "/";

	private static final byte CHAR_I = 0x01;
	private static final byte CHAR_D = 0x02;
	private static final byte CHAR_E = 0x03;
	private static final byte CHAR_BLANK = 0x20; // ' '
	private static final byte CHAR_NEWLINE = 0x0A; // '\n'
	private static final byte CHAR_SEPARATORS = 0x3A; // ':'
	
	private static final String UTF8 = "UTF-8";

	private static AtomicBoolean isWriteToFile = new AtomicBoolean(false);

	static{
		try {
			InputStream in = HLog.class.getResourceAsStream("/assets/log.properties");
			if(in == null){
				Log.i("HLog", "Properties is not exists");
			}else{
				Properties config = new Properties();
				config.load(in);
				LOG_PATH = config.getProperty("log_path", LOG_PATH);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int i(String tag, String msg) {
		if (HLog.LOGI) {
			write(tag, HLog.CHAR_I, msg, null);
			return Log.i(tag, msg);
		} else {
			return 0;
		}
	}

	public static int i(String tag, String msg, Throwable tr) {
		if (HLog.LOGI) {
			write(tag, HLog.CHAR_I, msg, tr);
			return Log.i(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int d(String tag, String msg) {
		if (HLog.LOGD) {
			write(tag, HLog.CHAR_D, msg, null);
			return Log.d(tag, msg);
		} else {
			return 0;
		}
	}

	public static int d(String tag, String msg, Throwable tr) {
		if (HLog.LOGD) {
			write(tag, HLog.CHAR_D, msg, tr);
			return Log.d(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int e(String tag, String msg) {
		if (HLog.LOGE) {
			write(tag, HLog.CHAR_E, msg, null);
			return Log.e(tag, msg);
		} else {
			return 0;
		}
	}

	public static int e(String tag, String msg, Throwable tr) {
		if (HLog.LOGE) {
			write(tag, HLog.CHAR_E, msg, tr);
			return Log.e(tag, msg, tr);
		} else {
			return 0;
		}
	}
	/**
	 * 从Throwable取得跟踪堆栈的函数
	 * @param tr
	 * @return
	 */
	public static String getStackTraceString(Throwable tr){
		if(tr == null)
			return "";
		String _r = null; 
		try {
			StringWriter sw=new StringWriter();
			PrintWriter pw=new PrintWriter(sw);
			tr.printStackTrace(pw);
			_r=sw.toString();
		} catch (Exception e) {
			_r="";
		}
		return _r;
	}
	private synchronized static void write(String tag, byte level, String msg, Throwable tr) {
		OutputStream out=getOutputStream();
		if(out == null)return;
		
		//------//
		try {
			out.write(getCurrentTime().getBytes(HLog.UTF8));
			out.write(HLog.CHAR_BLANK);
			out.write(("("+level+")").getBytes(HLog.UTF8));
			out.write(HLog.CHAR_BLANK);
			out.write(tag.getBytes(HLog.UTF8));
			out.write(HLog.CHAR_SEPARATORS);
			out.write(HLog.CHAR_BLANK);
			if(!TextUtils.isEmpty(msg)){
				out.write(msg.getBytes(HLog.UTF8));
				out.write(HLog.CHAR_NEWLINE);
			}
			if(tr != null){
				out.write(Log.getStackTraceString(tr).getBytes(HLog.UTF8));
				out.write(HLog.CHAR_NEWLINE);
			}
			
			out.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static OutputStream getOutputStream() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				File logPath=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+LOG_PATH);
				if(!logPath.exists()){
					logPath.mkdirs();
				}
				File logFile=new File(logPath,HLog.LOG_FILENAME_PREFIX+getTimeOfToday()+".log");
				
				return new FileOutputStream(logFile,true);	//追加内容至文件末尾
			} catch (FileNotFoundException e) {
				Log.e("Huntero", "HLog output file stream exception", e);
			}
		}
		return null;
	}
	
	@SuppressLint("SimpleDateFormat")
	private static String getTimeOfToday(){
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(new Date());
	}
	@SuppressLint("SimpleDateFormat")
	private static String getCurrentTime(){
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
		return format.format(new Date());
	}
}
