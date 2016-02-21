package com.github.erozabesu.yplutillibrary.tellraw.object;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.github.erozabesu.yplutillibrary.tellraw.event.HoverEvent.HoverActionType;

/**
 *
 * @author erozabesu
 *
 */
public class ShowEntityValue implements IHoverEventValue {

    /** エンティティのUUID */
    private UUID uuid;

    /** エンティティのカスタム名 */
    private String name;

    /** エンティティタイプ */
    private EntityType entityType;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public ShowEntityValue(Entity entity) {
        this.setUuid(entity.getUniqueId());
        this.setName(entity.getCustomName());
        this.setEntityType(entity.getType());
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    @Override
    public HoverActionType getActionType() {
        return HoverActionType.SHOW_ENTITY;
    }

    /*
     * id:uuid,
     * name:custome_name,
     * type:entity_type
     */
    @Override
    public String buildCommandParts() {
        String parts = "";

        // UUIDの記述
        parts += "id:" + this.getUuid().toString();

        // 名前の記述
        parts += ",name:" + this.getName();

        // エンティティタイプの記述
        parts += ",type:" + this.getEntityType().name().toLowerCase();

        return parts;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return エンティティのUUID */
    public UUID getUuid() {
        return uuid;
    }

    /** @return エンティティのカスタム名 */
    public String getName() {
        return name;
    }

    /** @return エンティティタイプ */
    public EntityType getEntityType() {
        return entityType;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param uuid エンティティのUUID */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /** @param name エンティティのカスタム名 */
    public void setName(String name) {
        this.name = name;
    }

    /** @param entityType エンティティタイプ */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
