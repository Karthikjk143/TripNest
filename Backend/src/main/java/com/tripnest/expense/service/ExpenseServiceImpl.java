package com.tripnest.expense.service;

import com.tripnest.expense.dto.ExpenseDTO;
import com.tripnest.expense.entity.Expense;
import com.tripnest.expense.entity.ExpenseCategory;
import com.tripnest.expense.repository.ExpenseRepository;
import com.tripnest.trip.entity.Trip;
import com.tripnest.trip.repository.TripRepository;
import com.tripnest.user.entity.User;
import com.tripnest.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, TripRepository tripRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        Trip trip = tripRepository.findById(expenseDTO.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        User user = userRepository.findById(expenseDTO.getPaidByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Expense expense = Expense.builder()
                .trip(trip)
                .paidBy(user)
                .description(expenseDTO.getDescription())
                .category(expenseDTO.getCategory())
                .amount(expenseDTO.getAmount())
                .expenseDate(expenseDTO.getExpenseDate())
                .notes(expenseDTO.getNotes())
                .status("PENDING")
                .build();
        
        Expense savedExpense = expenseRepository.save(expense);
        return mapToDTO(savedExpense);
    }

    @Override
    public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        
        expense.setDescription(expenseDTO.getDescription());
        expense.setCategory(expenseDTO.getCategory());
        expense.setAmount(expenseDTO.getAmount());
        expense.setExpenseDate(expenseDTO.getExpenseDate());
        expense.setNotes(expenseDTO.getNotes());
        
        Expense updatedExpense = expenseRepository.save(expense);
        return mapToDTO(updatedExpense);
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return mapToDTO(expense);
    }

    @Override
    public List<ExpenseDTO> getExpensesByTrip(Long tripId) {
        return expenseRepository.findByTripIdOrderByExpenseDateDesc(tripId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalExpensesByTrip(Long tripId) {
        BigDecimal total = expenseRepository.getTotalExpensesByTripId(tripId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public List<ExpenseDTO> getExpensesByCategory(Long tripId, String category) {
        return expenseRepository.findByTripIdAndCategory(tripId, ExpenseCategory.valueOf(category)).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> getExpensesByDateRange(Long tripId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findExpensesByDateRange(tripId, startDate, endDate).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ExpenseDTO mapToDTO(Expense expense) {
        return ExpenseDTO.builder()
                .id(expense.getId())
                .tripId(expense.getTrip().getId())
                .paidByUserId(expense.getPaidBy().getId())
                .paidByUserName(expense.getPaidBy().getUsername())
                .description(expense.getDescription())
                .category(expense.getCategory())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .notes(expense.getNotes())
                .status(expense.getStatus())
                .build();
    }
}
