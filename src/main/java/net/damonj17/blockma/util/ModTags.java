package net.damonj17.blockma.util;

import net.damonj17.blockma.BlockmaMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> PERMAFROST_ORE_REPLACEABLES = createTag("permafrost_ore_replaceables");
        public static final TagKey<Block> PERMAFROST_REPLACEABLE_BLOCKS = createTag("permafrost_replaceable_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(BlockmaMod.MOD_ID, name));
        }
    }

    public static class Items {

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(BlockmaMod.MOD_ID, name));
        }
    }
}