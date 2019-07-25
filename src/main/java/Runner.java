
import entity.Event;
import service.EventProcessor;
import service.EventService;
import service.MyReader;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.*;

public class Runner {

    public static void main(String[] args) {
        String filePath = JOptionPane.showInputDialog("Please provide log file path");
        String threadCount = JOptionPane.showInputDialog("Please provide number of threads, that must be used");
        execute(filePath, Integer.valueOf(threadCount));
    }

    private static void execute(String filePath, int threadCount) {
        MyReader reader = new MyReader(filePath);
        Map<String, Event> eventMap = new ConcurrentHashMap<>();
        EventService service = new EventService();
        ExecutorService executorService =
                new ThreadPoolExecutor(threadCount, threadCount, 1000L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());

        for(int i = 0;i < threadCount; i++) {
            EventProcessor eventProcessor = new EventProcessor(reader, eventMap, service);
            executorService.execute(eventProcessor);
        }

        executorService.shutdown();
    }
}
