package com.tripnest.expense.controller;

import com.tripnest.expense.dto.ExpenseDTO;
import com.tripnest.expense.service.ExpenseService;
import com.tripnest.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseDTO>> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO created = expenseService.createExpense(expenseDTO);
        return ResponseEntity.ok(ApiResponse.success(created, "Expense created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDTO>> getExpenseById(@PathVariable Long id) {
        ExpenseDTO expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(ApiResponse.success(expense, "Expense retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<ApiResponse<List<ExpenseDTO>>> getExpensesByTrip(@PathVariable Long tripId) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByTrip(tripId);
        return ResponseEntity.ok(ApiResponse.success(expenses, "Expenses retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}/total")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalExpensesByTrip(@PathVariable Long tripId) {
        BigDecimal total = expenseService.getTotalExpensesByTrip(tripId);
        return ResponseEntity.ok(ApiResponse.success(total, "Total expenses retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}/category/{category}")
    public ResponseEntity<ApiResponse<List<ExpenseDTO>>> getExpensesByCategory(@PathVariable Long tripId, @PathVariable String category) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByCategory(tripId, category);
        return ResponseEntity.ok(ApiResponse.success(expenses, "Expenses retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}/range")
    public ResponseEntity<ApiResponse<List<ExpenseDTO>>> getExpensesByDateRange(@PathVariable Long tripId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByDateRange(tripId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(expenses, "Expenses retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDTO>> updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO updated = expenseService.updateExpense(id, expenseDTO);
        return ResponseEntity.ok(ApiResponse.success(updated, "Expense updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Expense deleted successfully"));
    }
}
