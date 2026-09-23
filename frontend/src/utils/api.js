const BASE_URL = import.meta.env.VITE_API_URL || 'http://127.0.0.1:8080';

const post = async (path, payload) => {
    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    return response.json();
};

export const submitGameMetrics = (payload) => post('/api/metrics', payload);
export const submitMemoryMetrics = (payload) => post('/api/metrics/memory', payload);
export const submitFlexibilityMetrics = (payload) => post('/api/metrics/flexibility', payload);
export const submitSpeedMetrics = (payload) => post('/api/metrics/speed', payload);

export const fetchCognitiveProfile = async (childId) => {
    const response = await fetch(`${BASE_URL}/api/profile/${childId}`);
    if (!response.ok) throw new Error(`Profile fetch failed: ${response.status}`);
    return response.json();
};