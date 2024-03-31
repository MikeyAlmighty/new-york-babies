import React, { useState, useEffect } from 'react';
import "./search.css"

interface SearchInputProps {
  onSearch: (term: string) => void
}

const SearchInput = ({ onSearch }: SearchInputProps) => {
  const [searchTerm, setSearchTerm] = useState<string>('');

  useEffect(() => {
    const delaySearch = setTimeout(() => {
      onSearch(searchTerm);
    }, 500);

    return () => clearTimeout(delaySearch);
  }, [searchTerm, onSearch]);

  const handleInputChange = ({ target }: any) => setSearchTerm(target.value);;

  return (
    <input
      className={"search-input"}
      type="text"
      placeholder="Search Name"
      value={searchTerm}
      onChange={handleInputChange}
    />
  );
};

export default SearchInput;
