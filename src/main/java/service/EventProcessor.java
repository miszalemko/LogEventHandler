package service;

import com.google.gson.Gson;
import entity.Event;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

@Getter
public class EventProcessor implements Runnable {


    private static final Logger logger = LogManager.getLogger(EventProcessor.class);

    private EventService service;
    private MyReader reader;
    private Map<String, Event> eventMap;

    public EventProcessor(MyReader reader, Map<String, Event> eventMap, EventService service) {
        this.reader = reader;
        this.eventMap = eventMap;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.debug(Thread.currentThread().getName() + " reading line: " + line);
                Event event = getEventFromLine(line);
                saveEvent(event);
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public Event getEventFromLine(String line) {
        Gson gson = new Gson();
        return gson.fromJson(line, Event.class);
    }

    public void saveEvent(Event event) {
        String eventId = event.getId();
        Event eventFromMap = eventMap.get(eventId);
        if (eventFromMap == null) {
            eventMap.put(eventId, event);
        } else {
            eventFromMap.update(event);
            service.saveEvent(eventFromMap);
            eventMap.remove(eventFromMap.getId());
        }
    }
}
