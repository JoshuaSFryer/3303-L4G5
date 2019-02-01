import java.util.TimerTask;

public class TimedEvent extends TimerTask {
    private Event event;

    public TimedEvent(Event event){
        this.event = event;
    }

    public void run(){
        System.out.println();
        // send it using the floor sender
        FloorSender.getInstance().sendFloorClick(event.getFloor(), event.getDirection());
        // add it to the triggered event map to send on receipt of floor arrival
        TriggeredEventMap.getInstance().add(event);
    }
}
