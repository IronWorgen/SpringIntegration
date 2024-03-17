package sem.service.logging;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component("ExampleLombokLogger")
public class ExampleLombokLogger implements ApplicationLogger {
    @Override
    public void log(String message) {
        log.info(message);
    }
}
