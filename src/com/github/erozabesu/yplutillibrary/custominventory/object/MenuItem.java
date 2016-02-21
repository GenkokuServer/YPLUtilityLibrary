package com.github.erozabesu.yplutillibrary.custominventory.object;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * カスタムインベントリに並べるアイテム
 * @author erozabesu
 */
public abstract class MenuItem implements Cloneable {

    /** カスタムインベントリのアイテム */
    private ItemStack item = new ItemStack(Material.STONE);

    /** カスタムインベントリのページ番号 */
    private int page = 1;

    /** カスタムインベントリのスロット番号 */
    private int slot = 0;

    /** アイテムのスタック数をページ番号と連動させるかどうか */
    private boolean syncStackToPage = false;

    /** syncStackToPageがtrueの場合、ページ番号値との差のオフセットを指定する */
    private int syncStackRelativeOffset = 0;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * コンストラクタ
     * @param item アイテム
     * @param page ページ番号
     * @param slot スロット番号
     */
    public MenuItem(ItemStack item, int page, int slot) {
        this.setItem(item);
        this.setPage(page);
        this.setSlot(slot);
    }

    /**
     * コンストラクタ
     * @param item アイテム
     * @param page ページ番号
     * @param slot スロット番号
     * @param syncStackToPage アイテムのスタック数をページ番号とリンクさせるかどうか
     * @param syncStackRelativeOffset syncStackToPageがtrueの場合に、スタック数のオフセットを指定する
     */
    public MenuItem(ItemStack item, int page, int slot, boolean syncStackToPage, int syncStackRelativeOffset) {
        this(item, page, slot);
        this.setSyncStackToPage(syncStackToPage);
        this.setSyncStackRelativeOffset(syncStackRelativeOffset);
    }

    /** メニューアイテムをクリックした際に実行される処理 */
    public abstract void onClick();

    /** メニューアイテムをシフトクリックした際に実行される処理 */
    public abstract void onShiftClick();

    @Override
    public MenuItem clone() {
        try {
            return (MenuItem) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return カスタムインベントリのアイテム */
    public ItemStack getItem() {
        return item;
    }

    /** @return カスタムインベントリのページ番号 */
    public int getPage() {
        return page;
    }

    /** @return カスタムインベントリのスロット番号 */
    public int getSlot() {
        return slot;
    }

    /** @return アイテムのスタック数をページ番号と連動させるかどうか */
    public boolean isSyncStackToPage() {
        return syncStackToPage;
    }

    /** @return syncStackToPageがtrueの場合、ページ番号値との差のオフセットを指定する */
    public int getSyncStackRelativeOffset() {
        return syncStackRelativeOffset;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param item カスタムインベントリのアイテム */
    public MenuItem setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    /** @param page カスタムインベントリのページ番号 */
    public MenuItem setPage(int page) {
        this.page = page;
        return this;
    }

    /** @param slot カスタムインベントリのスロット番号 */
    public MenuItem setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    /** @param syncStackToPage アイテムのスタック数をページ番号と連動させるかどうか */
    public MenuItem setSyncStackToPage(boolean syncStackToPage) {
        this.syncStackToPage = syncStackToPage;
        return this;
    }

    /** @param syncNumberRelativeOffset syncStackToPageがtrueの場合、ページ番号値との差のオフセットを指定する */
    public MenuItem setSyncStackRelativeOffset(int syncNumberRelativeOffset) {
        this.syncStackRelativeOffset = syncNumberRelativeOffset;
        return this;
    }
}
