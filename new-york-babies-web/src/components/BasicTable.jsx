import {useEffect, useMemo, useState} from "react";
import { useTable } from 'react-table'

import "./table.css"
import { COLUMNS } from './Columns'

export const BasicTable = () => {
    const columns = useMemo(() => COLUMNS, [])
    const [initialData, setInitialData] = useState(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/baby/pagination/0/10');
      const jsonData = await response.json();
      const babyData = jsonData.response.content;

      setInitialData(babyData);

    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

    const tableInstance = useTable({
        columns,
        data: initialData ?? []
    })

    const { getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow
    } = tableInstance;

    return(
        <table
            {...getTableProps()}
        >
            <thead>
            {
                headerGroups.map((headerGroup) => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map(column => (
                            <th {...column.getHeaderProps()}>
                                {column.render('Header')}
                            </th>
                        ))}
                    </tr>
                ))
            }

            </thead>
            <tbody {...getTableBodyProps()}>
            {
                rows.map(row => {
                    prepareRow(row)
                    return (
                        <tr {...row.getRowProps()}>
                            {row.cells.map(cell => (
                                <td{...cell.getCellProps()}>
                                    {cell.render('Cell')}
                                </td>
                            ))}
                        </tr>
                    )
                })
            }
            </tbody>
        </table>
    )
}
