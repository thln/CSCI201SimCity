package application;

public class TimeManager {

	private static final long startTime = System.currentTimeMillis();

	private static TimeManager timeManager;

	public enum Day{Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday};
	
	private boolean europeanCalendar = true;
	
	private static int speedOfTime = 80;

	private TimeManager() {
	}

	public static TimeManager getTimeManager() {
		if (timeManager == null) {
			timeManager = new TimeManager();
			return timeManager;
		}
		else {
			return timeManager;
		}
	}

	public Time getTime() {
		//Could implement a "time slider"
		long runTime;
		runTime = System.currentTimeMillis() - startTime;
		long simulationMinutes;
		simulationMinutes = runTime/speedOfTime;
		Time time = new Time((int) simulationMinutes);

		return time;
	}
/*
	public Time getTime(int faster)
	{
		long runTime;
		runTime = System.currentTimeMillis() - startTime;

		long simulationMinutes;
		simulationMinutes = runTime/faster;
		Time time = new Time((int) simulationMinutes);

		return time;
	}*/
	
	public void fastForward(int faster)
	{
		speedOfTime = faster;
	}
	
	public void resetTimeSpeed()
	{
		speedOfTime = 80;
	}
	
	public class Time {
		public final int dayMinute;
		public final int dayHour;
		public Day day = Day.Sunday;

		//Used to calculate simulation time
		Time(int simulationMinutesSinceBegnningOfTime) {
			dayMinute = (int) simulationMinutesSinceBegnningOfTime % 60;
			dayHour = ((int) simulationMinutesSinceBegnningOfTime / 60) % 24;

			int daysSinceStartOfTime;
			daysSinceStartOfTime = (simulationMinutesSinceBegnningOfTime / 60) / 24;

			int dayOfWeek;
			dayOfWeek = daysSinceStartOfTime % 7;
			if(!europeanCalendar)
			{
				switch (dayOfWeek) {
				case 0:
					day = Day.Sunday;
					break;
				case 1:
					day = Day.Monday;
					break;
				case 2:
					day = Day.Tuesday;
					break;
				case 3:
					day = Day.Wednesday;
					break;
				case 4:
					day = Day.Thursday;
					break;
				case 5:
					day = Day.Friday;
					break;
				case 6:
					day = Day.Saturday;
					break;
				}
			}
			else
			{
				switch (dayOfWeek) {
				case 0:
					day = Day.Monday;
					break;
				case 1:
					day = Day.Tuesday;
					break;
				case 2:
					day = Day.Wednesday;
					break;
				case 3:
					day = Day.Thursday;
					break;
				case 4:
					day = Day.Friday;
					break;
				case 5:
					day = Day.Saturday;
					break;
				case 6:
					day = Day.Sunday;
					break;
				}
			}
		}
	}
	
	public static int getSpeedOfTime(){
		return speedOfTime;
	}
	
	public void setEuropeanCalendarTrue()
	{
		europeanCalendar = true;
	}
	
	public void setAmericanCalendarTrue()
	{
		europeanCalendar = false;
	}
}
