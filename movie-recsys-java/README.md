# Movie Recommendation System (Java + MySQL)

Collaborative filtering recommendations in Java using JDBC and MySQL.
Supports **User-based CF** and **Item-based CF** with cosine similarity (Pearson also available).

## Setup
1. Install Java 17+ and MySQL.
2. Create DB and seed:
   ```sql
   SOURCE src/main/resources/schema.sql;
