package com.github.erozabesu.yplutillibrary.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.erozabesu.yplutillibrary.config.CommentableYamlConfiguration;
import com.github.erozabesu.yplutillibrary.util.CommonUtil;

/**
 * プレイヤーのインベントリ情報をymlファイルとして入出力するクラス
 * @author erozabesu
 *
 */
public class SimpleInventoryHolder {
    /** プレイヤーのUUID */
    private UUID uuid;

    /** コンフィグファイル */
    private File configFile;

    /** コンフィグデータ */
    private CommentableYamlConfiguration config;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * コンストラクタ。<br>
     * インスタンスを渡すと同時に、playerのインベントリファイルを取得、もしくは存在しない場合新規生成する。
     * @param plugin
     * @param fileDirectoryPath
     * @param player
     */
    public SimpleInventoryHolder(JavaPlugin plugin, String fileDirectoryPath, Player player){
        this.setUniqueId(player.getUniqueId());

        File dataFolder = new File(fileDirectoryPath);
        dataFolder.mkdirs();
        this.setConfigFile(new File(dataFolder, this.getUniqueId() + ".yml"));

        if (!this.getConfigFile().exists()) {
            try {
                this.getConfigFile().createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        CommentableYamlConfiguration commentConfig = new CommentableYamlConfiguration();
        commentConfig.load(this.getConfigFile());
        this.setConfig(commentConfig);
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    private void save() {
        try {
            this.getConfig().save(this.getConfigFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setItemStack(ItemStack setItem, int inventorySlot) {
        this.getConfig().set(String.valueOf(inventorySlot), setItem);

        this.save();
    }

    public void removeItemStack(ItemStack removeItem) {
        for (String key : this.getConfig().getKeys(false)) {
            ItemStack configItem = this.getConfig().getItemStack(key);
            if (configItem == null) {
                continue;
            }
            if (CommonUtil.isSimilar(configItem, removeItem, 1)) {
                this.getConfig().set(key, null);
            }
        }

        this.save();
    }

    public void removeItemStack(List<ItemStack> removeItemList) {
        for (String key : this.getConfig().getKeys(false)) {
            for (ItemStack removeItem : removeItemList) {
                ItemStack configItem = this.getConfig().getItemStack(key);
                if (configItem == null) {
                    continue;
                }
                if (CommonUtil.isSimilar(configItem, removeItem, 1)) {
                    this.getConfig().set(key, null);
                }
            }
        }

        this.save();
    }

    /**
     * コンフィグファイルのデータを初期化し保存する。
     */
    public void clearData() {
        Iterator<String> iterator = getConfig().getKeys(true).iterator();
        while(iterator.hasNext()) {
            getConfig().set(iterator.next(), null);
        }

        this.save();
    }

    /**
     * プレイヤーインベントリの全アイテムを引数に応じてローカルファイルに保存する。<br>
     * 引数armorがtrueの場合、アーマーコンテンツのアイテムを保存する。<br>
     * 引数containerがtrueの場合、コンテナのアイテムを保存する。<br>
     * 引数workspaceがtrueの場合、ワークスペースのアイテムを保存する。<br>
     * 引数whiteListがnull,emptyでない場合、上記アイテムの内引数whiteListに含まれるアイテムのみローカルファイルに保存する。
     */
    private void savePlayerData(boolean armor, boolean container, boolean workspace, List<ItemStack> whiteList) {
        Player player = Bukkit.getPlayer(this.getUniqueId());
        if (player == null) {
            return;
        }

        this.clearData();

        PlayerInventory inventory = player.getInventory();
        List<ItemStack> whiteListClone = whiteList == null ? new ArrayList<ItemStack>() : whiteList;

        // ホワイトリストが有効かどうか
        boolean whiteListed = !whiteListClone.isEmpty();

        if (armor) {
            ItemStack helmet = inventory.getHelmet();
            if (helmet != null) {
                if (whiteListed) {
                    if (CommonUtil.containsSimilarItemStack(whiteListClone, helmet)) {
                        this.getConfig().set("helmet", helmet);
                    }
                } else {
                    this.getConfig().set("helmet", helmet);
                }
            }

            ItemStack chest = inventory.getChestplate();
            if (chest != null) {
                if (whiteListed) {
                    if (CommonUtil.containsSimilarItemStack(whiteListClone, chest)) {
                        this.getConfig().set("chestplate", chest);
                    }
                } else {
                    this.getConfig().set("chestplate", chest);
                }
            }

            ItemStack leggings = inventory.getLeggings();
            if (leggings != null) {
                if (whiteListed) {
                    if (CommonUtil.containsSimilarItemStack(whiteListClone, leggings)) {
                        this.getConfig().set("leggings", leggings);
                    }
                } else {
                    this.getConfig().set("leggings", leggings);
                }
            }

            ItemStack boots = inventory.getBoots();
            if (boots != null) {
                if (whiteListed) {
                    if (CommonUtil.containsSimilarItemStack(whiteListClone, boots)) {
                        this.getConfig().set("boots", boots);
                    }
                } else {
                    this.getConfig().set("boots", boots);
                }
            }
        }

        ItemStack[] inv = inventory.getContents();

        if (container) {
            for(int slot = 9; slot < 36; slot++){
                if(inv[slot] != null) {
                    if (whiteListed) {
                        if (CommonUtil.containsSimilarItemStack(whiteListClone, inv[slot])) {
                            this.getConfig().set(String.valueOf(slot), inv[slot]);
                        }
                    } else {
                        this.getConfig().set(String.valueOf(slot), inv[slot]);
                    }
                }
            }
        }

        if (workspace) {
            for(int slot = 0; slot < 9; slot++){
                if(inv[slot] != null) {
                    if (whiteListed) {
                        if (CommonUtil.containsSimilarItemStack(whiteListClone, inv[slot])) {
                            this.getConfig().set(String.valueOf(slot), inv[slot]);
                        }
                    } else {
                        this.getConfig().set(String.valueOf(slot), inv[slot]);
                    }
                }
            }
        }

        this.save();
    }

    /**
     * アーマーコンテンツ、コンテナ、ワークスペースの全アイテムをローカルファイルに保存する。
     */
    public void savePlayerInventoryData() {
        this.savePlayerData(true, true, true, null);
    }

    /**
     * アーマーコンテンツ、コンテナ、ワークスペースの全アイテムのうち、<br>
     * 引数whiteListに含まれるアイテムのみローカルファイルに保存する。
     * @param whiteList
     */
    public void savePlayerInventoryData(List<ItemStack> whiteList) {
        this.savePlayerData(true, true, true, whiteList);
    }

    /**
     * アーマーコンテンツの全アイテムをローカルファイルに保存する。
     */
    public void savePlayerArmorContentsData() {
        this.savePlayerData(true, false, false, null);
    }

    /**
     * アーマーコンテンツの全アイテムのうち、<br>
     * 引数whiteListに含まれるアイテムのみローカルファイルに保存する。
     * @param whiteList
     */
    public void savePlayerArmorContentsData(List<ItemStack> whiteList) {
        this.savePlayerData(true, false, false, whiteList);
    }

    /**
     * コンテナの全アイテムをローカルファイルに保存する。
     */
    public void savePlayerContainerData() {
        this.savePlayerData(false, true, false, null);
    }

    /**
     * コンテナの全アイテムのうち、<br>
     * 引数whiteListに含まれるアイテムのみローカルファイルに保存する。
     * @param whiteList
     */
    public void savePlayerContainerData(List<ItemStack> whiteList) {
        this.savePlayerData(false, true, false, whiteList);
    }

    /**
     * ワークスペースの全アイテムをローカルファイルに保存する。
     */
    public void savePlayerWorkSpaceData() {
        this.savePlayerData(false, false, true, null);
    }

    /**
     * ワークスペースの全アイテムのうち、<br>
     * 引数whiteListに含まれるアイテムのみローカルファイルに保存する。
     * @param whiteList
     */
    public void savePlayerWorkSpaceData(List<ItemStack> whiteList) {
        this.savePlayerData(false, false, true, whiteList);
    }

    /**
     * 保存されたインベントリ情報を元にインベントリを復元する。<br>
     * mergeItemにtrueを指定した場合は、既にインベントリスロットにアイテムが存在している場合でもアイテムを上書き復元する。
     * @param mergeItem アイテムを上書き復元するかどうか
     */
    public void recoveryInventory(boolean mergeItem) {
        Player player = Bukkit.getPlayer(this.getUniqueId());
        if (player == null) {
            return;
        }

        PlayerInventory inventory = player.getInventory();
        for (String key : this.getConfig().getKeys(true)) {
            if (key.equalsIgnoreCase("helmet")) {
                ItemStack helmet = getConfig().getItemStack(key);
                if (helmet != null) {
                    if (mergeItem || inventory.getHelmet() == null) {
                        inventory.setHelmet(helmet);
                    }
                }
            } else if (key.equalsIgnoreCase("chestplate")) {
                ItemStack chect = getConfig().getItemStack(key);
                if (chect != null) {
                    if (mergeItem || inventory.getChestplate() == null) {
                        inventory.setChestplate(chect);
                    }
                }
            } else if (key.equalsIgnoreCase("leggings")) {
                ItemStack leggings = getConfig().getItemStack(key);
                if (leggings != null) {
                    if (mergeItem || inventory.getLeggings() == null) {
                        inventory.setLeggings(leggings);
                    }
                }
            } else if (key.equalsIgnoreCase("boots")) {
                ItemStack boots = getConfig().getItemStack(key);
                if (boots != null) {
                    if (mergeItem || inventory.getBoots() == null) {
                        inventory.setBoots(boots);
                    }
                }
            } else {
                ItemStack item = this.getConfig().getItemStack(key);
                if (item != null) {
                    if (mergeItem || inventory.getItem(Integer.valueOf(key)) == null) {
                        inventory.setItem(Integer.valueOf(key), item);
                    }
                }
            }
        }

        player.updateInventory();
    }

    /**
     * 保存されたインベントリ情報のアイテムスタックをリストで返す。
     * @return 保存されているアイテムスタックリスト
     */
    public List<ItemStack> getInventoryContents() {
        List<ItemStack> contents = new ArrayList<ItemStack>();

        for (String key : this.getConfig().getKeys(true)) {
            if (key.equalsIgnoreCase("helmet")) {
                ItemStack helmet = getConfig().getItemStack(key);
                if (helmet != null) {
                    contents.add(helmet);
                }
            } else if (key.equalsIgnoreCase("chestplate")) {
                ItemStack chect = getConfig().getItemStack(key);
                if (chect != null) {
                    contents.add(chect);
                }
            } else if (key.equalsIgnoreCase("leggings")) {
                ItemStack leggings = getConfig().getItemStack(key);
                if (leggings != null) {
                    contents.add(leggings);
                }
            } else if (key.equalsIgnoreCase("boots")) {
                ItemStack boots = getConfig().getItemStack(key);
                if (boots != null) {
                    contents.add(boots);
                }
            } else {
                ItemStack item = this.getConfig().getItemStack(key);
                if (item != null) {
                    contents.add(item);
                }
            }
        }

        return contents;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return プレイヤーのUUID */
    public UUID getUniqueId() {
        return uuid;
    }

    /** @return コンフィグファイル */
    public File getConfigFile() {
        return configFile;
    }

    /** @return コンフィグデータ */
    public CommentableYamlConfiguration getConfig() {
        return config;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param uuid プレイヤーのUUID */
    private void setUniqueId(UUID uuid) {
        this.uuid = uuid;
    }

    /** @param configFile コンフィグファイル */
    private void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    /** @param config コンフィグデータ */
    private void setConfig(CommentableYamlConfiguration config) {
        this.config = config;
    }
}
