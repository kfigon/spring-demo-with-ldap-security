package com.example.demo.repository;

import com.example.demo.dto.BusinessTripReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BusinessTripReportRepository {
    private static final String CORE_QUERY = "select count(*),\n" +
            " a.name,\n" +
            "       a.surname,\n" +
            "       SUM( CASE WHEN bt.insurancebuyby = 'JIT_INSURANCE' THEN 1 ELSE 0 END ) as payed_by_jit,\n" +
            "       p.name as project,\n" +
            "       c.name as client\n" +
            " from businesstrip bt\n" +
            " join account a on bt.accountid = a.id\n" +
            " join project p on bt.projectid = p.id\n" +
            " join client c on bt.clientid = c.id\n";
    //raport dot. delegacji - kto jeździ i ile (per osoba, per klient, per project)
    // + informacja za jaką część z tych delegacji płaci jit a za jaką klient
    @Autowired
    private EntityManager entityManager;

    public List<BusinessTripReportDto> findBusinessTripsByPerson(String name, String surname, int pageStart, int pageSize) {
        String sql = CORE_QUERY +
                "where a.name = ? \n" +
                "and a.surname = ? \n" +
                "group by a.surname, a.name, p.name, c.name\n";

        Query query = entityManager.createNativeQuery(sql)
                .setParameter(1, name)
                .setParameter(2, surname);

        return executeAndPaginate(pageStart, pageSize, query);
    }

    public List<BusinessTripReportDto> findBusinessTripsByClient(String client, int pageStart, int pageSize) {
        String sql = CORE_QUERY +
                "where c.name = ? \n" +
                "group by c.name, p.name, a.surname, a.name\n";

        Query query = entityManager.createNativeQuery(sql)
                .setParameter(1, client);

        return executeAndPaginate(pageStart, pageSize, query);
    }

    public List<BusinessTripReportDto> findBusinessTripsByProject(String project, int pageStart, int pageSize) {
        String sql = CORE_QUERY +
                "where p.name = ? \n" +
                "group by p.name, c.name, a.surname, a.name\n";

        Query query = entityManager.createNativeQuery(sql)
                .setParameter(1, project);

        return executeAndPaginate(pageStart, pageSize, query);
    }

    private List<BusinessTripReportDto> executeAndPaginate(int pageStart, int pageSize, Query query) {
        return (List<BusinessTripReportDto>) query
                .setFirstResult(pageStart)
                .setMaxResults(pageSize)
                .getResultList()
                .stream()
                .map(this::mapToReportResultDto)
                .collect(Collectors.toList());
    }

    private BusinessTripReportDto mapToReportResultDto(Object o) {
        Object[] result = (Object[]) o;

        return BusinessTripReportDto.builder()
                .numberOfTrips((BigInteger) result[0])
                .name((String) result[1])
                .surname((String) result[2])
                .howManyByJit((BigInteger) result[3])
                .project((String) result[4])
                .client((String) result[5])
                .build();
    }
}