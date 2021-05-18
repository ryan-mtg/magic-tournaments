package info.servobot.tournaments.trash;

import feign.Feign;
import feign.Logger;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

public interface DeckClient {
    @RequestLine("GET /{deck}.json")
    Deck getDeck(@Param("deck") final String deck);

    static DeckClient newClient() {
        String url = getServerUrl();
        return Feign.builder().client(new OkHttpClient()).decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(DeckClient.class)).logLevel(Logger.Level.FULL)
                .target(DeckClient.class, url);
    }

    static String getServerUrl() {
        return "https://s3-us-west-1.amazonaws.com/hvn-decklist.magic.gg";
    }
}
