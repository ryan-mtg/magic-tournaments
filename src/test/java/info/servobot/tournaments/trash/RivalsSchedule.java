package info.servobot.tournaments.trash;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor
public class RivalsSchedule {
    private List<Match> jsonObject;
}
