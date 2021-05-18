package info.servobot.tournaments.trash;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Match {
    @JsonProperty("Round")
    private int round;

    @JsonProperty("Player1")
    private String player1;

    @JsonProperty("Archetype1")
    private String archetype1;

    @JsonProperty("Player2")
    private String player2;

    @JsonProperty("Archetype2")
    private String archetype2;

    @JsonProperty("Day")
    private String day;

    public String toString() {
        StringBuilder t = new StringBuilder();
        return t.append(round).append(',').append(player1).append(',').append(archetype1).append(",vs.,")
                .append(player2).append(',').append(archetype2).append(',').append(day).toString();
    }
}
