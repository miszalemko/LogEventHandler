package service;

import entity.Event;
import entity.enums.EventState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
For testing operation with database we will use special data base for test(like H2)
 only for showing purposes
 */
public class EventServiceTest {

    private EventService eventService;

    @Before
    public void setup() {
        eventService = new EventService();
        Event event = new Event("testId", "testType", 12345, 3L, false);
        eventService.saveEvent(event);
    }

    @Test
    public void shouldGetEventByEventId() {
        //given
        String eventId = "testId";
        //when
        Event event =  eventService.getEventsByEventId(eventId);
        //then
        assertNotNull(event);
        assertEquals(eventId, event.getId());
        assertEquals("testType", event.getType());
        assertEquals(12345, (int)event.getHost());
        assertEquals(3L, (long)event.getDuration());
        assertFalse(event.getAlert());
    }

    @Test
    public void shouldNotGetEventByEventId() {
        //given
        String eventId = "dummy";
        //when
        Event event =  eventService.getEventsByEventId(eventId);
        //then
        assertNull(event);
    }

    @Test
    public void shouldSaveEvent() {
        //given
        StringBuilder builder = new StringBuilder();
        int count = 20;
        while (count-- != 0) {
            int character = (int)(Math.random()*"ABCDEFGHIJKLMNOPQRSTUVWXYZ".length());
            builder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(character));
        }
        Event event = new Event(builder.toString(), "testType", 12345, 3L, false);
        //when
        eventService.saveEvent(event);
        Event savedEvent = eventService.getEventsByEventId(builder.toString());
        //then
        assertNotNull(savedEvent);
        assertEquals(builder.toString(), event.getId());
        assertEquals("testType", event.getType());
        assertEquals(12345, (int)event.getHost());
        assertEquals(3L, (long)event.getDuration());
        assertFalse(event.getAlert());
    }

    @Test
    public void shouldNotSaveEvent() {
        //given
        Event event = new Event("testId", "differentTestType", 54321, 5L, true);
        //when
        eventService.saveEvent(event);
        Event savedEvent = eventService.getEventsByEventId("testId");
        //then
        assertNotEquals("differentTestType", savedEvent.getType());
        assertNotEquals(54321, (int)savedEvent.getHost());
        assertNotEquals(5L, (long)savedEvent.getDuration());
        assertFalse(savedEvent.getAlert());
    }
}