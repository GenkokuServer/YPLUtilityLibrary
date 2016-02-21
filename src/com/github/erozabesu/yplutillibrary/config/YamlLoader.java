package com.github.erozabesu.yplutillibrary.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author erozabesu
 *
 */
public class YamlLoader extends LoaderAbstract {

    /** ローカルコンフィグ */
    private CommentableYamlConfiguration localConfig;

    /** デフォルトコンフィグ */
    private YamlConfiguration defaultConfig;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public YamlLoader(JavaPlugin plugin, String fileName, String localPath, String defaultPath) {
        super(plugin);

        this.setFileName(fileName);
        this.setLocalPath(localPath);
        this.setDefaultPath(defaultPath);

        this.loadDefault();
        this.loadLocal();
    }

    /**
     * 引数を元にローカルパス、デフォルトパスを自動設定し格納するコンストラクタ。<br>
     * fileNameは「ファイル名.yml」のフォーマットで記述すること。<br>
     * ローカルパスは plugins/plugin/filename<br>
     * デフォルトパスは .jar/src/resources/filename に設定される。
     * @param fileName
     */
    public YamlLoader(JavaPlugin plugin, String fileName) {
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
     * 引数を元にローカルパス、デフォルトパスを自動設定し格納するコンストラクタ。<br>
     * ローカルパスは plugins/plugin/language/filename<br>
     * デフォルトパスは .jar/src/language/引数language/filename に設定される。<br>
     * デフォルトパスが存在しない場合は .jar/src/language/ja_JP/filenameに設定される。
     * @param fileName
     * @param language
     */
    public YamlLoader(JavaPlugin plugin, String fileName, String language) {
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
        CommentableYamlConfiguration config = new CommentableYamlConfiguration();
        config.load(new File(this.getLocalPath()));
        this.setLocalConfig(config);
    }

    @Override
    public void saveLocal() {
        try {
            this.getLocalConfig().save(this.getLocalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadDefault() {
        InputStream input = null;
        BufferedReader reader = null;

        try {
            input = this.getPlugin().getResource(this.getDefaultPath());

            if (input == null) {
                return;
            }

            reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            this.setDefaultConfig(YamlConfiguration.loadConfiguration(reader));
        } catch (UnsupportedEncodingException e) {
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

    public void incertComment(YamlConfiguration commentConfig) {
        for (String key : commentConfig.getKeys(false)) {
            // バリューの改行タグを\nに置換
            String value = commentConfig.getString(key);

            // <bbr><br>の順序で置換すると正しく改行されないため、前後を入れ替える
            value = value.replaceAll("<bbr><br>", "<br><bbr>");
            value = value.replaceAll("<br>", "\n");
            value = value.replaceAll("<bbr>", "!\n");

            this.getLocalConfig().setComment(key.replaceAll("\\$", "\\."), value);
        }
    }

    public void incertHeader(List<String> headerComment) {
        // バリューの改行タグを\nに置換
        String value = "";
        for (String values : headerComment) {
            value += values;
        }

        // <bbr><br>の順序で置換すると正しく改行されないため、前後を入れ替える
        value = value.replaceAll("<bbr><br>", "<br><bbr>");
        value = value.replaceAll("<br>", "\n");
        value = value.replaceAll("<bbr>", "!\n");

        this.getLocalConfig().setAltHeader(value);
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つString値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @return 取得したString値
     */
    public String getString(String configKey) {
        return this.getLocalConfig().getString(configKey, this.getDefaultConfig().getString(configKey));
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つString値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @param defaultValue デフォルト値
     * @return 取得したString値
     */
    public String getString(String configKey, String defaultValue) {
        return this.getLocalConfig().getString(configKey, defaultValue);
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つboolean値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @return 取得したboolean値
     */
    public boolean getBoolean(String configKey) {
        return this.getLocalConfig().getBoolean(configKey, this.getDefaultConfig().getBoolean(configKey));
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つboolean値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @param defaultValue デフォルト値
     * @return 取得したboolean値
     */
    public boolean getBoolean(String configKey, boolean defaultValue) {
        return this.getLocalConfig().getBoolean(configKey, defaultValue);
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つint値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @return 取得したint値
     */
    public int getInt(String configKey) {
        return this.getLocalConfig().getInt(configKey, this.getDefaultConfig().getInt(configKey));
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つint値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @param defaultValue デフォルト値
     * @return 取得したint値
     */
    public int getInt(String configKey, int defaultValue) {
        return this.getLocalConfig().getInt(configKey, defaultValue);
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つbyte値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @return 取得したbyte値
     */
    public byte getByte(String configKey) {
        return (byte) this.getInt(configKey);
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つbyte値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @param defaultValue デフォルト値
     * @return 取得したbyte値
     */
    public byte getByte(String configKey, byte defaultValue) {
        return (byte) this.getInt(configKey, defaultValue);
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つdouble値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @return 取得したdouble値
     */
    public double getDouble(String configKey) {
        return this.getLocalConfig().getDouble(configKey, this.getDefaultConfig().getDouble(configKey));
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つdouble値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @param defaultValue デフォルト値
     * @return 取得したdouble値
     */
    public double getDouble(String configKey, double defaultValue) {
        return this.getLocalConfig().getDouble(configKey, defaultValue);
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つfloat値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @return 取得したfloat値
     */
    public float getFloat(String configKey) {
        return (float) this.getDouble(configKey);
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つfloat値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @param defaultValue デフォルト値
     * @return 取得したfloat値
     */
    public float getFloat(String configKey, float defaultValue) {
        return (float) this.getDouble(configKey, defaultValue);
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つMaterial値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @return 取得したMaterial値
     */
    public Material getMaterial(String configKey) {
        String materialName = this.getLocalConfig().getString(configKey, this.getDefaultConfig().getString(configKey, "STONE"));

        return Material.getMaterial(materialName.toUpperCase());
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つMaterial値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @param defaultValue デフォルト値
     * @return 取得したMaterial値
     */
    public Material getMaterial(String configKey, String defaultValue) {
        String materialName = this.getLocalConfig().getString(configKey, defaultValue);

        return Material.getMaterial(materialName.toUpperCase());
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つSound値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @return 取得したSound値
     */
    public Sound getSound(String configKey) {
        String soundName = this.getLocalConfig().getString(configKey, this.getDefaultConfig().getString(configKey, "STONE"));

        return Sound.valueOf(soundName.toUpperCase());
    }

    /**
     * ローカルコンフィグの設定データから引数configKeyをパスに持つSound値を返す。<br>
     * 一致する値が存在しない場合はデフォルトコンフィグの値を保存し返す。
     * @param configKey コンフィグキー
     * @param defaultValue デフォルト値
     * @return 取得したSound値
     */
    public Sound getSound(String configKey, String defaultValue) {
        String soundName = this.getLocalConfig().getString(configKey, defaultValue);

        return Sound.valueOf(soundName.toUpperCase());
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return ローカルコンフィグ */
    public CommentableYamlConfiguration getLocalConfig() {
        return localConfig;
    }

    /** @return デフォルトコンフィグ */
    public YamlConfiguration getDefaultConfig() {
        return defaultConfig;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param localConfig ローカルコンフィグ */
    private void setLocalConfig(CommentableYamlConfiguration localConfig) {
        this.localConfig = localConfig;
    }

    /** @param defaultConfig デフォルトコンフィグ */
    private void setDefaultConfig(YamlConfiguration defaultConfig) {
        this.defaultConfig = defaultConfig;
    }
}
