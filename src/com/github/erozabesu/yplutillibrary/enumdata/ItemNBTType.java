package com.github.erozabesu.yplutillibrary.enumdata;

/**
 *
 * @author erozabesu
 *
 */
public enum ItemNBTType {
    HIDE_FLAGS("HideFlags"),
    ENCHANT("ench"),
    ATTRIBUTE_MODIFIERS("AttributeModifiers"),
    DISPLAY("display"),
    UNBREAKABLE("Unbreakable"),
    CAN_DESTROY("CanDestroy"),
    CAN_PLACE_ON("CanPlaceOn"),

    // 独自タグ

    MENU("Menu");

    /** NBTTagの固有名 */
    private String tagName;

    private ItemNBTType(String tagName) {
        this.setTagName(tagName);
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return NBTTagの固有名 */
    public String getTagName() {
        return tagName;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param tagName NBTTagの固有名 */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * ItemStackに適用されるNBTTag AttributeModifiersの内部タグとして利用されるタグの名称を管理するクラス。
     * @author erozabesu
     */
    public static enum ItemNBTAttributeType {
        /** 最大体力上昇 */
        MAX_HEALTH("generic.maxHealth"),

        /** 索敵範囲の広さ */
        FOLLOW_RANGE("generic.followRange"),

        /** ノックバック耐性 */
        KNOCK_BACK_RESISTANCE("generic.knockbackResistance"),

        /** 移動速度 */
        MOVEMENT_SPEED("generic.movementSpeed"),

        /** 攻撃力 */
        ATTACK_DAMAGE("generic.attackDamage"),

        /** ジャンプ力。ウマにのみ適用される。 */
        JUMP_STRENGTH("horse.jumpStrength"),

        /** 被攻撃時に他のゾンビを召喚する確立。ゾンビにのみ適用される。 */
        SPAWN_REINFORCEMENTS("zombie.spawnReinforcements");

        /** タグ名 */
        private String tagName;

        private ItemNBTAttributeType(String tagName) {
            this.setTagName(tagName);
        }

        /** @return タグ名 */
        public String getTagName() {
            return tagName;
        }

        /** @param tagName タグ名 */
        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
    }
}
