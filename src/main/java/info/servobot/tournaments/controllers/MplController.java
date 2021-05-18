package info.servobot.tournaments.controllers;

import info.servobot.tournaments.view.Bracket;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class MplController {
    @RequestMapping("/mpl")
    public String mplPage(final Model model) throws IOException {
        model.addAttribute("bracket", new Bracket());
        parseLeague();
        return "test";
    }

    void parseStandings() throws IOException {
        String url = "https://magic.gg/standings";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);

        //IOUtils.copy(response.getEntity().getContent(), new FileOutputStream(new File("league.txt")));
        List<String> lines = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))
                .lines().collect(Collectors.toList());

        Pattern parametersPattern = Pattern.compile(".*function\\((.*)\\)\\{return.*");
        Pattern argumentsPattern = Pattern.compile(".*\\}\\((.*)\\)\\);.*");
        List<String> keys = null;
        List<String> values = null;
        for (String line : lines) {
            Matcher m = parametersPattern.matcher(line);
            if (m.matches()) {
                keys = Arrays.asList(m.group(1).split(","));
            }
            Matcher argM = argumentsPattern.matcher(line);
            if (argM.matches()) {
                values = Arrays.asList(argM.group(1).split(","));
            }
        }
        for (int i = 0; i < keys.size(); i++) {
            System.out.println(keys.get(i) +  ": " + values.get(i));
        }
    }

    void parseLeague() throws IOException {
        String url = "https://magic.gg/league?activeLeague=ePxsGwEv75LcohyKuElmt";
        captureFile(url, "rivals_league_all.txt");

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);

        List<String> lines = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))
                .lines().collect(Collectors.toList());


        Pattern tablePattern = Pattern.compile(".*tbody(.*)tbody.*tbody(.*)tbody.*");
        PrintStream printStream = new PrintStream(new File("rivals_schedule.txt"), StandardCharsets.UTF_8.name());
        for (String line : lines) {
            Matcher m = tablePattern.matcher(line);
            if (m.matches()) {
                String table = m.group(1);
                System.out.println(table.length());
                System.out.println(table);
                for (String row : table.split("\\\\u003Ctr\\\\u003E")) {
                    System.out.println(row);
                    printStream.println(row);
                }

                table = m.group(2);
                System.out.println(table.length());
                System.out.println(table);
                for (String row : table.split("\\\\u003Ctr\\\\u003E")) {
                    System.out.println(row);
                    printStream.println(row);
                }
            }
        }
        printStream.close();
    }

    void captureFile(final String url, final String fileName) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);

        IOUtils.copy(response.getEntity().getContent(), new FileOutputStream(new File(fileName)));
    }
}
