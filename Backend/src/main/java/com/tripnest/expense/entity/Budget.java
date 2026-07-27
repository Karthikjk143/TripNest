package com.tripnest.expense.entity;

import com.tripnest.trip.entity.Trip;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false, unique = true)
    private Trip trip;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalBudget;

    @Column(precision = 10, scale = 2)
    private BigDecimal transportationBudget;

    @Column(precision = 10, scale = 2)
    private BigDecimal accommodationBudget;

    @Column(precision = 10, scale = 2)
    private BigDecimal foodBudget;

    @Column(precision = 10, scale = 2)
    private BigDecimal shoppingBudget;

    @Column(precision = 10, scale = 2)
    private BigDecimal entertainmentBudget;

    @Column(precision = 10, scale = 2)
    private BigDecimal activitiesBudget;

    @Column(precision = 10, scale = 2)
    private BigDecimal miscellaneousBudget;

    @Column(columnDefinition = "LONGTEXT")
    private String notes;
}
