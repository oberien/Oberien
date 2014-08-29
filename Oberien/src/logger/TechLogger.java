package logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TechLogger {
	public static final Logger logger = Logger.getLogger(TechLogger.class.getName());
	private static final Logger slickLogger = Logger.getLogger("org.newdawn");
	private static final Logger niftyLogger = Logger.getLogger("de.lessvoid");
	
	public static void init() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			Handler[] handlers = logger.getHandlers();
			for (Handler h : handlers) {
				logger.removeHandler(h);
			}
			handlers = slickLogger.getHandlers();
			for (Handler h : handlers) {
				slickLogger.removeHandler(h);
			}
			handlers = niftyLogger.getHandlers();
			for (Handler h : handlers) {
				niftyLogger.removeHandler(h);
			}
			File f = new File("logs/tech/");
			if (!f.exists()) {
				f.mkdirs();
			}
			FileHandler fh = new FileHandler("logs/tech/" + sdf.format(new Date(System.currentTimeMillis())) + ".log");
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
			logger.setLevel(Level.ALL);
			slickLogger.addHandler(fh);
			slickLogger.setLevel(Level.ALL);
			niftyLogger.addHandler(fh);
			niftyLogger.setLevel(Level.ALL);
		} catch (Exception e) {e.printStackTrace();}
	}
}
