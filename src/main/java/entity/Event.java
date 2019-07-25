package entity;

import entity.enums.EventState;
import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {

    private static final Logger logger = LogManager.getLogger(Event.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbId;

    @NonNull
    private String id;

    @Transient
    @NonNull
    private EventState state;

    private String type;

    private Integer host;

    @Transient
    @NonNull
    private Long timestamp;

    private Long duration;

    private Boolean alert;

    public Event(@NonNull String id, @NonNull EventState state, @NonNull Long timestamp) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
    }

    public Event(@NonNull String id, String type, Integer host, Long duration, Boolean alert) {
        this.id = id;
        this.type = type;
        this.host = host;
        this.duration = duration;
        this.alert = alert;
    }

    public void update(Event event) {
        String eventId = event.getId();
        if (!id.equals(eventId)) {
            logger.error("Cannot update event by another with different ids: " + id + " and " + eventId);
            return;
        }
        if (state.equals(event.getState())) {
            logger.error("Cannot update event by another with same state: " + state);
            return;
        }
        if (EventState.STARTED.equals(state)) {
            if(event.getTimestamp() - timestamp <= 0) {
                logger.error("Finish timestamp can't be less then start one");
                return;
            }
            state = event.getState();
            duration = event.getTimestamp() - timestamp;
        } else {
            if(timestamp - event.getTimestamp() <= 0) {
                logger.error("Finish timestamp can't be less then start one");
                return;
            }
            duration = timestamp - event.getTimestamp();
        }

        if (duration > 4) {
            setAlert(true);
        } else {
            setAlert(false);
        }
        logger.info("Event with id " + id + " finished processing with duration " + duration);
    }
}
