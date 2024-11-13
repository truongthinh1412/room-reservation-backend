package com.example.main.model.dto;

public class RoomFilterDTO {
    private String type;
    private Boolean guestFavorite;
    private Double minPrice;
    private Double maxPrice;
    private Integer capacity;
    private int page;
    private int size;

    public RoomFilterDTO(String type, Boolean guestFavorite, Double minPrice, Double maxPrice, Integer capacity, int page, int size) {
        this.type = type;
        this.guestFavorite = guestFavorite;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.capacity = capacity;
        this.page = page;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getGuestFavorite() {
        return guestFavorite;
    }

    public void setGuestFavorite(Boolean guestFavorite) {
        this.guestFavorite = guestFavorite;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
