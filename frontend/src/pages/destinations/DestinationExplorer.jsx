import React, { useState, useEffect } from 'react';
import { destinationService } from '../services/modules';
import { motion } from 'framer-motion';
import LoadingSpinner from '../components/ui/LoadingSpinner';

const DestinationExplorer = () => {
  const [destinations, setDestinations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCountry, setSelectedCountry] = useState('All');

  useEffect(() => {
    fetchDestinations();
  }, []);

  const fetchDestinations = async () => {
    try {
      setLoading(true);
      const response = await destinationService.getAll();
      setDestinations(response.data.data);
    } catch (error) {
      console.error('Error fetching destinations:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchQuery.trim()) {
      fetchDestinations();
      return;
    }
    try {
      setLoading(true);
      const response = await destinationService.search(searchQuery);
      setDestinations(response.data.data);
    } catch (error) {
      console.error('Error searching destinations:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCountryFilter = async (country) => {
    setSelectedCountry(country);
    if (country === 'All') {
      fetchDestinations();
      return;
    }
    try {
      setLoading(true);
      const response = await destinationService.getByCountry(country);
      setDestinations(response.data.data);
    } catch (error) {
      console.error('Error filtering destinations:', error);
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
        <h1 className="text-4xl font-bold mb-4">Discover Destinations</h1>
        <p className="text-lg opacity-90">Explore amazing travel destinations around the world</p>
      </motion.div>

      <form onSubmit={handleSearch} className="flex gap-3 mb-6">
        <input
          type="text"
          placeholder="Search destinations..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          className="flex-1 px-4 py-3 border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
        />
        <button
          type="submit"
          className="px-6 py-3 bg-primary text-white rounded-lg hover:bg-primary-dark transition-colors"
        >
          Search
        </button>
      </form>

      <div className="flex gap-2 flex-wrap mb-6">
        {['All', 'India', 'Thailand', 'Japan', 'Australia'].map((country) => (
          <button
            key={country}
            onClick={() => handleCountryFilter(country)}
            className={`px-4 py-2 rounded-lg transition-colors ${
              selectedCountry === country
                ? 'bg-primary text-white'
                : 'bg-gray-200 text-gray-800 hover:bg-gray-300'
            }`}
          >
            {country}
          </button>
        ))}
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {destinations.map((destination, index) => (
          <motion.div
            key={destination.id}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: index * 0.1 }}
            className="bg-white rounded-lg overflow-hidden shadow-lg hover:shadow-xl transition-shadow"
          >
            {destination.imageUrl && (
              <img
                src={destination.imageUrl}
                alt={destination.name}
                className="w-full h-48 object-cover"
              />
            )}
            <div className="p-4">
              <h3 className="text-xl font-bold text-text mb-2">{destination.name}</h3>
              <p className="text-sm text-text-muted mb-2">
                {destination.country}, {destination.region}
              </p>
              <p className="text-text-secondary text-sm mb-4 line-clamp-3">
                {destination.description}
              </p>
              <div className="flex justify-between items-center text-xs text-text-muted">
                <span>📍 Best time: {destination.bestTimeToVisit}</span>
                <span className="bg-accent text-white px-2 py-1 rounded-full">Popularity: {destination.popularityScore}</span>
              </div>
            </div>
          </motion.div>
        ))}
      </div>

      {destinations.length === 0 && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="text-center py-12"
        >
          <p className="text-text-muted text-lg">No destinations found</p>
        </motion.div>
      )}
    </div>
  );
};

export default DestinationExplorer;
