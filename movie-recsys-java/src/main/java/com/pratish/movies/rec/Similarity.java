package com.pratish.movies.rec;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Similarity {

    // Cosine similarity between two sparse rating vectors
    public static double cosine(Map<Integer, Double> a, Map<Integer, Double> b) {
        Set<Integer> common = new HashSet<>(a.keySet());
        common.retainAll(b.keySet());
        if (common.isEmpty()) return 0.0;

        double dot = 0, na = 0, nb = 0;
        for (int i : common) dot += a.get(i) * b.get(i);
        for (double v : a.values()) na += v * v;
        for (double v : b.values()) nb += v * v;
        if (na == 0 || nb == 0) return 0.0;
        return dot / (Math.sqrt(na) * Math.sqrt(nb));
    }

    // Pearson correlation (centered)
    public static double pearson(Map<Integer, Double> a, Map<Integer, Double> b) {
        Set<Integer> common = new HashSet<>(a.keySet());
        common.retainAll(b.keySet());
        if (common.size() < 2) return 0.0;

        double meanA = common.stream().mapToDouble(i -> a.get(i)).average().orElse(0);
        double meanB = common.stream().mapToDouble(i -> b.get(i)).average().orElse(0);

        double num = 0, denA = 0, denB = 0;
        for (int i : common) {
            double da = a.get(i) - meanA;
            double db = b.get(i) - meanB;
            num += da * db;
            denA += da * da;
            denB += db * db;
        }
        double den = Math.sqrt(denA) * Math.sqrt(denB);
        if (den == 0) return 0.0;
        return num / den;
    }
}
