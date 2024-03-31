import * as React from "react"
import {useEffect} from "react";
import "./styles.css"
import {Table} from "./components/Table";
import {TableProvider} from "./stores/TableContext";

export const App = () => {
    useEffect(() => {
        document.title = "New york | Baby Name Index"
    }, []);

    return (
        <div style={{ textAlign: "center" }}>
            <h1>New York Baby Name Index</h1>
            <TableProvider>
                <Table />
            </TableProvider>
        </div>
    )
}
