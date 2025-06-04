import { useState, useMemo } from 'react';

export default function useDiaryFilters(diaries) {
  const [filters, setFilters] = useState({
    project: '',
    tag: '',
    date: '',
  });

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const filteredDiaries = useMemo(() => {
    return diaries.filter((d) => {
      return (
        (filters.project === '' || d.project === filters.project) &&
        (filters.tag === '' || d.tags.includes(filters.tag)) &&
        (filters.date === '' || d.date === filters.date)
      );
    });
  }, [diaries, filters]);

  return { filters, handleFilterChange, filteredDiaries };
}
