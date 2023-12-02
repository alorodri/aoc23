package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    private final String originalText;
    private Map<String, Block> blocksHash = new HashMap<>();
    private Block lastBlock;
    public Parser(String text) {
        this.originalText = text;
        Block defaultBlock = new Block();
        defaultBlock.add(text);
        blocksHash.put("default", defaultBlock);
        lastBlock = defaultBlock;
    }
    public Parser divideBy(final String delimiter, final String hashKey, final String fromHashKey) {
        if (hashKey.equals("default")) {
            throw new IllegalArgumentException("Cannot put duplicate default key");
        } else if (hashKey.equals("last")) {
            throw new IllegalArgumentException("Cannot save with last key");
        }

        Block b;
        if (fromHashKey != null) {
            if (fromHashKey.equals("last")) b = lastBlock;
            else b = blocksHash.get(fromHashKey);
        } else {
            b = blocksHash.get("default");
        }

        for (String c : b.contentList) {
            final String[] splitResult = c.split(delimiter);
            Block newBlock = new Block();
            for (String r : splitResult) {
                newBlock.add(r);
            }
        }

        blocksHash.put(hashKey, b);
    }

    static class Block {

        List<String> contentList = new ArrayList<>();

        public void add(String content) {
            this.contentList.add(content);
        }
    }
}
