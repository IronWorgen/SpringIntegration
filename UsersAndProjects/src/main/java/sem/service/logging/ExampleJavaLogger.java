package sem.service.logging;
import org.springframework.stereotype.Component;

import  java.util.logging.Logger;
@Component("ExampleJavaLogger")
public class ExampleJavaLogger implements  ApplicationLogger{
    Logger logger = Logger.getLogger("ExampleJavaLogger");

    @Override
    public void log(String message) {
        logger.info(message);
    }
}
