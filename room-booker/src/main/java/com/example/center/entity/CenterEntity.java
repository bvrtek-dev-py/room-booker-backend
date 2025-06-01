package com.example.center.entity;

import java.util.Optional;

import com.example.company.entity.CompanyEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "centers")
public class CenterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 255)
    private String name;
    @Column(length = 500)
    private String description;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    protected CenterEntity() {}

    public CenterEntity(Long id, String name, String description, CompanyEntity company) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public CenterEntity with(Optional<String> newName, Optional<String> newDescription, Optional<CompanyEntity> company)  {
        CenterEntity newCenter = new CenterEntity();
    
        newCenter.id = this.id;
        newCenter.name = newName.orElse(this.name);
        newCenter.description = newDescription.orElse(this.description);
        newCenter.company = company.orElse(this.company);
        
        return newCenter;
    }
}