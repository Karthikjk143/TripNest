package com.tripnest.expense.repository;

import com.tripnest.expense.entity.Expense;
import com.tripnest.expense.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByTripIdOrderByExpenseDateDesc(Long tripId);
    List<Expense> findByTripIdAndCategory(Long tripId, ExpenseCategory category);
    List<Expense> findByPaidByIdAndTripId(Long userId, Long tripId);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.trip.id = :tripId")
    BigDecimal getTotalExpensesByTripId(@Param("tripId") Long tripId);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.trip.id = :tripId AND e.category = :category")
    BigDecimal getTotalExpensesByCategory(@Param("tripId") Long tripId, @Param("category") ExpenseCategory category);
    
    @Query("SELECT e FROM Expense e WHERE e.trip.id = :tripId AND e.expenseDate BETWEEN :startDate AND :endDate ORDER BY e.expenseDate DESC")
    List<Expense> findExpensesByDateRange(@Param("tripId") Long tripId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
