import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { documentService } from '../services/modules';
import { motion } from 'framer-motion';
import LoadingSpinner from '../components/ui/LoadingSpinner';

const DocumentManager = () => {
  const { tripId } = useParams();
  const [documents, setDocuments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedType, setSelectedType] = useState('All');

  const documentTypes = ['All', 'PASSPORT', 'VISA', 'TICKET', 'INSURANCE', 'HOTEL_BOOKING', 'TRAVEL_GUIDE', 'ITINERARY', 'OTHER'];

  useEffect(() => {
    fetchDocuments();
  }, [tripId]);

  const fetchDocuments = async () => {
    try {
      setLoading(true);
      const response = await documentService.getByTrip(tripId);
      setDocuments(response.data.data);
    } catch (error) {
      console.error('Error fetching documents:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleTypeFilter = async (type) => {
    setSelectedType(type);
    if (type === 'All') {
      fetchDocuments();
      return;
    }
    try {
      setLoading(true);
      const response = await documentService.getByType(tripId, type);
      setDocuments(response.data.data);
    } catch (error) {
      console.error('Error filtering documents:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner />;

  const getFileIcon = (mimeType) => {
    if (mimeType.includes('pdf')) return '📄';
    if (mimeType.includes('image')) return '🖼️';
    if (mimeType.includes('word')) return '📝';
    return '📎';
  };

  return (
    <div className="space-y-8">
      <motion.div
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        className="bg-gradient-to-r from-secondary to-secondary-light rounded-lg p-8 text-white"
      >
        <h1 className="text-4xl font-bold mb-2">Document Manager</h1>
        <p className="text-lg opacity-90">Store and organize your travel documents</p>
      </motion.div>

      <div className="flex gap-2 flex-wrap">
        {documentTypes.map((type) => (
          <button
            key={type}
            onClick={() => handleTypeFilter(type)}
            className={`px-4 py-2 rounded-lg transition-colors ${
              selectedType === type
                ? 'bg-secondary text-white'
                : 'bg-gray-200 text-gray-800 hover:bg-gray-300'
            }`}
          >
            {type}
          </button>
        ))}
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {documents.map((doc, index) => (
          <motion.div
            key={doc.id}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: index * 0.1 }}
            className="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow p-4"
          >
            <div className="flex items-start gap-3 mb-4">
              <span className="text-4xl">{getFileIcon(doc.mimeType)}</span>
              <div className="flex-1">
                <h3 className="font-semibold truncate">{doc.documentName}</h3>
                <p className="text-xs text-text-muted">{doc.documentType}</p>
              </div>
            </div>

            <div className="space-y-2 text-sm text-text-secondary mb-4">
              <p>📤 by {doc.uploadedByUserName}</p>
              <p>📅 {new Date(doc.uploadedAt).toLocaleDateString()}</p>
              <p>💾 {(doc.fileSize / 1024).toFixed(2)} KB</p>
            </div>

            {doc.notes && (
              <p className="text-xs text-text-muted mb-4 p-2 bg-gray-50 rounded line-clamp-2">{doc.notes}</p>
            )}

            <div className="flex gap-2">
              <a
                href={doc.fileUrl}
                target="_blank"
                rel="noopener noreferrer"
                className="flex-1 px-3 py-2 bg-secondary text-white text-center rounded text-sm hover:bg-secondary-dark transition-colors"
              >
                View
              </a>
              <button className="flex-1 px-3 py-2 bg-gray-200 text-gray-800 rounded text-sm hover:bg-gray-300 transition-colors">
                Download
              </button>
            </div>
          </motion.div>
        ))}
      </div>

      {documents.length === 0 && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="text-center py-12 bg-gray-50 rounded-lg"
        >
          <p className="text-text-muted text-lg">No documents found</p>
          <p className="text-text-muted text-sm">Upload your travel documents to keep them organized</p>
        </motion.div>
      )}
    </div>
  );
};

export default DocumentManager;
