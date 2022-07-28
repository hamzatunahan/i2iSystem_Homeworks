package log4j;

import java.time.LocalDateTime;

import org.apache.logging.log4j.*;

public class TimerLog4j {
	private static Logger logger = LogManager.getLogger(TimerLog4j.class.getName());

	public static void main(String[] args) {

		while (true) {

			LocalDateTime time = LocalDateTime.now();
			int hour = time.getHour();
			int minute = time.getMinute();
			int second = time.getSecond();
			CheckLog(hour, minute, second);

		}
	}

	public static int CheckLog(int hour, int minute, int second) {

		if (hour != LocalDateTime.now().getHour()) {
			logger.error("Error");
		} else if (minute != LocalDateTime.now().getMinute()) {
			logger.info("info");
		} else if (second != LocalDateTime.now().getSecond()) {
			logger.debug("debug");
		}
		return 0;
	}

	public TimerLog4j() {
		// TODO Auto-generated constructor stub
	}

}
