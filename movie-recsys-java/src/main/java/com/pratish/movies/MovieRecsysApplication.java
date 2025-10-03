package com.pratish.movies;

import com.pratish.movies.dao.RatingDAO;
import com.pratish.movies.rec.UserCF;
import com.pratish.movies.rec.ItemCF;

import java.sql.SQLException;
import java.util.Map;

public class App {
    public static void main(String[] args) throws SQLException {
        int userId = args.length > 0 ? Integer.parseInt(args[0]) : 1; // default:user 1
        int topN = args.length > 1 ? Integer.parseInt(args[1]) : 5;

        RatingDAO dao = new RatingDAO();
        var userRatings = dao.loadUserMovieRatings();
        var movieTitles = dao.loadMovieTitles();

        System.out.println("=== User-based CF Recommendations for user " + userId + " ===");
        var ub = new UserCF(userRatings).recommend(userId, topN);
        for (var e : ub.entrySet()) {
            System.out.printf("%-3d  %-30s  score=%.3f%n",
                    e.getKey(), movieTitles.getOrDefault(e.getKey(), ("Movie " + e.getKey())), e.getValue()) ;
        }

        System.out.println("\n=== Item-based CF Recommendations for user " + userId + " ===");
        var ib = new ItemCF(userRatings).recommend(userId, topN);
        for (var e : ib.entrySet()) {
            System.out.printf("%-3d  %-30s  score=%.3f%n",
                    e.getKey(), movieTitles.getOrDefault(e.getKey(), ("Movie " + e.getKey())), e.getValue());
        }

        System.out.println("\nTip: pass userId and topN: `mvn -q exec:java -Dexec.mainClass=com.pratish.movies.App -Dexec.args=\"2 3\"`");
    }
}
