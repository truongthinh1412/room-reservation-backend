package com.example.main.service;

import com.example.main.model.Room;
import com.example.main.model.RoomAvailability;
import com.example.main.model.dto.RoomFilterDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        Date startDate = roomFilterDTO.getStartDate();
        Date endDate = roomFilterDTO.getEndDate();
        int pageNumber = roomFilterDTO.getPage();
        int pageSize = roomFilterDTO.getSize();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = cb.createQuery(Room.class);
        Root<RoomAvailability> root = query.from(RoomAvailability.class);

        // Join Room entity
        Join<RoomAvailability, Room> roomJoin = root.join("room");

        // Create a list of predicates for filtering
        List<Predicate> predicates = new ArrayList<>();

        // Add filters based on method parameters
        if (type != null && !type.isEmpty()) {
            predicates.add(cb.equal(roomJoin.get("type"), type));
        }
        if (capacity != null && capacity > 0) {
            predicates.add(cb.greaterThanOrEqualTo(roomJoin.get("capacity"), capacity));
        }
        if (maxPrice != null && maxPrice > 0) {
            predicates.add(cb.lessThanOrEqualTo(roomJoin.get("price"), maxPrice));
        }
        if (startDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("startOfAvailability"), startDate));
        }
        if (endDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("endOfAvailability"), endDate));
        }

        // Apply where clause
        query.select(roomJoin).distinct(true).where(predicates.toArray(new Predicate[0]));

        // Execute the query to get the list of rooms with pagination
        List<Room> rooms = entityManager.createQuery(query)
                .setFirstResult(pageNumber * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        // Get the total count for pagination
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<RoomAvailability> countRoot = countQuery.from(RoomAvailability.class);
        countQuery.select(cb.countDistinct(countRoot.get("room")))
                .where(predicates.toArray(new Predicate[0]));
        Long totalRooms = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(rooms, PageRequest.of(pageNumber, pageSize), totalRooms);
    }
}
