import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { destinationService, activityService } from '../services/modules';
import { motion } from 'framer-motion';
import LoadingSpinner from '../components/ui/LoadingSpinner';

const DestinationDetails = () => {
  const { id } = useParams();
  const [destination, setDestination] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDestinationDetails();
  }, [id]);

  const fetchDestinationDetails = async () => {
    try {
      setLoading(true);
      const response = await destinationService.getById(id);
      setDestination(response.data.data);
    } catch (error) {
      console.error('Error fetching destination:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner />;
  if (!destination) return <div>Destination not found</div>;

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="space-y-8"
    >
      {destination.imageUrl && (
        <img
          src={destination.imageUrl}
          alt={destination.name}
          className="w-full h-96 object-cover rounded-lg"
        />
      )}

      <div>
        <h1 className="text-4xl font-bold mb-2">{destination.name}</h1>
        <p className="text-lg text-text-secondary mb-4">
          {destination.country} • {destination.region}
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div>
          <h2 className="text-2xl font-bold mb-4">About</h2>
          <p className="text-text-secondary leading-relaxed mb-6">{destination.description}</p>
          
          <div className="space-y-4">
            <div>
              <h3 className="font-semibold text-lg mb-2">Main Attractions</h3>
              <p className="text-text-secondary">{destination.attractions}</p>
            </div>
            
            <div>
              <h3 className="font-semibold text-lg mb-2">Best Time to Visit</h3>
              <p className="text-text-secondary">{destination.bestTimeToVisit}</p>
            </div>
          </div>
        </div>

        <div className="bg-gradient-to-br from-primary to-secondary rounded-lg p-6 text-white">
          <h2 className="text-2xl font-bold mb-4">Travel Information</h2>
          <div className="space-y-4">
            <div className="flex justify-between items-center border-b border-white border-opacity-20 pb-4">
              <span>Latitude</span>
              <span className="font-mono">{destination.latitude?.toFixed(4)}</span>
            </div>
            <div className="flex justify-between items-center border-b border-white border-opacity-20 pb-4">
              <span>Longitude</span>
              <span className="font-mono">{destination.longitude?.toFixed(4)}</span>
            </div>
            <div className="flex justify-between items-center">
              <span>Popularity Score</span>
              <span className="text-2xl font-bold">{destination.popularityScore}%</span>
            </div>
          </div>
        </div>
      </div>
    </motion.div>
  );
};

export default DestinationDetails;
