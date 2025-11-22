package com.hamza.courseenrollmentsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "api_cache")
public class ApiCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiId;

    @Column(columnDefinition = "TEXT")
    private String cachedData;

    private LocalDateTime cacheTime;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getApiId() { return apiId; }
    public void setApiId(String apiId) { this.apiId = apiId; }
    public String getCachedData() { return cachedData; }
    public void setCachedData(String cachedData) { this.cachedData = cachedData; }
    public LocalDateTime getCacheTime() { return cacheTime; }
    public void setCacheTime(LocalDateTime cacheTime) { this.cacheTime = cacheTime; }
}

