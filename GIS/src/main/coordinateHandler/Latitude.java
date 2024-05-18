package main.coordinateHandler;

public class Latitude {
    private int latitude,minutes,seconds;

    public Latitude(int latitude, int minutes, int seconds) {
        this.latitude = latitude;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public void translate(int latitude, int minutes, int seconds) {
        int secondsTranslate = (60*minutes)+seconds;

        this.latitude += latitude;

    }
}
