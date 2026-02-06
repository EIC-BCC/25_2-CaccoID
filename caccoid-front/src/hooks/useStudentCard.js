import { useState, useEffect } from 'react';
import { useAPI } from './useAPI';

export const useStudentCard = (cardId) => {
  const [card, setCard] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const api = useAPI();

  useEffect(() => {
    if (!cardId) return;

    const fetchCard = async () => {
      setLoading(true);
      setError(null);
      try {
        let response;
        try {
          response = await api.get(`/student-card/${cardId}`);
        } catch {
          response = await api.get(`/student-card/get-card-by-id/${cardId}`);
        }
        setCard(response.data.result || null);
      } catch (err) {
        console.error('Erro ao buscar carteirinha:', err);
        setCard(null);
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCard();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [cardId]);

  return { card, loading, error };
};
