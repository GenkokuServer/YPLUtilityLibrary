package com.github.erozabesu.yplutillibrary.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import com.google.common.io.Files;

public class CommentableYamlConfiguration extends YamlConfiguration{
    private final static String BLANK = "";
    private final static String DOT = ".";
    private final static String NEWLINE = "\n";
    private final static String UTF8 = "UTF8";
    private final static String COMMENT_KEY = "#";
    private final static String COMMENT_PREFIX = COMMENT_KEY + " ";
    private final static String PATTERN_COMMENT_PREFIX = "(?<=^|\n)(?!#|!\n)";
    private final static String PATTERN_COMMENT_BLANKLINE = "(?<=^|\n)!(?=\n)";
    private final static String PATTERN_COMMENT_SPACEY = "(?<=^|\n)(?=#)";

    private final Pattern keyPattern = Pattern.compile("( *)(.+?): *(.*)");

    private List<String> dummyToken;
    private int dummyIndentCount;

    private String altHeader;
    private Map<String, String> commentMap;

    // <bukkit>
    // protected static final String BLANK_CONFIG = "{}\n";
    private static final String BLANK_CONFIG = "{}\n";

    private final DumperOptions yamlOptions = new DumperOptions();
    private final Representer yamlRepresenter = new YamlRepresenter();
    private final Yaml yaml = new Yaml(new YamlConstructor(), this.yamlRepresenter, this.yamlOptions);
    // </bukkit>

    public CommentableYamlConfiguration () {
        this.dummyToken = new ArrayList<String>();
        this.dummyIndentCount = 0;

        this.altHeader = BLANK;
        this.commentMap = new HashMap<String, String>();
    }

    @Override
    public String saveToString() {

        // <bukkit>
        this.yamlOptions.setIndent(options().indent());
        this.yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yamlOptions.setAllowUnicode(true);
        this.yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        // String header = buildHeader();
        String dump = this.yaml.dump(getValues(false));
        if (dump.equals(BLANK_CONFIG)) {
            dump = BLANK;
        }

        // return header + dump;
        // </bukkit>

        String result = this.getAltHeader() + this.reDumper(dump);

        try {
            result = new String (result.getBytes(UTF8), UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void save(File file) throws IOException {
        // <bukkit>
        Validate.notNull(file, "File cannot be null");

        Files.createParentDirs(file);

        String data = saveToString();

        // Writer writer = new OutputStreamWriter(new FileOutputStream(file),
        //         (UTF8_OVERRIDE) && (!UTF_BIG) ? Charsets.UTF_8 : Charset.defaultCharset());
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), UTF8);
        try {
            writer.write(data);
        } finally {
            writer.close();
        }
        // </bukkit>
    }

    /**
     * ローカルのコンフィグファイルから設定データを読み込む。<br>
     * YamlConfiguration.loadConfiguration(File file)では、<br>
     * 2バイト文字を含むUTF-8で記述されたテキストファイルは正常に読み取れないため、<br>
     * YamlConfiguration.loadConfiguration(Reader reader)を利用する。
     * @param filepath コンフィグファイルのパス
     * @return 取得したYamlConfiguration
     */
    @Override
    public void load(File file) {
        Validate.notNull(file, "File cannot be null");
        Validate.notNull(file.getParentFile(), "Parent File cannot be null");

        InputStream input = null;
        BufferedReader reader = null;

        try {
            input = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            this.load(reader);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
    }

    private String reDumper (String dump) {
        String reDump = "";
        for (String line: dump.split(NEWLINE)) {
            Matcher m = keyPattern.matcher(line);
            if (m.find()) {
                String spacey = m.group(1);
                String key = m.group(2);
                String value = m.group(3);

                String commentNode = layerToNode (spacey, key);
                if (!commentNode.equals(BLANK) && this.commentMap.containsKey(commentNode)){
                    String comment =  this.commentMap.get(commentNode);
                    reDump += comment
                            .replaceAll(PATTERN_COMMENT_PREFIX, COMMENT_PREFIX)
                            .replaceAll(PATTERN_COMMENT_BLANKLINE, BLANK)
                            .replaceAll(PATTERN_COMMENT_SPACEY, spacey)
                            + NEWLINE;
                }
            }
            reDump += line + NEWLINE;
        }

        this.dummyToken.clear();
        this.dummyIndentCount = 0;

        return reDump;
    }

    private String layerToNode (String spacey, String key) {
        if (key == null) {
        }
        else if (spacey.length() == 0) {
            this.dummyToken.clear();
            this.dummyToken.add(key);
            this.dummyIndentCount = 0;
        }
        else if (spacey.length() == this.dummyIndentCount) {
            this.dummyToken.remove(this.dummyToken.size() - 1);
            this.dummyToken.add(key);
        }
        else if (spacey.length() > this.dummyIndentCount) {
            this.dummyToken.add(key);
            this.dummyIndentCount = spacey.length();
        }
        else if (spacey.length() < this.dummyIndentCount) {
            int removeCounter = (this.dummyIndentCount - spacey.length()) * (this.dummyToken.size() - 1) / this.dummyIndentCount + 1;
            while (removeCounter > 0) {
                this.dummyToken.remove(this.dummyToken.size() - 1);
                removeCounter--;
            }
            this.dummyToken.add(key);
            this.dummyIndentCount = spacey.length();
        }
        return this.combineNode(this.dummyToken);
    }

    private String combineNode (List<String> list) {
        if (list.isEmpty()) {
            return "";
        }
        Iterator<String> iterator = list.iterator();
        String combine = BLANK;
        while (iterator.hasNext()) {
            combine += iterator.next();
            if (iterator.hasNext()) {
                combine += DOT;
            }
        }

        return combine;
    }

    public void setComment (String paramString, String comment) {
        this.commentMap.put(paramString, comment);
    }

    public void setCommentAll (Map<String, String> map) {
        this.commentMap.putAll(map);
    }

    public void deletComment (String paramString) {
        this.commentMap.remove(paramString);
    }

    public void setAltHeader (String header) {
        header = header
                .replaceAll(PATTERN_COMMENT_PREFIX, COMMENT_PREFIX)
                .replaceAll(PATTERN_COMMENT_BLANKLINE, BLANK)
                + NEWLINE;

        this.altHeader = header;
    }

    public String getAltHeader () {
        if (this.altHeader != null && !this.altHeader.equals(BLANK)){
            return this.altHeader + NEWLINE;
        }
        return this.altHeader;
    }
}
