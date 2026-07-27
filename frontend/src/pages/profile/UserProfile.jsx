import React, { useState, useEffect } from 'react';
import { userProfileService } from '../services/modules';
import { motion } from 'framer-motion';
import LoadingSpinner from '../components/ui/LoadingSpinner';

const UserProfile = () => {
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({});
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      setLoading(true);
      const response = await userProfileService.getProfile(userId);
      setProfile(response.data.data);
      setFormData(response.data.data);
    } catch (error) {
      console.error('Error fetching profile:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateProfile = async (e) => {
    e.preventDefault();
    try {
      await userProfileService.updateProfile(userId, formData);
      setProfile(formData);
      setIsEditing(false);
    } catch (error) {
      console.error('Error updating profile:', error);
    }
  };

  if (loading) return <LoadingSpinner />;
  if (!profile) return <div>Profile not found</div>;

  return (
    <div className="space-y-8">
      <motion.div
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        className="bg-gradient-to-r from-primary to-secondary rounded-lg p-8 text-white"
      >
        <h1 className="text-4xl font-bold mb-2">My Profile</h1>
        <p className="text-lg opacity-90">Manage your account information</p>
      </motion.div>

      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="bg-white rounded-lg shadow-md p-8"
      >
        {!isEditing ? (
          <div>
            <div className="flex justify-between items-start mb-6">
              <div className="flex gap-6">
                {profile.profilePictureUrl && (
                  <img
                    src={profile.profilePictureUrl}
                    alt={profile.username}
                    className="w-24 h-24 rounded-full object-cover"
                  />
                )}
                <div>
                  <h2 className="text-3xl font-bold">{profile.firstName} {profile.lastName}</h2>
                  <p className="text-lg text-text-muted">@{profile.username}</p>
                  <p className="text-text-secondary mt-2">{profile.bio}</p>
                </div>
              </div>
              <button
                onClick={() => setIsEditing(true)}
                className="px-6 py-2 bg-primary text-white rounded-lg hover:bg-primary-dark transition-colors"
              >
                Edit Profile
              </button>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-8">
              <div>
                <p className="text-sm text-text-muted mb-1">Email</p>
                <p className="text-lg font-semibold">{profile.email}</p>
              </div>
              <div>
                <p className="text-sm text-text-muted mb-1">Phone</p>
                <p className="text-lg font-semibold">{profile.phone || 'Not provided'}</p>
              </div>
              <div>
                <p className="text-sm text-text-muted mb-1">Location</p>
                <p className="text-lg font-semibold">{profile.city}, {profile.country}</p>
              </div>
              <div>
                <p className="text-sm text-text-muted mb-1">Timezone</p>
                <p className="text-lg font-semibold">{profile.timezone || 'Not set'}</p>
              </div>
              <div>
                <p className="text-sm text-text-muted mb-1">Language</p>
                <p className="text-lg font-semibold">{profile.preferredLanguage || 'English'}</p>
              </div>
              <div>
                <p className="text-sm text-text-muted mb-1">Notifications</p>
                <p className="text-lg font-semibold">{profile.notificationsEnabled ? '✅ Enabled' : '❌ Disabled'}</p>
              </div>
            </div>
          </div>
        ) : (
          <form onSubmit={handleUpdateProfile} className="space-y-4">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <input
                type="text"
                placeholder="First Name"
                value={formData.firstName || ''}
                onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
                className="px-4 py-2 border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
              />
              <input
                type="text"
                placeholder="Last Name"
                value={formData.lastName || ''}
                onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
                className="px-4 py-2 border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
              />
              <input
                type="email"
                placeholder="Email"
                value={formData.email || ''}
                disabled
                className="px-4 py-2 border border-border rounded-lg bg-gray-100"
              />
              <input
                type="tel"
                placeholder="Phone"
                value={formData.phone || ''}
                onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
                className="px-4 py-2 border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
              />
              <input
                type="text"
                placeholder="City"
                value={formData.city || ''}
                onChange={(e) => setFormData({ ...formData, city: e.target.value })}
                className="px-4 py-2 border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
              />
              <input
                type="text"
                placeholder="Country"
                value={formData.country || ''}
                onChange={(e) => setFormData({ ...formData, country: e.target.value })}
                className="px-4 py-2 border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
              />
            </div>
            <textarea
              placeholder="Bio"
              value={formData.bio || ''}
              onChange={(e) => setFormData({ ...formData, bio: e.target.value })}
              className="w-full px-4 py-2 border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
              rows="4"
            />
            <div className="flex gap-3">
              <button
                type="submit"
                className="px-6 py-2 bg-primary text-white rounded-lg hover:bg-primary-dark transition-colors"
              >
                Save Changes
              </button>
              <button
                type="button"
                onClick={() => setIsEditing(false)}
                className="px-6 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition-colors"
              >
                Cancel
              </button>
            </div>
          </form>
        )}
      </motion.div>
    </div>
  );
};

export default UserProfile;
