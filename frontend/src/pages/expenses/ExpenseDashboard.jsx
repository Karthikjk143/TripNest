import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { expenseService, budgetService } from '../services/modules';
import { motion } from 'framer-motion';
import LoadingSpinner from '../components/ui/LoadingSpinner';
import { BarChart, Bar, PieChart, Pie, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const COLORS = ['#2563EB', '#38BDF8', '#F97316', '#EC4899', '#8B5CF6', '#06B6D4'];

const ExpenseDashboard = () => {
  const { tripId } = useParams();
  const [expenses, setExpenses] = useState([]);
  const [budget, setBudget] = useState(null);
  const [loading, setLoading] = useState(true);
  const [totalSpent, setTotalSpent] = useState(0);
  const [categoryData, setCategoryData] = useState([]);

  useEffect(() => {
    fetchData();
  }, [tripId]);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [expResponse, budgetResponse, totalResponse] = await Promise.all([
        expenseService.getByTrip(tripId),
        budgetService.getByTripId(tripId).catch(() => null),
        expenseService.getTotalByTrip(tripId),
      ]);

      setExpenses(expResponse.data.data);
      if (budgetResponse) setBudget(budgetResponse.data.data);
      setTotalSpent(totalResponse.data.data);

      // Process category data
      const categoryMap = {};
      expResponse.data.data.forEach((expense) => {
        categoryMap[expense.category] = (categoryMap[expense.category] || 0) + expense.amount;
      });
      setCategoryData(Object.entries(categoryMap).map(([name, value]) => ({ name, value })));
    } catch (error) {
      console.error('Error fetching expense data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner />;

  const budgetRemaining = budget ? budget.totalBudget - totalSpent : 0;
  const budgetPercentage = budget ? (totalSpent / budget.totalBudget) * 100 : 0;

  return (
    <div className="space-y-8">
      <motion.div
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        className="bg-gradient-to-r from-accent to-accent-light rounded-lg p-8 text-white"
      >
        <h1 className="text-4xl font-bold mb-2">Expense Tracker</h1>
        <p className="text-lg opacity-90">Manage and track your trip expenses</p>
      </motion.div>

      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="bg-white rounded-lg p-6 shadow-md border-l-4 border-primary"
        >
          <p className="text-text-muted mb-2">Total Spent</p>
          <p className="text-3xl font-bold text-primary">₹{totalSpent.toFixed(2)}</p>
        </motion.div>

        {budget && (
          <>
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.1 }}
              className="bg-white rounded-lg p-6 shadow-md border-l-4 border-secondary"
            >
              <p className="text-text-muted mb-2">Total Budget</p>
              <p className="text-3xl font-bold text-secondary">₹{budget.totalBudget.toFixed(2)}</p>
            </motion.div>

            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.2 }}
              className="bg-white rounded-lg p-6 shadow-md border-l-4 border-accent"
            >
              <p className="text-text-muted mb-2">Remaining</p>
              <p className={`text-3xl font-bold ${
                budgetRemaining >= 0 ? 'text-green-600' : 'text-red-600'
              }`}>
                ₹{budgetRemaining.toFixed(2)}
              </p>
            </motion.div>
          </>
        )}
      </div>

      {/* Budget Progress */}
      {budget && (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="bg-white rounded-lg p-6 shadow-md"
        >
          <h2 className="text-xl font-bold mb-4">Budget Progress</h2>
          <div className="w-full bg-gray-200 rounded-full h-4 overflow-hidden">
            <motion.div
              initial={{ width: 0 }}
              animate={{ width: `${Math.min(budgetPercentage, 100)}%` }}
              transition={{ duration: 1, ease: 'easeOut' }}
              className={`h-full transition-colors ${
                budgetPercentage > 100 ? 'bg-red-500' : 'bg-green-500'
              }`}
            />
          </div>
          <p className="text-sm text-text-muted mt-2">{budgetPercentage.toFixed(1)}% of budget used</p>
        </motion.div>
      )}

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {categoryData.length > 0 && (
          <motion.div
            initial={{ opacity: 0, scale: 0.95 }}
            animate={{ opacity: 1, scale: 1 }}
            className="bg-white rounded-lg p-6 shadow-md"
          >
            <h2 className="text-xl font-bold mb-4">Expenses by Category</h2>
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={categoryData}
                  cx="50%"
                  cy="50%"
                  labelLine={false}
                  label={({ name, value }) => `${name}: ₹${value}`}
                  outerRadius={80}
                  fill="#8884d8"
                  dataKey="value"
                >
                  {categoryData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip formatter={(value) => `₹${value.toFixed(2)}`} />
              </PieChart>
            </ResponsiveContainer>
          </motion.div>
        )}

        <motion.div
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.1 }}
          className="bg-white rounded-lg p-6 shadow-md"
        >
          <h2 className="text-xl font-bold mb-4">Recent Expenses</h2>
          <div className="space-y-3 max-h-80 overflow-y-auto">
            {expenses.slice(0, 10).map((expense, index) => (
              <motion.div
                key={expense.id}
                initial={{ opacity: 0, x: -20 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ delay: index * 0.05 }}
                className="flex justify-between items-center p-3 bg-gray-50 rounded-lg"
              >
                <div>
                  <p className="font-semibold">{expense.description}</p>
                  <p className="text-sm text-text-muted">{expense.category}</p>
                </div>
                <p className="font-bold text-accent">₹{expense.amount.toFixed(2)}</p>
              </motion.div>
            ))}
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default ExpenseDashboard;
