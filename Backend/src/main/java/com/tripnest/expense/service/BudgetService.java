package com.tripnest.expense.service;

import com.tripnest.expense.dto.BudgetDTO;
import java.util.Optional;

public interface BudgetService {
    BudgetDTO createBudget(BudgetDTO budgetDTO);
    BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO);
    void deleteBudget(Long id);
    BudgetDTO getBudgetById(Long id);
    BudgetDTO getBudgetByTripId(Long tripId);
}
