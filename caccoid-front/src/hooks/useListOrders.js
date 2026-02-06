import { useState, useEffect } from 'react';
import { useAPI } from './useAPI';
import { solicitationStatusConverter } from '../utils/solicitationStatusConverter';

export const useListOrders = ({status}) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const api = useAPI();

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);

      try {
        const response = await api.get('/solicitation/get/status/' + solicitationStatusConverter(status));
        console.log('Response data:', response.data);
        setData(response.data.result);

      } catch (err) {
        console.error('Erro ao buscar dados:', err);
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [status]);

  return { data, loading, error };
};
