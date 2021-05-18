package info.servobot.tournaments.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "organization_user")
@IdClass(OrganizationUserRow.OrganizationUserId.class)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationUserRow {
    @Id
    @Column(name = "organization_id")
    private int organizationId;

    @Id
    @Column(name = "user_id")
    private int userId;

    private int roles;

    @Data
    @NoArgsConstructor
    public static class OrganizationUserId implements Serializable {
        private int organizationId;
        private int userId;
    }
}
