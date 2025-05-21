package net.damonj17.blockma.world;

import java.util.function.Consumer;

import net.damonj17.blockma.block.ModBlocks;
import net.damonj17.blockma.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.damonj17.blockma.block.custom.PointedPermafrostBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;

public class PermafrostHelper {
    public PermafrostHelper() {
    }

    protected static double scaleHeightFromRadius(double radius, double scale, double heightScale, double bluntness) {
        if (radius < bluntness) {
            radius = bluntness;
        }

        double d = 0.384;
        double e = radius / scale * 0.384;
        double f = 0.75 * Math.pow(e, 1.3333333333333333);
        double g = Math.pow(e, 0.6666666666666666);
        double h = 0.3333333333333333 * Math.log(e);
        double i = heightScale * (f - g - h);
        i = Math.max(i, 0.0);
        return i / 0.384 * scale;
    }

    protected static boolean canGenerateBase(StructureWorldAccess world, BlockPos pos, int height) {
        if (canGenerateOrLava(world, pos)) {
            return false;
        } else {
            float f = 6.0F;
            float g = 6.0F / (float)height;

            for(float h = 0.0F; h < 6.2831855F; h += g) {
                int i = (int)(MathHelper.cos(h) * (float)height);
                int j = (int)(MathHelper.sin(h) * (float)height);
                if (canGenerateOrLava(world, pos.add(i, 0, j))) {
                    return false;
                }
            }

            return true;
        }
    }

    protected static boolean canGenerate(WorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, net.damonj17.blockma.world.PermafrostHelper::canGenerate);
    }

    protected static boolean canGenerateOrLava(WorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, net.damonj17.blockma.world.PermafrostHelper::canGenerateOrLava);
    }

    protected static void getPermafrostThickness(Direction direction, int height, boolean merge, Consumer<BlockState> callback) {
        if (height >= 3) {
            callback.accept(getState(direction, Thickness.BASE));

            for(int i = 0; i < height - 3; ++i) {
                callback.accept(getState(direction, Thickness.MIDDLE));
            }
        }

        if (height >= 2) {
            callback.accept(getState(direction, Thickness.FRUSTUM));
        }

        if (height >= 1) {
            callback.accept(getState(direction, merge ? Thickness.TIP_MERGE : Thickness.TIP));
        }

    }

    protected static void generatePointedPermafrost(WorldAccess world, BlockPos pos, Direction direction, int height, boolean merge) {
        if (canReplace(world.getBlockState(pos.offset(direction.getOpposite())))) {
            BlockPos.Mutable mutable = pos.mutableCopy();
            getPermafrostThickness(direction, height, merge, (state) -> {
                if (state.isOf(ModBlocks.POINTED_PERMAFROST)) {
                    state = (BlockState)state.with(PointedPermafrostBlock.WATERLOGGED, world.isWater(mutable));
                }

                world.setBlockState(mutable, state, 2);
                mutable.move(direction);
            });
        }
    }

    protected static boolean generatePermafrostBlock(WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isIn(ModTags.Blocks.PERMAFROST_REPLACEABLE_BLOCKS)) {
            world.setBlockState(pos, ModBlocks.PERMAFROST.getDefaultState(), 2);
            return true;
        } else {
            return false;
        }
    }

    private static BlockState getState(Direction direction, Thickness thickness) {
        return (BlockState)((BlockState)ModBlocks.POINTED_PERMAFROST.getDefaultState().with(PointedPermafrostBlock.VERTICAL_DIRECTION, direction)).with(PointedPermafrostBlock.THICKNESS, thickness);
    }

    public static boolean canReplaceOrLava(BlockState state) {
        return canReplace(state) || state.isOf(Blocks.LAVA);
    }

    public static boolean canReplace(BlockState state) {
        return state.isOf(ModBlocks.PERMAFROST) || state.isIn(ModTags.Blocks.PERMAFROST_REPLACEABLE_BLOCKS);
    }

    public static boolean canGenerate(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER);
    }

    public static boolean cannotGenerate(BlockState state) {
        return !state.isAir() && !state.isOf(Blocks.WATER);
    }

    public static boolean canGenerateOrLava(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA);
    }
}
