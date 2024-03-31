import { useTable } from "../stores/TableContext";

interface NavigationProps {
   pageNumber: number;
   totalPages: number;
}

export const Navigation = ({ pageNumber, totalPages }: NavigationProps) => {
 const {
     pageSize,
     updatePageSize,
     offset,
     updateOffset
 } = useTable();

    const handleChange = ({ target }: any) => updatePageSize(target.value)
    const increaseOffset = () => updateOffset(offset + 1)
    const decreaseOffset = () => updateOffset(offset - 1)

    return(
        <div>
            <h3>Page: {pageNumber} / {(totalPages / pageSize) * pageSize}</h3>
            <button disabled={offset === 0} onClick={decreaseOffset}>{`< Previous`}</button>
            <select style={{margin: "1em"}} value={pageSize} onChange={handleChange}>
                <option value="10">10</option>
                <option value="25">25</option>
                <option value="100">100</option>
            </select>
            <button onClick={increaseOffset}>{`Next >`}</button>
        </div>
    )
}