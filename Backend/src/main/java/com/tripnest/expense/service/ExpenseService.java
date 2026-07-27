package com.tripnest.expense.service;

import com.tripnest.expense.dto.ExpenseDTO;
import com.tripnest.expense.dto.BudgetDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseDTO createExpense(ExpenseDTO expenseDTO);
    ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO);
    void deleteExpense(Long id);
    ExpenseDTO getExpenseById(Long id);
    List<ExpenseDTO> getExpensesByTrip(Long tripId);
    BigDecimal getTotalExpensesByTrip(Long tripId);
    List<ExpenseDTO> getExpensesByCategory(Long tripId, String category);
    List<ExpenseDTO> getExpensesByDateRange(Long tripId, LocalDate startDate, LocalDate endDate);
}
