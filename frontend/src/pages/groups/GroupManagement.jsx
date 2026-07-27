import React, { useState, useEffect } from 'react';
import { groupService } from '../services/modules';
import { motion } from 'framer-motion';
import LoadingSpinner from '../components/ui/LoadingSpinner';

const GroupManagement = () => {
  const [groups, setGroups] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [formData, setFormData] = useState({ groupName: '', description: '' });
  const userId = localStorage.getItem('userId'); // Assuming userId is stored in localStorage

  useEffect(() => {
    fetchGroups();
  }, []);

  const fetchGroups = async () => {
    try {
      setLoading(true);
      const response = await groupService.getByUser(userId);
      setGroups(response.data.data);
    } catch (error) {
      console.error('Error fetching groups:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateGroup = async (e) => {
    e.preventDefault();
    try {
      await groupService.create({
        ...formData,
        createdByUserId: userId,
      });
      setFormData({ groupName: '', description: '' });
      setShowCreateForm(false);
      fetchGroups();
    } catch (error) {
      console.error('Error creating group:', error);
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div className="space-y-8">
      <motion.div
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        className="bg-gradient-to-r from-accent to-accent-light rounded-lg p-8 text-white"
      >
        <h1 className="text-4xl font-bold mb-2">Travel Groups</h1>
        <p className="text-lg opacity-90">Collaborate with friends on shared trips</p>
      </motion.div>

      {showCreateForm && (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="bg-white rounded-lg p-6 shadow-md border border-border"
        >
          <form onSubmit={handleCreateGroup} className="space-y-4">
            <input
              type="text"
              placeholder="Group Name"
              value={formData.groupName}
              onChange={(e) => setFormData({ ...formData, groupName: e.target.value })}
              className="w-full px-4 py-2 border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
              required
            />
            <textarea
              placeholder="Description"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              className="w-full px-4 py-2 border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
              rows="3"
            />
            <div className="flex gap-3">
              <button
                type="submit"
                className="px-6 py-2 bg-primary text-white rounded-lg hover:bg-primary-dark transition-colors"
              >
                Create Group
              </button>
              <button
                type="button"
                onClick={() => setShowCreateForm(false)}
                className="px-6 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition-colors"
              >
                Cancel
              </button>
            </div>
          </form>
        </motion.div>
      )}

      <button
        onClick={() => setShowCreateForm(!showCreateForm)}
        className="px-6 py-3 bg-primary text-white rounded-lg hover:bg-primary-dark transition-colors font-semibold"
      >
        + Create New Group
      </button>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {groups.map((group, index) => (
          <motion.div
            key={group.id}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: index * 0.1 }}
            className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow"
          >
            <h3 className="text-xl font-bold mb-2">{group.groupName}</h3>
            <p className="text-text-secondary mb-4 line-clamp-2">{group.description}</p>
            
            <div className="space-y-3 mb-4 text-sm">
              <div className="flex items-center gap-2">
                <span className="text-xl">👥</span>
                <span className="text-text-muted">{group.memberCount} members</span>
              </div>
              <div className="flex items-center gap-2">
                <span className="text-xl">🗺️</span>
                <span className="text-text-muted">{group.sharedTripIds?.length || 0} shared trips</span>
              </div>
              <div className="flex items-center gap-2">
                <span className="text-xl">👤</span>
                <span className="text-text-muted">by {group.createdByUserName}</span>
              </div>
            </div>

            <button className="w-full px-4 py-2 bg-primary text-white rounded-lg hover:bg-primary-dark transition-colors">
              View Group
            </button>
          </motion.div>
        ))}
      </div>

      {groups.length === 0 && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="text-center py-12 bg-gray-50 rounded-lg"
        >
          <p className="text-text-muted text-lg">No groups yet</p>
          <p className="text-text-muted text-sm">Create a group to collaborate with friends on trips</p>
        </motion.div>
      )}
    </div>
  );
};

export default GroupManagement;
