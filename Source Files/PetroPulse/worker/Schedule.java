package worker;
public class Schedule {
    private int stationId;
    private String date;
    private String day;
    private String startTime;
    private String endTime;
    private int workerId;

    public Schedule(int stationId, String dat, String day, String startTime, String endTime, int workerId) 
    {
        this.stationId = stationId;
        this.day = day;
        this.date = dat;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workerId = workerId;
    }

    public int getStationId() {
        return stationId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getWorkerId() {
        return workerId;
    }
    
    public String getDate() {
        return this.date;
    }

    public String getDay() {
        return this.day;
    }
}
