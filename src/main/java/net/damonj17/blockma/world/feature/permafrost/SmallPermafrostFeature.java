package net.damonj17.blockma.world.feature.permafrost;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Optional;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class SmallPermafrostFeature extends Feature<SmallPermafrostFeatureConfig> {
    public SmallPermafrostFeature(Codec<SmallPermafrostFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<SmallPermafrostFeatureConfig> context) {
        WorldAccess worldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        SmallPermafrostFeatureConfig smallPermafrostFeatureConfig = (SmallPermafrostFeatureConfig)context.getConfig();
        Optional<Direction> optional = getDirection(worldAccess, blockPos, random);
        if (optional.isEmpty()) {
            return false;
        } else {
            BlockPos blockPos2 = blockPos.offset(((Direction)optional.get()).getOpposite());
            generatePermafrostBlocks(worldAccess, random, blockPos2, smallPermafrostFeatureConfig);
            int i = random.nextFloat() < smallPermafrostFeatureConfig.chanceOfTallerPermafrost && PermafrostHelper.canGenerate(worldAccess.getBlockState(blockPos.offset((Direction)optional.get()))) ? 2 : 1;
            PermafrostHelper.generatePointedPermafrost(worldAccess, blockPos, (Direction)optional.get(), i, false);
            return true;
        }
    }

    private static Optional<Direction> getDirection(WorldAccess world, BlockPos pos, Random random) {
        boolean bl = PermafrostHelper.canReplace(world.getBlockState(pos.up()));
        boolean bl2 = PermafrostHelper.canReplace(world.getBlockState(pos.down()));
        if (bl && bl2) {
            return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
        } else if (bl) {
            return Optional.of(Direction.DOWN);
        } else {
            return bl2 ? Optional.of(Direction.UP) : Optional.empty();
        }
    }

    private static void generatePermafrostBlocks(WorldAccess world, Random random, BlockPos pos, SmallPermafrostFeatureConfig config) {
        PermafrostHelper.generatePermafrostBlock(world, pos);
        Iterator var4 = Type.HORIZONTAL.iterator();

        while(var4.hasNext()) {
            Direction direction = (Direction)var4.next();
            if (!(random.nextFloat() > config.chanceOfDirectionalSpread)) {
                BlockPos blockPos = pos.offset(direction);
                PermafrostHelper.generatePermafrostBlock(world, blockPos);
                if (!(random.nextFloat() > config.chanceOfSpreadRadius2)) {
                    BlockPos blockPos2 = blockPos.offset(Direction.random(random));
                    PermafrostHelper.generatePermafrostBlock(world, blockPos2);
                    if (!(random.nextFloat() > config.chanceOfSpreadRadius3)) {
                        BlockPos blockPos3 = blockPos2.offset(Direction.random(random));
                        PermafrostHelper.generatePermafrostBlock(world, blockPos3);
                    }
                }
            }
        }

    }
}
