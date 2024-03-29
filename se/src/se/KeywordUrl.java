package se;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a URL, its score in case of a keyword, and the description to represent the keyword in the website.
 * Also stores sources (where keywords exists? Meta Description or context) and positions.
 */
public class KeywordUrl implements Serializable, Comparable<KeywordUrl> {
    LinkedList<KeywordUrlSourcePositions> sources = new LinkedList<>();
    String url;
    String title;
    int score;
    String description;

    public KeywordUrl(String url, String title, int score) {
        this.url = url;
        this.title = title;
        this.score = score;
        this.sources = new LinkedList<>();
    }

    public void add(KeywordUrlSourcePositions value) {
        int idx = sources.indexOf(value);
        if (idx > -1) {
            sources.get(idx).addAll(value.positions);
        } else {
            sources.add(value);
        }
    }

    public void addAll(Collection<KeywordUrlSourcePositions> values) {
        for (KeywordUrlSourcePositions v : values) {
            add(v);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() && this.url.equals(((KeywordUrl) obj).url);
    }

    @Override
    public int compareTo(KeywordUrl another) {
        return compare(this, another);
    }

    public static int compare(KeywordUrl o1, KeywordUrl o2) {
        int x = o1.score;
        int y = o2.score;
        return (x > y) ? -1 : ((x == y) ? 0 : 1);
    }

    @Override
    public String toString() {
        ArrayList<String> ret = new ArrayList<>();
        for (KeywordUrlSourcePositions kusp : sources) {
            ret.add(kusp.toString());
        }
        return "{url=" + url + ", score=" + score + ", description=" + description + ", sources=[" + String.join(", ", ret) + "]}";
    }
}
