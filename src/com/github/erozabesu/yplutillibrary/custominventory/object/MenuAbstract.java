package com.github.erozabesu.yplutillibrary.custominventory.object;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * 仮想チェストインベントリを利用したカスタムメニューインベントリのオブジェクトクラス。<br>
 * 重要な仕様として、最大スロット数を超えるスロット番号、もしくは0未満の番号を利用したメニューアイテムは特に制限なく登録できる。<br>
 * 登録されたアイテムは、仮想インベントリをプレイヤーに表示する際に無視されるのみで、異常なく動作する。<br>
 * これは、拡張性を高めるために意図的に対処していない仕様であるため、スロット番号を設定する場合は注意すること。
 * @author erozabesu
 */
public abstract class MenuAbstract implements Cloneable {

    /** メニューとして使用するインベントリ */
    private Inventory inventory;

    /** メニューに登録されているアイテムリスト */
    private HashSet<MenuItem> itemSet = new HashSet<MenuItem>();

    /** 各ページに必ず配置されるアイテムリスト */
    private HashSet<MenuItem> absoluteItemSet = new HashSet<MenuItem>();

    /** メニュー名 */
    private String menuName = "";

    /** 最大スロット数 */
    private int slotSize = 36;

    /** 最大ページ数 */
    private int pageSize = 1;

    /** 現在開いているページ番号 */
    private int currentPage = 1;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * コンストラクタ
     * @param menuName メニューのタイトルネーム
     * @param slotSize インベントリサイズ。9～54間の9の倍数を指定すること。
     */
    public MenuAbstract(String menuName, int slotSize) {
        this.setMenuName(menuName);
        this.setSlotSize(slotSize);
        this.setInventory(Bukkit.createInventory(null, slotSize, menuName));
    }

    /**
     * コンストラクタ
     * @param menuName メニューのタイトルネーム
     * @param slotSize インベントリサイズ。9～54間の9の倍数を指定すること。
     * @param pageSize ページサイズ
     */
    public MenuAbstract(String menuName, int slotSize, int pageSize) {
        this(menuName, slotSize);
        this.setPageSize(pageSize);
    }

    @Override
    public MenuAbstract clone() {
        try {
            return (MenuAbstract) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * メニューに配置するアイテムを登録する。
     * @param menuItemArray 配置するアイテム
     */
    public void registerItem(MenuItem... menuItemArray) {
        for (MenuItem item : menuItemArray) {
            // 重複するスロットのアイテムを削除
            this.unregisterItem(item.getPage(), item.getSlot());

            this.getItemSet().add(item);
        }
    }

    /**
     * メニューに配置されている通常アイテムの登録を全て解除する。
     */
    public void unregisterAllItems() {
        this.getItemSet().clear();
    }

    /**
     * メニューに配置されているアイテムから引数menuItemの登録を解除する。<br>
     * 解除できた場合はtrueを返す。<br>
     * 登録されていない場合はfalseを返す。
     * @param menuItem 登録を解除するアイテム
     * @return 登録を解除したかどうか
     */
    public boolean unregisterItem(MenuItem menuItem) {
        return this.getItemSet().remove(menuItem);
    }

    /**
     * 引数pageのページ番号、引数slotのスロット番号に配置されているアイテムの登録を解除する。<br>
     * 解除できた場合はtrueを返す。<br>
     * 登録されていない場合はfalseを返す。
     * @param page ページ番号
     * @param slot スロット番号
     * @return 登録を解除したかどうか
     */
    public boolean unregisterItem(int page, int slot) {
        for (MenuItem registeredItem : this.getItemsByPage(page)) {
            if (registeredItem.getSlot() == slot) {
                return this.getItemSet().remove(registeredItem);
            }
        }
        return false;
    }

    /**
     * メニューに配置するアイテムをabsolute指定で登録する。<br>
     * absolute指定のアイテムは必ず毎ページに配置される。<br>
     * また、同様のスロットに通常のアイテムが配置されている場合、上書き配置される。
     * @param menuItem 配置するアイテム
     */
    public void registerAbsoluteItem(MenuItem menuItem) {
        // 重複するスロットのアイテムを削除
        this.unregisterAbsoluteItem(menuItem.getSlot());

        this.getAbsoluteItemSet().add(menuItem);
    }

    /**
     * メニューに配置されているabsolute指定のアイテムの登録を全て解除する。
     */
    public void unregisterAllAbsoluteItems() {
        this.getAbsoluteItemSet().clear();
    }

    /**
     * メニューに配置されているabsolute指定のアイテムから引数menuItemの登録を解除する。<br>
     * 解除できた場合はtrueを返す。<br>
     * 登録されていない場合はfalseを返す。
     * @param menuItem 登録を解除するアイテム
     * @return 登録を解除したかどうか
     */
    public boolean unregisterAbsoluteItem(MenuItem menuItem) {
        return this.getAbsoluteItemSet().remove(menuItem);
    }

    /**
     * 引数slotのスロット番号に配置されているabsolute指定のアイテムの登録を解除する。<br>
     * 解除できた場合はtrueを返す。<br>
     * 登録されていない場合はfalseを返す。
     * @param slot スロット番号
     * @return 登録を解除したかどうか
     */
    public boolean unregisterAbsoluteItem(int slot) {
        for (MenuItem registeredItem : this.getAbsoluteItemSet()) {
            if (registeredItem.getSlot() == slot) {
                return this.getAbsoluteItemSet().remove(registeredItem);
            }
        }
        return false;
    }

    /**
     * 引数pageNumberのページに配置されているアイテムをインベントリに配置する。
     * @param pageNumber ページ番号
     */
    public void updateMenuItems(int pageNumber) {
        // インベントリのアイテムの初期化
        this.getInventory().clear();

        // 開いているページの更新
        this.setCurrentPage(getPageByRawPageNumber(pageNumber));

        // pageNumberのページに配置されるアイテムを取得し、配置
        for (MenuItem menuItem : this.getItemsByPage(pageNumber)) {
            // 最大スロット数を超えるスロット番号を指定したMenuItemは配置しない
            if (this.isInMenuSlot(menuItem.getSlot())) {
                ItemStack itemStack = menuItem.getItem().clone();

                // スタック数のページ番号との同期設定が有効なMenuItemの場合は同期する
                if (menuItem.isSyncStackToPage()) {
                    itemStack.setAmount(this.getPageByRawPageNumber(this.getCurrentPage() + menuItem.getSyncStackRelativeOffset()));
                }
                this.getInventory().setItem(menuItem.getSlot(), menuItem.getItem());
            }
        }

        // absolute指定のアイテムを配置
        for (MenuItem menuItem : this.getAbsoluteItemSet()) {
            // 最大スロット数を超えるスロット番号を指定したMenuItemは配置しない
            if (this.isInMenuSlot(menuItem.getSlot())) {
                ItemStack itemStack = menuItem.getItem().clone();

                // スタック数のページ番号との同期設定が有効なMenuItemの場合は同期する
                if (menuItem.isSyncStackToPage()) {
                    itemStack.setAmount(this.getPageByRawPageNumber(this.getCurrentPage() + menuItem.getSyncStackRelativeOffset()));
                }
                this.getInventory().setItem(menuItem.getSlot(), itemStack);
            }
        }
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数slotがインベントリ内のスロット番号かどうかを返す。
     * @param slot スロット番号
     * @return
     */
    public boolean isInMenuSlot(int slot) {
        return 0 <= slot && slot < this.getSlotSize();
    }

    /**
     * 引数playerにメニューを表示する。
     * @param player 表示するプレイヤー
     */
    public void openMenu(Player player) {
        this.updateMenuItems(this.getCurrentPage());
        player.closeInventory();
        player.openInventory(this.getInventory());
    }

    /**
     * 引数playerに引数pageがページ番号のメニューを表示する。
     * @param player 表示するプレイヤー
     * @param page 表示するページ
     */
    public void openMenu(Player player, int page) {
        this.updateMenuItems(this.getPageByRawPageNumber(page));
        player.closeInventory();
        player.openInventory(this.getInventory());
    }

    public void renameAndReopenInventory(Player player, String newName) {
        this.setMenuName(newName);

        Inventory inv = Bukkit.createInventory(null, this.getSlotSize(), newName);
        inv.setContents(this.getInventory().getContents());

        this.setInventory(inv);

        this.openMenu(player, this.getCurrentPage());
    }

    /**
     * 引数pageNumberのページに配置されているMenuItemをHashSetで返す。
     * @param pageNumber ページ番号
     * @return 取得したMenuItemのHashSet
     */
    public HashSet<MenuItem> getItemsByPage(int pageNumber) {
        HashSet<MenuItem> itemList = new HashSet<MenuItem>();
        for (MenuItem menuItem : this.getItemSet()) {
            if (menuItem.getPage() == pageNumber) {
                itemList.add(menuItem);
            }
        }

        return itemList;
    }

    /**
     * 現在開いているページに配置されているスロット番号が引数slotNumberのスロットのMenuItemを返す。
     * @param slotNumber スロット番号
     * @return 取得したMenuItem
     */
    public MenuItem getItemBySlot(int slotNumber) {
        for (MenuItem menuItem : this.getItemsByPage(this.getCurrentPage())) {
            if (menuItem.getSlot() == slotNumber) {
                return menuItem;
            }
        }

        for (MenuItem menuItem : this.getAbsoluteItemSet()) {
            if (menuItem.getSlot() == slotNumber) {
                return menuItem;
            }
        }

        return null;
    }

    /**
     * rawPageをメニューの最大ページ数の範囲内のページ番号に変換し返す。<br>
     * 負の数、及び最大ページ数を超えている場合は、最大ページ数以内に収まるまで数値の1順を繰り返す。
     * @param rawPage 変換するページ番号
     * @return 変換後のページ番号
     */
    public int getPageByRawPageNumber(int rawPage) {
        // 最大ページを超過したページを指定している場合、ページ数を1順させる
        while (this.getPageSize() < rawPage) {
            rawPage -= this.getPageSize();
        }

        // 指定ページが負の数の場合、ページ数を1順させる
        while (rawPage < 1) {
            rawPage += this.getPageSize();
        }

        return rawPage;
    }

    /**
     * rawPageが負の数、もしくは最大ページ数を超えた数値の場合、終端のページ番号に変換し返す。<br>
     * 負の数の場合は1を返し、最大ページ数を超えている場合は最大ページ数を返す。
     * @param rawPage 変換するページ番号
     * @return 変換後のページ番号
     */
    public int getEndPageByRawNumber(int rawPage) {
        if (this.getPageSize() < rawPage) {
            return this.getPageSize();
        } else if (rawPage < 1) {
            return 1;
        }

        return rawPage;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return メニューとして使用するインベントリ */
    protected Inventory getInventory() {
        return inventory;
    }

    /** @return メニューに登録されているアイテムリスト */
    protected HashSet<MenuItem> getItemSet() {
        return itemSet;
    }

    /** @return 各ページに必ず配置されるアイテムリスト */
    protected HashSet<MenuItem> getAbsoluteItemSet() {
        return absoluteItemSet;
    }

    /** @return メニュー名 */
    public String getMenuName() {
        return menuName;
    }

    /** @return 最大スロット数 */
    public int getSlotSize() {
        return slotSize;
    }

    /** @return 最大ページ数 */
    public int getPageSize() {
        return pageSize;
    }

    /** @return 現在開いているページ番号 */
    public int getCurrentPage() {
        return currentPage;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param inventory メニューとして使用するインベントリ */
    protected void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /** @param itemList メニューに登録されているアイテムリスト */
    protected void setItemSet(HashSet<MenuItem> itemList) {
        this.itemSet = itemList;
    }

    /** @param absoluteItemSet 各ページに必ず配置されるアイテムリスト */
    protected void setAbsoluteItemSet(HashSet<MenuItem> absoluteItemSet) {
        this.absoluteItemSet = absoluteItemSet;
    }

    /** @param name メニュー名 */
    protected void setMenuName(String name) {
        this.menuName = name;
    }

    /** @param slotSize 最大スロット数 */
    protected void setSlotSize(int slotSize) {
        this.slotSize = slotSize;
    }

    /** @param pageSize 最大ページ数 */
    protected void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /** @param currentPage 現在開いているページ番号 */
    protected void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
