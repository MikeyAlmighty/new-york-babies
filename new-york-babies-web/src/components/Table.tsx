import {useEffect, useMemo, useState} from "react";

import "./table.css"
import { Data } from './data'
import {Navigation} from "./Navigation";
import {useTable} from "../stores/TableContext";

export const Table = () => {
    const [initialData, setInitialData] = useState([]);

    const {
        sortingField,
        updateSortingField,
        pageSize,
        offset,
    } = useTable();

    useEffect(() => {
        fetchData();
    }, [offset, pageSize, sortingField]);

    console.log("OFFSET: ", offset)
    const handleSort = (field: string) => {
        updateSortingField(field);
    }

    const fetchData = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/baby/pagination/${offset}/${pageSize}/${sortingField}`);
            const jsonData = await response.json();
            const babyData = jsonData.response.content;

            setInitialData(babyData);

        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    return(
        <div style={{ display: "flex", flexDirection: "column"}}>
            <table>
                <thead>
                    <tr>
                        {Data.map(({ label, accessor }) => (
                            <th key={accessor} onClick={(e) => handleSort(accessor)}>
                                {label}
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                {initialData.map(({
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
            <Navigation />
        </div>
    )
}
