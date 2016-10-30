package se;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class KeywordsStorage {
    public static final KeywordsStorage INSTANCE = new KeywordsStorage();

    private HashMap<String, Keyword> keywords;
    private HashSet<String> _removedYetDeleted;

    private KeywordsStorage() {
        this.keywords = new HashMap<>();
        this._removedYetDeleted = new HashSet<>();

        String startDir = System.getProperty("user.dir");
        java.io.File keywordsDir = Paths.get(startDir, "keywords").toFile();
        if (!keywordsDir.exists())
            try {
                Files.createDirectory(keywordsDir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        for (final java.io.File fileEntry : keywordsDir.listFiles()) {
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileEntry)))) {
                put((Keyword) ois.readObject()); // TODO: 2016/10/31 ClassNotFoundExceprion
            } catch (FileNotFoundException fe) {
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sync() throws IOException {
        for (String kw : keywords.keySet()) {
            File fileKw = new File("keywords/" + kw, true);
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fileKw.getOutputStream()))) {
                oos.writeObject(keywords.get(kw));
            }
        }
        for (String kwRmv : _removedYetDeleted) {
            File fileKwRmv = new File("keywords/" + kwRmv, true);
            fileKwRmv.delete();
        }
        _removedYetDeleted.clear();
    }

    // TODO: debug : File not found exception -- charles
    public void put(Keyword keyword) {
        if (keywords.containsKey(keyword.keyword)) {
            this.keywords.get(keyword.keyword).addKeywordUrls(keyword.getAll());
        } else {
            this.keywords.put(keyword.keyword, keyword);
        }
    }

    public void putAll(Collection<Keyword> keywords) {
        for (Keyword k : keywords)
            put(k);
    }

    public Keyword get(String keywordString) {
        return this.keywords.get(keywordString);
    }

    public Keyword remove(String keywordString) {
        if (!keywords.containsKey(keywordString))
            return null;
        _removedYetDeleted.add(keywordString);
        return this.keywords.remove(keywordString);
    }
}
