package info.servobot.tournaments.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor @AllArgsConstructor
public class UserRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int flags;

    @Column(name = "twitch_id")
    private long twitchId;

    @Column(name = "twitch_name")
    @Size(max = 50)
    private String twitchName;
}