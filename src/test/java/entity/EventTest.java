package entity;

import entity.enums.EventState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {

    private Event event;

    @Before
    public void setup() {
        event = new Event("testId", EventState.STARTED, 1491377495212L);
    }

    @Test
    public void shouldNotUpdateEventBecauseOfDifferentId() {
        //given
        Event secondEvent = new Event("differentId", EventState.FINISHED, 1491377495212L);
        //when
        event.update(secondEvent);
        //then
        assertEquals(EventState.STARTED, event.getState());
        assertNull(event.getDuration());
    }

    @Test
    public void shouldNotUpdateEventBecauseOfSameState() {
        //given
        Event secondEvent = new Event("testId", EventState.STARTED, 1491377495215L);
        //when
        event.update(secondEvent);
        //then
        assertEquals(EventState.STARTED, event.getState());
        assertNull(event.getDuration());
    }

    @Test
    public void shouldNotUpdateEventBecauseEventTimestampLessThenBaseEventTimestamp() {
        //given
        Event secondEvent = new Event("testId", EventState.FINISHED, 1491377495210L);
        //when
        event.update(secondEvent);
        //then
        assertEquals(EventState.STARTED, event.getState());
        assertNull(event.getDuration());
    }

    @Test
    public void shouldUpdateBaseEventState() {
        //given
        Event secondEvent = new Event("testId", EventState.FINISHED, 1491377495215L);
        //when
        event.update(secondEvent);
        //then
        assertEquals(EventState.FINISHED, event.getState());
    }

    @Test
    public void shouldSetDuration() {
        //given
        Event secondEvent = new Event("testId", EventState.FINISHED, 1491377495217L);
        //when
        event.update(secondEvent);
        //then
        assertNotNull(event.getDuration());
    }

    @Test
    public void shouldSetAlertTrue() {
        //given
        Event secondEvent = new Event("testId", EventState.FINISHED, 1491377495217L);
        //when
        event.update(secondEvent);
        //then
        assertTrue(event.getAlert());
    }

    @Test
    public void shouldSetAlertFalse() {
        //given
        Event secondEvent = new Event("testId", EventState.FINISHED, 1491377495214L);
        //when
        event.update(secondEvent);
        //then
        assertFalse(event.getAlert());
    }
}