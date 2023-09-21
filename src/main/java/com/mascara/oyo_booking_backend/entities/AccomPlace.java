package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="accom_place")
public class AccomPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "accom_name", columnDefinition = "NVARCHAR(255) NOT NULL")
    private String accomName;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "grade_rate")
    private Float gradeRate;

    @Column(name = "num_review")
    private Double numReview;
}
