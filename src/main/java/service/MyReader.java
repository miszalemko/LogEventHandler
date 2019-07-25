package service;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Getter
public class MyReader implements Read {

    private BufferedReader reader;
    private static final Logger logger = LogManager.getLogger(MyReader.class);


    public MyReader(String filePath) {
        initReader(filePath);
    }

    private void initReader(String filePath) {
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
    }

    @Override
    public String readLine() throws IOException {
        if (reader != null) {
            return reader.readLine();
        }
        return null;
    }
}
