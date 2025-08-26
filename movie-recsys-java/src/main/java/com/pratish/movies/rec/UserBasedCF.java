package com.pratish.movies.rec;

import java.util.*;
import java.util.stream.Collectors;

/** User-based CF: score unseen items for a target user via neighbor weighted ratings */
public class UserCF {
    private final Map<Integer, Map<Integer, Double>> userRatings;

    public UserCF(Map<Integer, Map<Integer, Double>> userRatings) {
        this.userRatings = userRatings;
    }

    public Map<Integer, Double> recommend(int targetUser, int topN) {
        Map<Integer, Double> scores = new HashMap<>();
        Map<Integer, Double> targetVec = userRatings.getOrDefault(targetUser, Map.of());

        // Build candidate items = items other users rated that target hasn't
        Set<Integer> candidates = new HashSet<>();
        for (var e : userRatings.entrySet()) {
            if (e.getKey() == targetUser) continue;
            for (int m : e.getValue().keySet()) {
                if (!targetVec.containsKey(m)) candidates.add(m);
            }
        }

        // Score each candidate by weighted sum of neighbors' ratings
        for (int m : candidates) {
            double num = 0, den = 0;
            for (var e : userRatings.entrySet()) {
                int other = e.getKey();
                if (other == targetUser) continue;
                Map<Integer, Double> otherVec = e.getValue();
                if (!otherVec.containsKey(m)) continue;

                double sim = Similarity.cosine(targetVec, otherVec);
                if (sim <= 0) continue;

                num += sim * otherVec.get(m);
                den += Math.abs(sim);
            }
            if (den > 0) scores.put(m, num / den);
        }

        // Cold-start fallback: if nothing scored, return popular items
        if (scores.isEmpty()) {
            Map<Integer, Double> popularity = new HashMap<>();
            for (var e : userRatings.entrySet()) {
                for (var me : e.getValue().entrySet()) {
                    popularity.merge(me.getKey(), 1.0, Double::sum);
                }
            }
            return popularity.entrySet().stream()
                    .filter(me -> !targetVec.containsKey(me.getKey()))
                    .sorted((a,b)->Double.compare(b.getValue(), a.getValue()))
                    .limit(topN)
                    .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (x,y)->x, LinkedHashMap::new));
        }

        return scores.entrySet().stream()
                .sorted((a,b)->Double.compare(b.getValue(), a.getValue()))
                .limit(topN)
                .collect(Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue,
                    (x,y)->x, LinkedHashMap::new));
    }
}
