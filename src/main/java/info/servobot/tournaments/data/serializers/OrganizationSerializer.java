package info.servobot.tournaments.data.serializers;

import info.servobot.tournaments.conductors.UserConductor;
import info.servobot.tournaments.data.entities.OrganizationRow;
import info.servobot.tournaments.data.entities.OrganizationUserRow;
import info.servobot.tournaments.data.repositories.OrganizationRepository;
import info.servobot.tournaments.data.repositories.OrganizationUserRepository;
import info.servobot.tournaments.model.Organization;
import info.servobot.tournaments.model.User;
import info.servobot.tournaments.utility.Flags;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class OrganizationSerializer {
    private final OrganizationRepository organizationRepository;
    private final OrganizationUserRepository organizationUserRepository;
    private final UserConductor userConductor;

    public List<Organization> loadOrganizations() {
        Iterable<OrganizationRow> organizationRows = organizationRepository.findAll();
        Iterable<OrganizationUserRow> organizationUserRows = organizationUserRepository.findAll();

        Map<Integer, List<OrganizationUserRow>> userRowsByOrganizationId =
                getIdMapping(organizationUserRows, OrganizationUserRow::getOrganizationId);

        userConductor.loadUsers(getIds(organizationUserRows, OrganizationUserRow::getUserId));

        List<Organization> organizations = new ArrayList<>();
        for (OrganizationRow organizationRow : organizationRows) {
           organizations.add(
                   createOrganization(organizationRow, userRowsByOrganizationId.get(organizationRow.getId())));
        }

        return organizations;
    }

    private Organization createOrganization(final OrganizationRow organizationRow,
            final List<OrganizationUserRow> organizationUserRows) {

        List<User> tournamentOrganizers = new ArrayList<>();
        List<User> admins = new ArrayList<>();

        for (OrganizationUserRow organizationUserRow : organizationUserRows) {
            User user = userConductor.getUser(organizationUserRow.getUserId());
            int roles = organizationUserRow.getRoles();
            if (Flags.hasFlag(roles, Organization.TOURNAMENT_ORGANIZER_FLAG)) {
                tournamentOrganizers.add(user);
            }

            if (Flags.hasFlag(roles, Organization.ADMIN_FLAG)) {
                admins.add(user);
            }
        }

        return new Organization(organizationRow.getId(), organizationRow.getName(), tournamentOrganizers, admins);
    }

    private static <RowType> Map<Integer, List<RowType>> getIdMapping(final Iterable<RowType> rows,
            final Function<RowType, Integer> extractId) {
        Map<Integer, List<RowType>> rowMap = new HashMap<>();
        for (RowType row: rows) {
            Integer id = extractId.apply(row);
            List<RowType> rowsWithId = rowMap.computeIfAbsent(id, i -> new ArrayList<>());
            rowsWithId.add(row);
        }
        return rowMap;
    }

    private static <RowType> Iterable<Integer> getIds(final Iterable<RowType> rows,
            final Function<RowType, Integer> extractId) {
        return StreamSupport.stream(rows.spliterator(), false).map(extractId::apply)
                .collect(Collectors.toList());
    }
}
