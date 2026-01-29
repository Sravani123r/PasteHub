import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL
});

export const createPaste = (data) => api.post('/api/pastes', data);

export const getPaste = (id) => api.get(`/api/pastes/${id}`);
