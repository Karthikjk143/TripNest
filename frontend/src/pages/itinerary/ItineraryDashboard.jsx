import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { itineraryService, activityService } from '../services/modules';
import { motion } from 'framer-motion';
import LoadingSpinner from '../components/ui/LoadingSpinner';

const ItineraryDashboard = () => {
  const { tripId } = useParams();
  const [itinerary, setItinerary] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedDay, setSelectedDay] = useState(null);
  const [showActivityForm, setShowActivityForm] = useState(false);

  useEffect(() => {
    fetchItinerary();
  }, [tripId]);

  const fetchItinerary = async () => {
    try {
      setLoading(true);
      const response = await itineraryService.getByTrip(tripId);
      setItinerary(response.data.data);
      if (response.data.data.length > 0) {
        setSelectedDay(response.data.data[0]);
      }
    } catch (error) {
      console.error('Error fetching itinerary:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div className="space-y-8">
      <motion.div
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        className="bg-gradient-to-r from-primary to-secondary rounded-lg p-8 text-white"
      >
        <h1 className="text-4xl font-bold mb-2">Trip Itinerary</h1>
        <p className="text-lg opacity-90">Plan your activities day by day</p>
      </motion.div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Days Sidebar */}
        <div className="lg:col-span-1 space-y-2">
          <h2 className="text-xl font-bold mb-4">Days</h2>
          {itinerary.map((day, index) => (
            <motion.button
              key={day.id}
              onClick={() => setSelectedDay(day)}
              initial={{ opacity: 0, x: -20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: index * 0.1 }}
              className={`w-full p-4 text-left rounded-lg transition-all ${
                selectedDay?.id === day.id
                  ? 'bg-primary text-white shadow-lg'
                  : 'bg-gray-100 hover:bg-gray-200'
              }`}
            >
              <p className="font-semibold">Day {day.dayNumber}</p>
              <p className="text-sm opacity-75">{new Date(day.date).toLocaleDateString()}</p>
            </motion.button>
          ))}
        </div>

        {/* Activities */}
        <div className="lg:col-span-2">
          {selectedDay && (
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              className="space-y-4"
            >
              <div className="flex justify-between items-center">
                <h2 className="text-2xl font-bold">
                  Day {selectedDay.dayNumber} - {new Date(selectedDay.date).toLocaleDateString()}
                </h2>
                <button
                  onClick={() => setShowActivityForm(!showActivityForm)}
                  className="px-4 py-2 bg-primary text-white rounded-lg hover:bg-primary-dark transition-colors"
                >
                  + Add Activity
                </button>
              </div>

              {showActivityForm && (
                <div className="bg-gray-50 p-4 rounded-lg border border-border">
                  <p className="text-text-muted">Activity form coming soon...</p>
                </div>
              )}

              <div className="space-y-3">
                {selectedDay.activities?.length > 0 ? (
                  selectedDay.activities.map((activity, index) => (
                    <motion.div
                      key={activity.id}
                      initial={{ opacity: 0, x: -20 }}
                      animate={{ opacity: 1, x: 0 }}
                      transition={{ delay: index * 0.1 }}
                      className="bg-white p-4 rounded-lg border-l-4 border-primary shadow-sm hover:shadow-md transition-shadow"
                    >
                      <div className="flex justify-between items-start">
                        <div>
                          <h3 className="font-semibold text-lg">{activity.title}</h3>
                          <p className="text-sm text-text-muted">
                            {activity.type} • {activity.startTime} - {activity.endTime}
                          </p>
                          <p className="text-text-secondary mt-2">{activity.description}</p>
                          {activity.location && (
                            <p className="text-sm mt-2">📍 {activity.location}</p>
                          )}
                        </div>
                        {activity.estimatedCost && (
                          <div className="text-right">
                            <p className="text-lg font-bold text-accent">₹{activity.estimatedCost}</p>
                          </div>
                        )}
                      </div>
                    </motion.div>
                  ))
                ) : (
                  <p className="text-text-muted text-center py-6">No activities planned for this day</p>
                )}
              </div>

              {selectedDay.notes && (
                <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
                  <p className="font-semibold text-yellow-900 mb-2">Notes</p>
                  <p className="text-yellow-800">{selectedDay.notes}</p>
                </div>
              )}
            </motion.div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ItineraryDashboard;
