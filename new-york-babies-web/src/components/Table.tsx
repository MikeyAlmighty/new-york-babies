import {useEffect, useMemo, useState} from "react";

import "./table.css"
import { Data } from './data'
import {Navigation} from "./Navigation";
import {useTable} from "../stores/TableContext";
import SearchInput from "./Search";

export const Table = () => {
    const [initialData, setInitialData] = useState({ pageNumber: 1, data: [], totalPages: 0});
    const [sortOptions, setSortOptions] = useState({ isDescending: true, field: 'firstName'});
    const [searchTerm, setSearchTerm] = useState<string>("");

    const {
        sortingField,
        updateSortingField,
        pageSize,
        offset,
    } = useTable();

    useEffect(() => {
        fetchData();
    }, [offset, pageSize, sortingField, sortOptions.isDescending, searchTerm]);

    const handleSort = (field: string) => {
        updateSortingField(field);
        if (field === sortOptions.field) {
            setSortOptions({
                field,
                isDescending: !sortOptions.isDescending
            })
        } else {
            setSortOptions({
                field,
                isDescending: true
            })
        }
    }

    const fetchData = async () => {
        try {
            const response =
                !(searchTerm.length > 0)
                    ? await fetch(`http://localhost:8080/api/baby/pagination/${offset}/${pageSize}/${sortingField}/${sortOptions.isDescending ? "ASC" : "DSC"}`)
                    : await fetch(`http://localhost:8080/api/baby/${offset}/${pageSize}/search?term=${searchTerm}`);

            const jsonData = await response.json();
            const data = jsonData.response.content;
            const totalPages = jsonData.response.totalPages;

            const pageNumber = jsonData.response.pageable.pageNumber ?? 1;

            setInitialData({
                data,
                totalPages,
                pageNumber
            });

        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    return(
        <div style={{ display: "flex", flexDirection: "column"}}>
            <SearchInput onSearch={setSearchTerm}/>
            <table>
                <thead>
                    <tr>
                        {Data.map(({ label, accessor }) => (
                            <th key={accessor} onClick={(e) => handleSort(accessor)}>
                                <span style={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
                                    {label}
                                    {sortOptions.field === accessor && (!sortOptions.isDescending  ? <p>&#9650;</p> : <p>&#9660;</p>)}
                                </span>
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                {initialData.data?.map(({
                  id,
                  yearOfBirth,
                  gender,
                  ethnicity,
                  firstName  ,
                  count,
                  score
                }) => (
                    <tr key={id}>
                        <td>
                            {firstName}
                        </td>
                        <td>
                            {yearOfBirth}
                        </td>
                        <td>
                            {gender}
                        </td>
                        <td>
                            {ethnicity}
                        </td>
                        <td>
                            {count}
                        </td>
                        <td>
                            {score}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <Navigation
                pageNumber={initialData.pageNumber}
                totalPages={initialData.totalPages}
            />
        </div>
    )
}
