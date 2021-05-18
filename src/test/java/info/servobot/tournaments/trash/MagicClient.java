package info.servobot.tournaments.trash;

import feign.Feign;
import feign.Logger;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

public interface MagicClient {
    @RequestLine("GET /api/contentful/entry/7A0yn3NRQtapBjbvYxTrzh")
    RivalsSchedule getRivalsSchedule();

    static MagicClient newClient() {
        String url = getServerUrl();
        return Feign.builder().client(new OkHttpClient()).decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(MagicClient.class)).logLevel(Logger.Level.FULL)
                .target(MagicClient.class, url);
    }

    static String getServerUrl() {
        return "https://magic.gg/";
    }
}
