# New York Baby Name index

Why ? Easter Weekend.

[DATASET: Popular Baby Names in New York](https://catalog.data.gov/dataset/popular-baby-names)

## Back End
###  `new-york-babies`
- Maps CSV (50k records) to DB using **Spring Batch**
- CRUD layer over BabyIndex:
  - `Sorting`
  - `Pagination`
  - `Searching`

## Front End
### `new-york-babies-web`
- `React`
- `Chakra UI`
- `TanStack Table`