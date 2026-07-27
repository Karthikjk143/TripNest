import React, { useState, useEffect } from 'react';
import { notificationService } from '../services/modules';
import { motion } from 'framer-motion';
import LoadingSpinner from '../components/ui/LoadingSpinner';

const NotificationCenter = () => {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('all'); // 'all' or 'unread'
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    fetchNotifications();
    const interval = setInterval(fetchNotifications, 30000); // Refresh every 30 seconds
    return () => clearInterval(interval);
  }, []);

  const fetchNotifications = async () => {
    try {
      setLoading(true);
      const response = filter === 'unread'
        ? await notificationService.getUnread(userId)
        : await notificationService.getByUser(userId);
      setNotifications(response.data.data);
    } catch (error) {
      console.error('Error fetching notifications:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleMarkAsRead = async (id) => {
    try {
      await notificationService.markAsRead(id);
      fetchNotifications();
    } catch (error) {
      console.error('Error marking notification as read:', error);
    }
  };

  const handleMarkAllAsRead = async () => {
    try {
      await notificationService.markAllAsRead(userId);
      fetchNotifications();
    } catch (error) {
      console.error('Error marking all as read:', error);
    }
  };

  const getNotificationIcon = (type) => {
    const iconMap = {
      TRIP_REMINDER: '🗓️',
      ACTIVITY_REMINDER: '⏰',
      BUDGET_ALERT: '💰',
      GROUP_INVITATION: '👥',
      EXPENSE_ADDED: '💳',
      TRIP_UPDATE: '✈️',
      EXPENSE_SPLIT: '🔄',
      DOCUMENT_SHARED: '📄',
      SYSTEM_NOTIFICATION: 'ℹ️',
    };
    return iconMap[type] || '📢';
  };

  if (loading) return <LoadingSpinner />;

  const unreadCount = notifications.filter(n => !n.isRead).length;

  return (
    <div className="space-y-8">
      <motion.div
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        className="bg-gradient-to-r from-primary to-secondary rounded-lg p-8 text-white"
      >
        <h1 className="text-4xl font-bold mb-2">Notifications</h1>
        <p className="text-lg opacity-90">Stay updated with your trip activities</p>
      </motion.div>

      <div className="flex justify-between items-center">
        <div className="flex gap-2">
          <button
            onClick={() => setFilter('all')}
            className={`px-4 py-2 rounded-lg transition-colors ${
              filter === 'all'
                ? 'bg-primary text-white'
                : 'bg-gray-200 text-gray-800 hover:bg-gray-300'
            }`}
          >
            All ({notifications.length})
          </button>
          <button
            onClick={() => setFilter('unread')}
            className={`px-4 py-2 rounded-lg transition-colors ${
              filter === 'unread'
                ? 'bg-primary text-white'
                : 'bg-gray-200 text-gray-800 hover:bg-gray-300'
            }`}
          >
            Unread ({unreadCount})
          </button>
        </div>
        {unreadCount > 0 && (
          <button
            onClick={handleMarkAllAsRead}
            className="px-4 py-2 text-primary hover:text-primary-dark transition-colors font-semibold"
          >
            Mark all as read
          </button>
        )}
      </div>

      <div className="space-y-3">
        {notifications.map((notification, index) => (
          <motion.div
            key={notification.id}
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ delay: index * 0.05 }}
            onClick={() => !notification.isRead && handleMarkAsRead(notification.id)}
            className={`p-4 rounded-lg border-l-4 cursor-pointer transition-all ${
              notification.isRead
                ? 'bg-gray-50 border-gray-300'
                : 'bg-blue-50 border-primary hover:bg-blue-100'
            }`}
          >
            <div className="flex gap-4">
              <span className="text-3xl">{getNotificationIcon(notification.type)}</span>
              <div className="flex-1">
                <h3 className="font-semibold text-lg">{notification.title}</h3>
                <p className="text-text-secondary">{notification.message}</p>
                <p className="text-xs text-text-muted mt-2">
                  {new Date(notification.createdAt).toLocaleString()}
                </p>
              </div>
              {!notification.isRead && (
                <div className="w-3 h-3 bg-primary rounded-full mt-2"></div>
              )}
            </div>
          </motion.div>
        ))}
      </div>

      {notifications.length === 0 && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="text-center py-12 bg-gray-50 rounded-lg"
        >
          <p className="text-text-muted text-lg">No notifications yet</p>
          <p className="text-text-muted text-sm">Stay tuned for updates on your trips</p>
        </motion.div>
      )}
    </div>
  );
};

export default NotificationCenter;
