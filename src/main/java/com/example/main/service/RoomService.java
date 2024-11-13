package com.example.main.service;

import com.example.main.model.Room;
import com.example.main.model.dto.RoomFilterDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Room> getRoomsWithPaginationAndFilters(RoomFilterDTO roomFilterDTO) {
        String type = roomFilterDTO.getType();
        Boolean guestFavorite = roomFilterDTO.getGuestFavorite();
        Double minPrice = roomFilterDTO.getMinPrice();
        Double maxPrice = roomFilterDTO.getMaxPrice();
        Integer capacity = roomFilterDTO.getCapacity();
        int page = roomFilterDTO.getPage();
        int size = roomFilterDTO.getSize();

        // Create the CriteriaBuilder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);

        // Root for the Room entity
        Root<Room> roomRoot = criteriaQuery.from(Room.class);

        // Create predicates (filters)
        Predicate filters = criteriaBuilder.conjunction();  // Start with an empty conjunction (AND)

        if (type != null) {
            filters = criteriaBuilder.and(filters, criteriaBuilder.equal(roomRoot.get("type"), type));
        }
        if (guestFavorite != null) {
            filters = criteriaBuilder.and(filters, criteriaBuilder.equal(roomRoot.get("guestFavorite"), guestFavorite));
        }
        if (minPrice != null && maxPrice != null) {
            filters = criteriaBuilder.and(filters, criteriaBuilder.between(roomRoot.get("price"), minPrice, maxPrice));
        } else if (minPrice != null) {
            filters = criteriaBuilder.and(filters, criteriaBuilder.greaterThanOrEqualTo(roomRoot.get("price"), minPrice));
        } else if (maxPrice != null) {
            filters = criteriaBuilder.and(filters, criteriaBuilder.lessThanOrEqualTo(roomRoot.get("price"), maxPrice));
        }
        if (capacity != null) {
            filters = criteriaBuilder.and(filters, criteriaBuilder.equal(roomRoot.get("capacity"), capacity));
        }

        // Apply filters to the query
        criteriaQuery.where(filters);

        // Create the pageable object
        Pageable pageable = PageRequest.of(page, size);
        Query query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        // Get results and return as a Page
        long count = getCount(filters); // To get total number of records for pagination
        return new PageImpl<>(query.getResultList(), pageable, count);
    }

    private long getCount(Predicate filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Room> countRoot = countQuery.from(Room.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(filters);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
