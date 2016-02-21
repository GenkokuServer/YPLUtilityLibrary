package com.github.erozabesu.yplutillibrary.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author erozabesu
 *
 */
public abstract class LoaderAbstract {

    /** JavaPluginインスタンス */
    private JavaPlugin plugin;

    /** コンフィグファイルネーム */
    private String fileName;

    /** ローカルコンフィグファイルパス */
    private String localPath;

    /** デフォルトコンフィグファイルパス */
    private String defaultPath;

    public LoaderAbstract(JavaPlugin plugin) {
        this.setPlugin(plugin);
    }

    /** ローカルコンフィグファイルの設定値を取得し格納する */
    public abstract void loadLocal();

    /** ローカルコンフィグファイルに設定値を保存する */
    public abstract void saveLocal();

    /** デフォルトコンフィグファイルの設定値を取得し格納する */
    public abstract void loadDefault();

    public void reload() {
        try {
            this.saveLocal();
        } catch (NullPointerException ex) {
            // Do nothing
        }

        this.loadDefault();
        this.loadLocal();
    }

    /**
     * コンフィグファイルの生成が必要な状態かどうかを判別し必要ならファイルを生成する。<br>
     * defaultPathに指定されている.jar内のディレクトリにファイルが存在する場合はファイルをコピーする。<br>
     * 存在しない場合は空のファイルを生成する。<br>
     * 既にlocalPathにファイルが存在する場合は何もせずfalseを返す
     * @return ファイルを生成したかどうか
     */
    public boolean CreateConfig() {
        //既にローカルファイルが存在している
        if (new File(this.getLocalPath()).exists()) {
            return false;
        }

        /*
         * jarファイルからコンフィグファイルのコピーを試みる
         */
        JavaPlugin plugin = this.getPlugin();

        File outputFile = new File(this.getLocalPath());
        InputStream input = null;
        FileOutputStream output = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;

        File parent = outputFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        try {
            input = plugin.getResource(this.getDefaultPath());

            // デフォルトパスにファイルが存在しない
            if (input == null) {
                //log.log(Level.WARNING, "[" + plugin.getName() + "] v." + plugin.getDescription().getVersion() + " " + this.getFileName() + " was not found in jar file");
                outputFile.createNewFile();

            // デフォルトパスにファイルが存在する
            } else {
                output = new FileOutputStream(outputFile);
                reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));

                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
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
            if (input != null) {
                try {
                    input.close();
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

        return true;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return JavaPluginインスタンス */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /** @return コンフィグファイルネーム */
    public String getFileName() {
        return fileName;
    }

    /** @return ローカルコンフィグファイルパス */
    public String getLocalPath() {
        return localPath;
    }

    /** @return デフォルトコンフィグファイルパス */
    public String getDefaultPath() {
        return defaultPath;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param plugin JavaPluginインスタンス */
    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /** @param fileName コンフィグファイルネーム */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /** @param localPath ローカルコンフィグファイルパス */
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /** @param defaultPath デフォルトコンフィグファイルパス */
    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }
}
