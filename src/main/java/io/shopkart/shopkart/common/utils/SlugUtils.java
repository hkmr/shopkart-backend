package io.shopkart.shopkart.common.utils;

import java.text.Normalizer;
import java.util.Locale;

public final class SlugUtils {

    private SlugUtils() {}

    public static String toSlug(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        String slug = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")           // remove accents
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")      // replace punctuation/spaces
                .replaceAll("^-|-$", "");           // trim hyphens

        if (slug.isBlank()) {
            throw new IllegalArgumentException("Name cannot produce a valid slug");
        }

        return slug;
    }
}
