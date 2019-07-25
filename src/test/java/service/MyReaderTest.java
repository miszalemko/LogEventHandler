package service;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class MyReaderTest {

    private MyReader myReader;

    @Test
    public void shouldInitReader() {
        //given
        URL res = getClass().getClassLoader().getResource("dataserver_log.log");
        //when
        myReader = new MyReader(res.getPath());
        //then
        assertNotNull(myReader.getReader());
    }

    @Test
    public void shouldNotInitReader() {
        //given
        //when
        myReader = new MyReader("dummy_path");
        //then
        assertNull(myReader.getReader());
    }

    @Test
    public void shouldReturnLine() throws IOException {
        //given
        URL res = getClass().getClassLoader().getResource("dataserver_log.log");
        //when
        myReader = new MyReader(res.getPath());
        //then
        assertEquals("{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495212}", myReader.readLine());
    }

    @Test
    public void shouldReturnNull() throws IOException {
        //given
        //when
        myReader = new MyReader("dummy_path");
        //then
        assertNull(myReader.readLine());
    }
}