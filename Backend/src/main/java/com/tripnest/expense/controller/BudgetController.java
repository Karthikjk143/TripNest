package com.tripnest.expense.controller;

import com.tripnest.expense.dto.BudgetDTO;
import com.tripnest.expense.service.BudgetService;
import com.tripnest.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/budget")
@CrossOrigin(origins = "*")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BudgetDTO>> createBudget(@RequestBody BudgetDTO budgetDTO) {
        BudgetDTO created = budgetService.createBudget(budgetDTO);
        return ResponseEntity.ok(ApiResponse.success(created, "Budget created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BudgetDTO>> getBudgetById(@PathVariable Long id) {
        BudgetDTO budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(ApiResponse.success(budget, "Budget retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<ApiResponse<BudgetDTO>> getBudgetByTripId(@PathVariable Long tripId) {
        BudgetDTO budget = budgetService.getBudgetByTripId(tripId);
        return ResponseEntity.ok(ApiResponse.success(budget, "Budget retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BudgetDTO>> updateBudget(@PathVariable Long id, @RequestBody BudgetDTO budgetDTO) {
        BudgetDTO updated = budgetService.updateBudget(id, budgetDTO);
        return ResponseEntity.ok(ApiResponse.success(updated, "Budget updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Budget deleted successfully"));
    }
}
