package application;

public class WatchTime {
		public  int hour;
		public int minutes;
		public enum Day{Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday};
		public Day day;
		
		//Used for certain times in workers i.e. shifts
		public WatchTime(int hour, int minutes) {
			this.hour = hour;
			this.minutes = minutes;
		}
		
		public WatchTime(int hour) {
			this.hour = hour;
			minutes = 0;
		}
		
		public void setTime(int hour, int minutes) {
			this.hour = hour;
			this.minutes = minutes;
		}
		
		public int getTimeHour(){
			return this.hour;
		}
}