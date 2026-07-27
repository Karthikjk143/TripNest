package com.tripnest.expense.service;

import com.tripnest.expense.dto.BudgetDTO;
import com.tripnest.expense.entity.Budget;
import com.tripnest.expense.repository.BudgetRepository;
import com.tripnest.trip.entity.Trip;
import com.tripnest.trip.repository.TripRepository;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final TripRepository tripRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository, TripRepository tripRepository) {
        this.budgetRepository = budgetRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        Trip trip = tripRepository.findById(budgetDTO.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        
        Budget budget = mapToEntity(budgetDTO, trip);
        Budget savedBudget = budgetRepository.save(budget);
        return mapToDTO(savedBudget);
    }

    @Override
    public BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        
        budget.setTotalBudget(budgetDTO.getTotalBudget());
        budget.setTransportationBudget(budgetDTO.getTransportationBudget());
        budget.setAccommodationBudget(budgetDTO.getAccommodationBudget());
        budget.setFoodBudget(budgetDTO.getFoodBudget());
        budget.setShoppingBudget(budgetDTO.getShoppingBudget());
        budget.setEntertainmentBudget(budgetDTO.getEntertainmentBudget());
        budget.setActivitiesBudget(budgetDTO.getActivitiesBudget());
        budget.setMiscellaneousBudget(budgetDTO.getMiscellaneousBudget());
        budget.setNotes(budgetDTO.getNotes());
        
        Budget updatedBudget = budgetRepository.save(budget);
        return mapToDTO(updatedBudget);
    }

    @Override
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    @Override
    public BudgetDTO getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        return mapToDTO(budget);
    }

    @Override
    public BudgetDTO getBudgetByTripId(Long tripId) {
        Budget budget = budgetRepository.findByTripId(tripId)
                .orElseThrow(() -> new RuntimeException("Budget not found for trip"));
        return mapToDTO(budget);
    }

    private BudgetDTO mapToDTO(Budget budget) {
        return BudgetDTO.builder()
                .id(budget.getId())
                .tripId(budget.getTrip().getId())
                .totalBudget(budget.getTotalBudget())
                .transportationBudget(budget.getTransportationBudget())
                .accommodationBudget(budget.getAccommodationBudget())
                .foodBudget(budget.getFoodBudget())
                .shoppingBudget(budget.getShoppingBudget())
                .entertainmentBudget(budget.getEntertainmentBudget())
                .activitiesBudget(budget.getActivitiesBudget())
                .miscellaneousBudget(budget.getMiscellaneousBudget())
                .notes(budget.getNotes())
                .build();
    }

    private Budget mapToEntity(BudgetDTO dto, Trip trip) {
        return Budget.builder()
                .trip(trip)
                .totalBudget(dto.getTotalBudget())
                .transportationBudget(dto.getTransportationBudget())
                .accommodationBudget(dto.getAccommodationBudget())
                .foodBudget(dto.getFoodBudget())
                .shoppingBudget(dto.getShoppingBudget())
                .entertainmentBudget(dto.getEntertainmentBudget())
                .activitiesBudget(dto.getActivitiesBudget())
                .miscellaneousBudget(dto.getMiscellaneousBudget())
                .notes(dto.getNotes())
                .build();
    }
}
