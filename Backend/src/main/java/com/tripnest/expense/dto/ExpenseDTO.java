package com.tripnest.expense.dto;

import com.tripnest.expense.entity.ExpenseCategory;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private Long tripId;
    private Long paidByUserId;
    private String paidByUserName;
    private String description;
    private ExpenseCategory category;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String notes;
    private String status;
}
