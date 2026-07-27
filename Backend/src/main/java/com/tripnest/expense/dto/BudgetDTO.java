package com.tripnest.expense.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {
    private Long id;
    private Long tripId;
    private BigDecimal totalBudget;
    private BigDecimal transportationBudget;
    private BigDecimal accommodationBudget;
    private BigDecimal foodBudget;
    private BigDecimal shoppingBudget;
    private BigDecimal entertainmentBudget;
    private BigDecimal activitiesBudget;
    private BigDecimal miscellaneousBudget;
    private String notes;
}
