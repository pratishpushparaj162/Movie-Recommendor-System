package com.pratish.movies.rec;

import java.util.*;
import java.util.stream.Collectors;

/** Item-based CF: precompute item vectors; score by similar items the user liked */
public class ItemCF {
    private final Map<Integer, Map<Integer, Double>> userRatings;
    private final Map<Integer, Map<Integer, Double>> itemVectors;

    public ItemCF(Map<Integer, Map<Integer, Double>> userRatings) {
        this.userRatings = userRatings;
        this.itemVectors = transpose(userRatings); // movieId -> (userId -> rating)
    }

    private static Map<Integer, Map<Integer, Double>> transpose(Map<Integer, Map<Integer, Double>> userItem) {
        Map<Integer, Map<Integer, Double>> itemUser = new HashMap<>();
        for (var ue : userItem.entrySet()) {
            int u = ue.getKey();
            for (var me : ue.getValue().entrySet()) {
                int m = me.getKey();
                double r = me.getValue();
                itemUser.computeIfAbsent(m, k -> new HashMap<>()).put(u, r);
            }
        }
        return itemUser;
    }

    public Map<Integer, Double> recommend(int userId, int topN) {
        Map<Integer, Double> uvec = userRatings.getOrDefault(userId, Map.of());
        Set<Integer> seen = uvec.keySet();

        // Score candidates by similarity to items the user has rated
        Map<Integer, Double> scores = new HashMap<>();
        Map<Integer, Double> weights = new HashMap<>();

        for (int seenItem : seen) {
            Map<Integer, Double> seenVec = itemVectors.getOrDefault(seenItem, Map.of());
            for (var e : itemVectors.entrySet()) {
                int cand = e.getKey();
                if (seen.contains(cand)) continue;

                double sim = Similarity.cosine(seenVec, e.getValue());
                if (sim <= 0) continue;

                double ratingByUser = uvec.get(seenItem);
                scores.merge(cand, sim * ratingByUser, Double::sum);
                weights.merge(cand, Math.abs(sim), Double::sum);
            }
        }

        Map<Integer, Double> predictions = new HashMap<>();
        for (var e : scores.entrySet()) {
            double w = weights.getOrDefault(e.getKey(), 0.0);
            if (w > 0) predictions.put(e.getKey(), e.getValue() / w);
        }

        return predictions.entrySet().stream()
                .sorted((a,b)->Double.compare(b.getValue(), a.getValue()))
                .limit(topN)
                .collect(Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue,
                    (x,y)->x, LinkedHashMap::new));
    }
}
