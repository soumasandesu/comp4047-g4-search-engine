package se;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Keyword implements Serializable {
    String keyword;
    LinkedList<KeywordUrl> keywordUrls;

    public Keyword(String keyword) {
        this.keyword = keyword;
        this.keywordUrls = new LinkedList<>();
    }

    void addKeywordUrls(List<KeywordUrl> keywordUrls) {
        for (KeywordUrl ku : keywordUrls) {
            addKeywordUrl(ku);
        }
        keywordUrls.sort(KeywordUrl::compare);
    }

    void addKeywordUrl(KeywordUrl keywordUrl) {
        int idx = keywordUrls.indexOf(keywordUrl);
        if (idx > -1)
            keywordUrls.get(idx).addAll(keywordUrl.sources);
        else
            keywordUrls.add(keywordUrl);
        keywordUrls.sort(KeywordUrl::compare);
    }

    LinkedList<KeywordUrl> getAll() {
        return keywordUrls;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() && this.keyword.equals(((Keyword) obj).keyword);
    }
}