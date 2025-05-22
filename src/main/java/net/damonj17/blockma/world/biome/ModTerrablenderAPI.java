package net.damonj17.blockma.world.biome;


import net.damonj17.blockma.BlockmaMod;
import net.damonj17.blockma.world.biome.surface.ModMaterialRules;
import net.minecraft.util.Identifier;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerrablenderAPI implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverworldRegion(Identifier.of(BlockmaMod.MOD_ID,"permafrost_cave"), RegionType.OVERWORLD, 2));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, BlockmaMod.MOD_ID, ModMaterialRules.makeRules());
    }
}
