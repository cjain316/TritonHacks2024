package main.coordinateHandler;

public class Latitude {
    private int latitude,minutes,seconds;

    public Latitude(int latitude, int minutes, int seconds) {
        this.latitude = latitude;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public void translate(int latitude, int minutes, int seconds) {
        this.seconds += seconds;
        this.minutes += minutes;
        this.latitude += latitude;
        
        if(seconds >= 60) {
        	minutes += seconds / 60;
        	seconds = seconds % 60;
        }
        
        if(minutes >= 60) {
        	latitude += minutes / 60;
        	minutes = minutes % 60;
        }
        
        if(latitude > 180) {
        	latitude = latitude % 180;
        }
    }
}
