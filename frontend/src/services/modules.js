import apiClient from './apiClient';

export const destinationService = {
  getAll: () => apiClient.get('/destinations'),
  getById: (id) => apiClient.get(`/destinations/${id}`),
  search: (query) => apiClient.get(`/destinations/search?query=${query}`),
  getByCountry: (country) => apiClient.get(`/destinations/country/${country}`),
  getTop: () => apiClient.get('/destinations/top'),
  create: (data) => apiClient.post('/destinations', data),
  update: (id, data) => apiClient.put(`/destinations/${id}`, data),
  delete: (id) => apiClient.delete(`/destinations/${id}`),
};

export const activityService = {
  create: (data) => apiClient.post('/activities', data),
  getById: (id) => apiClient.get(`/activities/${id}`),
  getByItineraryDay: (itineraryDayId) => apiClient.get(`/activities/itinerary-day/${itineraryDayId}`),
  getByTrip: (tripId) => apiClient.get(`/activities/trip/${tripId}`),
  update: (id, data) => apiClient.put(`/activities/${id}`, data),
  delete: (id) => apiClient.delete(`/activities/${id}`),
};

export const itineraryService = {
  create: (data) => apiClient.post('/itinerary', data),
  getById: (id) => apiClient.get(`/itinerary/${id}`),
  getByTrip: (tripId) => apiClient.get(`/itinerary/trip/${tripId}`),
  getByTripAndDate: (tripId, date) => apiClient.get(`/itinerary/trip/${tripId}/date?date=${date}`),
  update: (id, data) => apiClient.put(`/itinerary/${id}`, data),
  delete: (id) => apiClient.delete(`/itinerary/${id}`),
};

export const expenseService = {
  create: (data) => apiClient.post('/expenses', data),
  getById: (id) => apiClient.get(`/expenses/${id}`),
  getByTrip: (tripId) => apiClient.get(`/expenses/trip/${tripId}`),
  getTotalByTrip: (tripId) => apiClient.get(`/expenses/trip/${tripId}/total`),
  getByCategory: (tripId, category) => apiClient.get(`/expenses/trip/${tripId}/category/${category}`),
  getByDateRange: (tripId, startDate, endDate) => apiClient.get(`/expenses/trip/${tripId}/range?startDate=${startDate}&endDate=${endDate}`),
  update: (id, data) => apiClient.put(`/expenses/${id}`, data),
  delete: (id) => apiClient.delete(`/expenses/${id}`),
};

export const budgetService = {
  create: (data) => apiClient.post('/budget', data),
  getById: (id) => apiClient.get(`/budget/${id}`),
  getByTripId: (tripId) => apiClient.get(`/budget/trip/${tripId}`),
  update: (id, data) => apiClient.put(`/budget/${id}`, data),
  delete: (id) => apiClient.delete(`/budget/${id}`),
};

export const documentService = {
  upload: (data) => apiClient.post('/documents', data),
  getById: (id) => apiClient.get(`/documents/${id}`),
  getByTrip: (tripId) => apiClient.get(`/documents/trip/${tripId}`),
  getByType: (tripId, documentType) => apiClient.get(`/documents/trip/${tripId}/type/${documentType}`),
  getByUser: (tripId, userId) => apiClient.get(`/documents/trip/${tripId}/user/${userId}`),
  update: (id, data) => apiClient.put(`/documents/${id}`, data),
  delete: (id) => apiClient.delete(`/documents/${id}`),
};

export const groupService = {
  create: (data) => apiClient.post('/groups', data),
  getById: (id) => apiClient.get(`/groups/${id}`),
  getByUser: (userId) => apiClient.get(`/groups/user/${userId}`),
  search: (query) => apiClient.get(`/groups/search?query=${query}`),
  update: (id, data) => apiClient.put(`/groups/${id}`, data),
  addMember: (groupId, userId) => apiClient.post(`/groups/${groupId}/members/${userId}`),
  removeMember: (groupId, userId) => apiClient.delete(`/groups/${groupId}/members/${userId}`),
  addTrip: (groupId, tripId) => apiClient.post(`/groups/${groupId}/trips/${tripId}`),
  removeTrip: (groupId, tripId) => apiClient.delete(`/groups/${groupId}/trips/${tripId}`),
  delete: (id) => apiClient.delete(`/groups/${id}`),
};

export const notificationService = {
  create: (data) => apiClient.post('/notifications', data),
  getById: (id) => apiClient.get(`/notifications/${id}`),
  getByUser: (userId) => apiClient.get(`/notifications/user/${userId}`),
  getUnread: (userId) => apiClient.get(`/notifications/user/${userId}/unread`),
  getUnreadCount: (userId) => apiClient.get(`/notifications/user/${userId}/unread-count`),
  markAsRead: (id) => apiClient.put(`/notifications/${id}/read`),
  markAllAsRead: (userId) => apiClient.put(`/notifications/user/${userId}/mark-all-read`),
  delete: (id) => apiClient.delete(`/notifications/${id}`),
};

export const userProfileService = {
  getProfile: (userId) => apiClient.get(`/profile/${userId}`),
  getByEmail: (email) => apiClient.get(`/profile/email/${email}`),
  getByUsername: (username) => apiClient.get(`/profile/username/${username}`),
  updateProfile: (userId, data) => apiClient.put(`/profile/${userId}`, data),
};
