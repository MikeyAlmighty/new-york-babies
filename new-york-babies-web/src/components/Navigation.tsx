import { useTable } from "../stores/TableContext";

export const Navigation = () => {
 const {
     pageSize,
     updatePageSize,
     updateOffset

 } = useTable();

    const handleChange = ({ target }: any) => {
      updatePageSize(target.value)
    };

    const increaseOffset = ({ target }: any) => {
      updateOffset(target.value ++)
    }
    const decreaseOffset = ({ target }: any) => {
      updateOffset(target.value--)
    }

    return(
        <div>
            <button onClick={decreaseOffset}>{`< Previous`}</button>
             <select style={{ margin: "1em" }} value={pageSize} onChange={handleChange}>
                <option value="10">10</option>
                <option value="25">25</option>
                <option value="100">100</option>
              </select>
            <button  onClick={increaseOffset}>{`Next >`}</button>
        </div>
    )
}