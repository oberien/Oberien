package logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class GameLogger {
	public static final Logger logger = Logger.getLogger(GameLogger.class.getName());
	
	static {
		init();
	}
	
	public static void init() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			Handler[] handlers = logger.getHandlers();
			for (Handler h : handlers) {
				logger.removeHandler(h);
			}
			File f = new File("logs/game/");
			if (!f.exists()) {
				f.mkdirs();
			}
			FileHandler fh = new FileHandler("logs/game/" + sdf.format(new Date(System.currentTimeMillis())) + ".log");
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
			logger.setLevel(Level.ALL);
		} catch (Exception e) {ErrorLogger.logger.severe(e.getMessage());}
	}
	
}
