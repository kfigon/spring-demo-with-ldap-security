package com.example.demo.repository;

import com.example.demo.dto.WorkReportResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class WorkReportRepository {



//    Dołożyć filtrowanie:
//
//po zakresie (np. styczeń - czerwiec) np. dla klienta XYZ
//kto hurtowo wypełnia timeports a kto codziennie/mniejszymi interwałami
//średni czas zaczęcia/skończenia pracy dla danej osoby (kto nie trzyma się core hours?)
//przodownicy pracy - najwięcej godzin dziennie
    @Autowired
    private EntityManager entityManager;

    private static final String CORE_QUERY =
            "select t.month, t.year, sum(d.worktime/1000/3600) as workedHours, " +
            "a.name as name, a.surname as surname, p.name as project, c.name as client\n" +
            "from dailytime d\n" +
            "         join timesheetreport t on d.timesheetreportid = t.id\n" +
            "         join account a on t.accountid = a.id\n" +
            "         join project p on d.projectid = p.id\n" +
            "         join client c on p.clientid = c.id\n";


    public List<WorkReportResultDto> getReportByPerson(String name, String surname, int startIndex, int pageSize) {
        Query query = entityManager.createNativeQuery(
                CORE_QUERY +
                        "where " +
                        " a.name = ? \n" +
                        " and a.surname = ? \n" +
                        "group by  a.surname, a.name, t.month, t.year,  p.name, c.name\n" +
                        "order by t.year, t.month, a.surname, p.name, c.name")
                .setParameter(1, name)
                .setParameter(2, surname);

        return executeAndPaginateQuery(startIndex, pageSize, query);
    }

    public List<WorkReportResultDto> getReportByClient(String client, int startIndex, int pageSize) {
        Query query = entityManager.createNativeQuery(
                CORE_QUERY +
                        "where " +
                        " c.name = ? \n" +
                        "group by  c.name, p.name, a.surname, a.name, t.month, t.year  \n" +
                        "order by t.year, t.month, p.name, c.name, a.surname")
                .setParameter(1, client);

        return executeAndPaginateQuery(startIndex, pageSize, query);
    }
    public List<WorkReportResultDto> getReportByProject(String project, int startIndex, int pageSize) {
        Query query = entityManager.createNativeQuery(
                CORE_QUERY +
                        "where " +
                        " p.name = ? \n" +
                        "group by  p.name, c.name, a.surname, a.name, t.month, t.year  \n" +
                        "order by t.year, t.month, p.name, c.name, a.surname")
                .setParameter(1, project);

        return executeAndPaginateQuery(startIndex, pageSize, query);

    }

    private List<WorkReportResultDto> executeAndPaginateQuery(int startIndex, int pageSize, Query query) {
        return (List<WorkReportResultDto>)
                query
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
        .getResultList()
                .stream()
                .map(this::mapToReportResultDto)
                .collect(Collectors.toList());
    }

    private WorkReportResultDto mapToReportResultDto(Object o) {
        Object[] result = (Object[]) o;
        return WorkReportResultDto.builder()
                .month((Integer) result[0])
                .year((Integer) result[1])
                .workedHours((BigDecimal) result[2])
                .name((String) result[3])
                .surname((String) result[4])
                .project((String) result[5])
                .client((String) result[6])
                .build();
    }
}
