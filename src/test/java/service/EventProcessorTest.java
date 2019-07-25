package service;

import entity.Event;
import entity.enums.EventState;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class EventProcessorTest {

    private EventProcessor eventProcessor;

    @Before
    public void setup() {
        MyReader reader = new MyReader("dummy");
        Map<String, Event> eventMap = new ConcurrentHashMap<>();
        EventService service = new EventService();
        eventProcessor = new EventProcessor(reader, eventMap, service);
    }

    @Test
    public void shouldParseLineToEvent() {
        //given
        String line = "{\"id\":\"scsmbstgraqw\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217}";
        //when
        Event event = eventProcessor.getEventFromLine(line);
        //then
        assertNotNull(event);
        assertEquals("scsmbstgraqw", event.getId());
        assertEquals(EventState.FINISHED, event.getState());
        assertEquals("APPLICATION_LOG", event.getType());
        assertEquals(12345, (int)event.getHost());
        assertEquals(1491377495217L, (long)event.getTimestamp());
    }
}