import React, { createContext, useContext, useState, FC } from 'react';

interface TableContextType {
  sortingField: string;
  updateSortingField: (field: string) => void;
  pageSize: number;
  updatePageSize: (field: number) => void;
  offset: number;
  updateOffset: (field: number) => void;
}

// Create a context for the table
const TableContext = createContext<TableContextType | undefined>(undefined);

// Create a provider component
export const TableProvider: FC<any> = ({ children }) => {
  const [sortingField, setSortingField] = useState<string>('firstName');
  const [pageSize, setPageSize] = useState<number>(10);
  const [offset, setOffset] = useState<number>(0);

  const updateSortingField = (field: string) => setSortingField(field);;

  const updatePageSize = (field: number) => setPageSize(field);;

  const updateOffset = (field: number) => setOffset(field);;

    return (
    <TableContext.Provider
        value={{
          sortingField,
          updateSortingField,
          pageSize,
          updatePageSize,
          offset,
          updateOffset
        }}>
      {children}
    </TableContext.Provider>
  );
};

// Custom hook to use the table context
export const useTable = (): TableContextType => {
  const context = useContext(TableContext);
  if (!context) {
    throw new Error('useTable must be used within a TableProvider');
  }
  return context;
};