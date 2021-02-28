package ch.gamepowerx.notes;

public class Pause {
    private final Long duration;

    private boolean inTicks;

    public Pause(Integer duration){
        this.duration = duration.longValue();
    }

    public Pause(Long duration,boolean inTicks){
        this.duration = duration;
        this.inTicks = inTicks;
    }

    public int getDurationInt(){
        return duration.intValue();
    }

    public long getDuration(){
        return duration;
    }

    public boolean isInTicks(){
        return inTicks;
    }
}
