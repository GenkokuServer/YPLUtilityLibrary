package com.github.erozabesu.yplutillibrary.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author erozabesu
 *
 */
public class RawTextLoader extends LoaderAbstract {

    /** ローカルテキスト */
    private List<String> localRawText;

    /** デフォルトコンフィグ */
    private List<String> defaultRawText;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public RawTextLoader(JavaPlugin plugin, String fileName, String localPath, String defaultPath) {
        super(plugin);

        this.setFileName(fileName);
        this.setLocalPath(localPath);
        this.setDefaultPath(defaultPath);

        this.loadDefault();
        this.loadLocal();
    }

    /**
     * 引数を元にローカルパス、デフォルトパスを自動設定し格納するコンストラクタ。<br>
     * ローカルパスは plugins/plugin/filename<br>
     * デフォルトパスは .jar/src/resources/filename に設定される。
     * @param fileName
     */
    public RawTextLoader(JavaPlugin plugin, String fileName) {
        super(plugin);

        this.setFileName(fileName);

        // ローカルパス
        this.setLocalPath(this.getPlugin().getDataFolder() + File.separator + fileName);

        // デフォルトパス
        this.setDefaultPath("resources/" + fileName);

        this.loadDefault();
        this.loadLocal();
    }

    /**
     * 引数を元に、言語ファイルに関するローカルパス、デフォルトパスを自動設定し格納するコンストラクタ。<br>
     * ローカルパスは plugins/plugin/language/filename<br>
     * デフォルトパスは .jar/src/language/引数language/filename に設定される。<br>
     * デフォルトパスが存在しない場合は .jar/src/language/ja_JP/filenameに設定される。
     * @param fileName
     * @param language
     */
    public RawTextLoader(JavaPlugin plugin, String fileName, String language) {
        super(plugin);

        this.setFileName(fileName);

        // ローカルパス
        String localPath = this.getPlugin().getDataFolder() + File.separator + language + File.separator + fileName;
        this.setLocalPath(localPath);

        // デフォルトパス
        String defaultPath = "language/" + language + "/" + fileName;
        // config.yml内の言語設定値のパッケージが存在しない場合、デフォルトパスとしてja_JPを利用する
        if (this.getPlugin().getResource(defaultPath) == null) {
            defaultPath = "language/ja_JP/" + fileName;
        }
        this.setDefaultPath(defaultPath);

        this.loadDefault();
        this.loadLocal();
    }

    @Override
    public void loadLocal() {
        // コンフィグの生成を試みる
        this.CreateConfig();

        // コンフィグの取得
        List<String> contents = new ArrayList<String>();

        File file = new File(this.getLocalPath());
        Validate.notNull(file, "File cannot be null");
        Validate.notNull(file.getParentFile(), "Parent File cannot be null");

        InputStream input = null;
        BufferedReader reader = null;

        try {
            input = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
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

        this.setLocalRawText(contents);
    }

    @Override
    public void saveLocal() {
        File outputFile = new File(this.getLocalPath());
        FileOutputStream output = null;
        BufferedWriter writer = null;

        File parent = outputFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        try {
            output = new FileOutputStream(outputFile);
            writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));

            for (String line : this.getLocalRawText()) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
            if (output != null) {
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
    }

    @Override
    public void loadDefault() {
        List<String> contents = new ArrayList<String>();
        InputStream input = null;
        BufferedReader reader = null;

        try {
            input = this.getPlugin().getResource(this.getDefaultPath());

            if (input == null) {
                return;
            }

            reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
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

        this.setDefaultRawText(contents);
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public String getCombinedLocalRawText() {
        String text = "";
        for (String localText : this.getLocalRawText()) {
            text += localText;
        }

        return text;
    }

    public String getCombinedDefaultRawText() {
        String text = "";
        for (String defaultText : this.getDefaultRawText()) {
            text += defaultText;
        }

        return text;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return ローカルコンフィグ */
    public List<String> getLocalRawText() {
        return localRawText;
    }

    /** @return デフォルトコンフィグ */
    public List<String> getDefaultRawText() {
        return defaultRawText;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param localConfig ローカルコンフィグ */
    private void setLocalRawText(List<String> localConfig) {
        this.localRawText = localConfig;
    }

    /** @param defaultConfig デフォルトコンフィグ */
    private void setDefaultRawText(List<String> defaultConfig) {
        this.defaultRawText = defaultConfig;
    }
}
