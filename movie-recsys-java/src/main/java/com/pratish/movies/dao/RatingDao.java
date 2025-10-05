package com.pratish.movies.dao;

import com.pratish.movies.db.Database;

import java.sql.*;
import java.util.*;

public class RatingDAO {

    public Map<Integer, Map<Integer, Double>> loadUserMovieRatings() throws SQLException {
        // userId -> (movieId -> rating)
        Map<Integer, Map<Integer, Double>> userRatings = new HashMap<>();
        String sql = "SELECT user_id, movie_id, rating FROM ratings";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery( )) {
            while (rs.next()) {
                int u = rs.getInt("user_id");
                int m = rs.getInt("movie_id");
                double r = rs.getDouble("rating");
                userRatings.computeIfAbsent(u, k -> new HashMap<>()).put(m, r);
            }
        }
        return userRatings;
    }

    public Map<Integer, String> loadMovieTitles() throws SQLException {
        Map<Integer, String> map = new HashMap<>();
        String sql = "SELECT id, title FROM movies";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getInt("id"), rs.getString("title"));
            }
        }
        return map;
    }

    public Set<Integer> loadAllUsers() throws SQLException {
        Set<Integer> set = new HashSet<>();
        String sql = "SELECT id FROM users";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) set.add(rs.getInt("id"));
        }
        return set;
    }
}
