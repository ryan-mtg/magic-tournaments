package info.servobot.tournaments.trash;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    private static final Pattern TWITCH_PATTERN = Pattern.compile("https://www.twitch.tv/(.+)");
    private static final Pattern TWITTER_PATTERN = Pattern.compile("https://twitter.com/(.+)");
    private static final Pattern COUNTRY_PATTERN = Pattern.compile(".*\\((.+)\\)");

    @org.junit.jupiter.api.Test
    void createRivalsSchedule() throws FileNotFoundException, UnsupportedEncodingException {
        MagicClient magicClient = MagicClient.newClient();
        RivalsSchedule rivalsSchedule = magicClient.getRivalsSchedule();

        PrintStream printStream = new PrintStream(new File("rivals_schedule.txt"), StandardCharsets.UTF_8.name());
        for (Match match : rivalsSchedule.getJsonObject()) {
            printStream.println(match);
            System.out.println(match.toString());
        }
        printStream.close();
    }

    @org.junit.jupiter.api.Test
    void createMplPlayerList() throws IOException {
        String league = "rivals";
        String url = "https://magic.gg/" + league;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);

        Document document = Jsoup.parse(response.getEntity().getContent(), StandardCharsets.UTF_8.name(), url);

        Map<String, String> decklistMap =
            //buildDecklistMap("https://magic.gg/decklists/november-zendikar-rising-league-weekend-mpl-decklists");
            buildDecklistMap("https://magic.gg/decklists/november-zendikar-rising-league-weekend-rivals-league-decklists");

        PrintStream printStream = new PrintStream(new File(league + ".players"), StandardCharsets.UTF_8.name());
        Elements playerDivs = document.getElementsByClass("css-1YgOK");
        for (Element playerDiv : playerDivs) {
            String name = "???";
            String country = "???";
            String twitchName = "";
            String twitterName = "";

            Elements anchors = playerDiv.getElementsByTag("a");
            for (Element anchor : anchors) {
                String href = anchor.attr("href");
                Matcher twitchMatcher = TWITCH_PATTERN.matcher(href);
                if (twitchMatcher.matches()) {
                    twitchName = twitchMatcher.group(1);
                }

                Matcher twitterMatcher = TWITTER_PATTERN.matcher(href);
                if (twitterMatcher.matches()) {
                    twitterName = twitterMatcher.group(1);
                }
            }

            Elements flagImages = playerDiv.getElementsByClass("css-sNquP");
            for (Element flagImage : flagImages) {
                Matcher countryMatcher = COUNTRY_PATTERN.matcher(flagImage.attr("alt"));
                if (countryMatcher.matches()) {
                    country = countryMatcher.group(1);
                }
            }

            Elements pictureImages = playerDiv.getElementsByClass("css-W6Hx5");
            for (Element pictureImage : pictureImages) {
                name = pictureImage.attr("alt").trim();
            }

            String deckName = decklistMap.get(name + "-name");
            if (!decklistMap.containsKey(name + "-name")) {
                System.out.println(name);
                System.out.println("Why?");
            }
            String deckLink = decklistMap.get(name + "-link");

            System.out.println(name);
            System.out.println(country);
            System.out.println("twitch: " + twitchName);
            System.out.println("twitter: " + twitterName);
            printStream.println(String.format("%s,%s,%s,%s,0,,%s,%s", name, country, twitchName, twitterName, deckName, deckLink));
        }
        printStream.close();
    }

    private Map<String, String> buildDecklistMap(final String url) throws IOException {
        Map<String, String> map = new HashMap<>();

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);

        DeckClient deckClient = DeckClient.newClient();

        Document document = Jsoup.parse(response.getEntity().getContent(), StandardCharsets.UTF_8.name(), url);

        Elements deckDivs = document.getElementsByClass("css-3X0PN");
        for (Element deckDiv : deckDivs) {
            String id = deckDiv.attr("id");
            System.out.println(id);
            String realId = URLDecoder.decode(id, StandardCharsets.UTF_8.name());
            realId = URLDecoder.decode(realId, StandardCharsets.UTF_8.name());
            System.out.println(realId);

            Deck deck = deckClient.getDeck(realId);
            String name = deck.getPlayerName();
            String deckLink = String.format("%s#%s", url, id);
            System.out.println(String.format("%s: %s", name, deckLink));
            map.put(name + "-link", deckLink);
            map.put(name + "-name", deck.getDeckArchetype());
        }

        return map;
    }
}