package sem.configs;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.AbstractPayloadTransformer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class RssIntegrationConfiguration {
    @Bean
    public FileWritingMessageHandler WritingHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("./RSS"/*название папки куда поместить файл*/));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }


    @Bean
    public AbstractPayloadTransformer<SyndEntry, String> extractLineFromRss() {
        return new AbstractPayloadTransformer<SyndEntry, String>() {
            @Override
            protected String transformPayload(SyndEntry payload) {
                return payload.getTitle() + " " + payload.getAuthor() + " " + payload.getLink();
            }

        };
    }

    @Bean
    public IntegrationFlow feedRSSFlow() throws MalformedURLException {
        return IntegrationFlow.from(
                        Feed.inboundAdapter(new URL("https://lenta.ru/rss"), "news"),
                        e -> e.poller(p -> p.fixedDelay(5000)))
                .transform(extractLineFromRss())
                .handle(WritingHandler())
                .get();
    }
}
