package net.damonj17.blockma.block.custom;

import com.google.common.annotations.VisibleForTesting;
import net.damonj17.blockma.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PointedPermafrostBlock extends Block implements Falling, Waterloggable {

    public static final EnumProperty<Direction> VERTICAL_DIRECTION = Properties.VERTICAL_DIRECTION;
    public static final EnumProperty<Thickness> THICKNESS = Properties.THICKNESS;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final int MAX_STALACTITE_GROWTH = 7;
    private static final int STALACTITE_FLOOR_SEARCH_RANGE = 10;
    private static final VoxelShape TIP_MERGE_SHAPE = Block.createColumnShape(6.0, 0.0, 16.0);
    private static final VoxelShape UP_TIP_SHAPE = Block.createColumnShape(6.0, 0.0, 11.0);
    private static final VoxelShape DOWN_TIP_SHAPE = Block.createColumnShape(6.0, 5.0, 16.0);
    private static final VoxelShape BASE_SHAPE = Block.createColumnShape(8.0, 0.0, 16.0);
    private static final VoxelShape FRUSTUM_SHAPE = Block.createColumnShape(10.0, 0.0, 16.0);
    private static final VoxelShape MIDDLE_SHAPE = Block.createColumnShape(12.0, 0.0, 16.0);
    private static final double DOWN_TIP_Y = DOWN_TIP_SHAPE.getMin(Direction.Axis.Y);
    private static final float MAX_HORIZONTAL_MODEL_OFFSET = (float)MIDDLE_SHAPE.getMin(Direction.Axis.X);
    private static final VoxelShape DRIP_COLLISION_SHAPE = Block.createColumnShape(4.0, 0.0, 16.0);


    public PointedPermafrostBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(VERTICAL_DIRECTION, Direction.UP).with(THICKNESS, Thickness.TIP).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAtWithDirection(world, pos, state.get(VERTICAL_DIRECTION));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state,
            WorldView world,
            ScheduledTickView tickView,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            Random random
    ) {
        if ((Boolean)state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        } else {
            Direction direction2 = state.get(VERTICAL_DIRECTION);
            if (direction2 == Direction.DOWN && tickView.getBlockTickScheduler().isQueued(pos, this)) {
                return state;
            } else if (direction == direction2.getOpposite() && !this.canPlaceAt(state, world, pos)) {
                if (direction2 == Direction.DOWN) {
                    tickView.scheduleBlockTick(pos, this, 2);
                } else {
                    tickView.scheduleBlockTick(pos, this, 1);
                }

                return state;
            } else {
                boolean bl = state.get(THICKNESS) == Thickness.TIP_MERGE;
                Thickness thickness = getThickness(world, pos, direction2, bl);
                return state.with(THICKNESS, thickness);
            }
        }
    }

    @Override
    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            if (world instanceof ServerWorld serverWorld
                    && projectile.canModifyAt(serverWorld, blockPos)
                    && projectile.canBreakBlocks(serverWorld)
                    && projectile instanceof TridentEntity
                    && projectile.getVelocity().length() > 0.6) {
                world.breakBlock(blockPos, true);
            }
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (state.get(VERTICAL_DIRECTION) == Direction.UP && state.get(THICKNESS) == Thickness.TIP) {
            entity.handleFallDamage(fallDistance + 2.5, 2.0F, world.getDamageSources().stalagmite());
        } else {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.12F)) {
                getFluid(world, pos, state).filter(fluid -> f < 0.02F || isFluidLiquid(fluid.fluid)).ifPresent(fluid -> createParticle(world, pos, state, fluid.fluid));
            }
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isPointingUp(state) && !this.canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
        } else {
            spawnFallingBlock(state, world, pos);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        dripTick(state, world, pos, random.nextFloat());
        if (random.nextFloat() < 0.011377778F && isHeldByPointedPermafrost(state, world, pos)) {
            tryGrow(state, world, pos, random);
        }
    }

    @VisibleForTesting
    public static void dripTick(BlockState state, ServerWorld world, BlockPos pos, float dripChance) {
        if (!(dripChance > 0.17578125F) || !(dripChance > 0.05859375F)) {
            if (isHeldByPointedPermafrost(state, world, pos)) {
                Optional<PointedPermafrostBlock.DrippingFluid> optional = getFluid(world, pos, state);
                if (!optional.isEmpty()) {
                    Fluid fluid = ((PointedPermafrostBlock.DrippingFluid)optional.get()).fluid;
                    float f;
                    if (fluid == Fluids.WATER) {
                        f = 0.17578125F;
                    } else {
                        if (fluid != Fluids.LAVA) {
                            return;
                        }

                        f = 0.05859375F;
                    }

                    if (!(dripChance >= f)) {
                        BlockPos blockPos = getTipPos(state, world, pos, 11, false);
                        if (blockPos != null) {
                            if (((PointedPermafrostBlock.DrippingFluid)optional.get()).sourceState.isOf(Blocks.MUD) && fluid == Fluids.WATER) {
                                BlockState blockState = Blocks.CLAY.getDefaultState();
                                world.setBlockState(((PointedPermafrostBlock.DrippingFluid)optional.get()).pos, blockState);
                                Block.pushEntitiesUpBeforeBlockChange(
                                        ((PointedPermafrostBlock.DrippingFluid)optional.get()).sourceState, blockState, world, ((PointedPermafrostBlock.DrippingFluid)optional.get()).pos
                                );
                                world.emitGameEvent(GameEvent.BLOCK_CHANGE, ((PointedPermafrostBlock.DrippingFluid)optional.get()).pos, GameEvent.Emitter.of(blockState));
                                world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS, blockPos, 0);
                            } else {

                            }
                        }
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction direction = ctx.getVerticalPlayerLookDirection().getOpposite();
        Direction direction2 = getDirectionToPlaceAt(worldAccess, blockPos, direction);
        if (direction2 == null) {
            return null;
        } else {
            boolean bl = !ctx.shouldCancelInteraction();
            Thickness thickness = getThickness(worldAccess, blockPos, direction2, bl);
            return thickness == null
                    ? null
                    : this.getDefaultState()
                    .with(VERTICAL_DIRECTION, direction2)
                    .with(THICKNESS, thickness)
                    .with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER);
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state) {
        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape voxelShape = switch ((Thickness)state.get(THICKNESS)) {
            case TIP_MERGE -> TIP_MERGE_SHAPE;
            case TIP -> state.get(VERTICAL_DIRECTION) == Direction.DOWN ? DOWN_TIP_SHAPE : UP_TIP_SHAPE;
            case FRUSTUM -> BASE_SHAPE;
            case MIDDLE -> FRUSTUM_SHAPE;
            case BASE -> MIDDLE_SHAPE;
        };
        return voxelShape.offset(state.getModelOffset(pos));
    }

    @Override
    protected boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    protected float getMaxHorizontalModelOffset() {
        return MAX_HORIZONTAL_MODEL_OFFSET;
    }

    @Override
    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_LANDS, pos, 0);
        }
    }

    @Override
    public DamageSource getDamageSource(Entity attacker) {
        return attacker.getDamageSources().fallingStalactite(attacker);
    }

    private static void spawnFallingBlock(BlockState state, ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        BlockState blockState = state;

        while (isPointingDown(blockState)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, mutable, blockState);
            if (isTip(blockState, true)) {
                int i = Math.max(1 + pos.getY() - mutable.getY(), 6);
                float f = 1.0F * i;
                fallingBlockEntity.setHurtEntities(f, 40);
                break;
            }

            mutable.move(Direction.DOWN);
            blockState = world.getBlockState(mutable);
        }
    }

    @VisibleForTesting
    public static void tryGrow(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.up(1));
        BlockState blockState2 = world.getBlockState(pos.up(2));
        if (canGrow(blockState, blockState2)) {
            BlockPos blockPos = getTipPos(state, world, pos, 7, false);
            if (blockPos != null) {
                BlockState blockState3 = world.getBlockState(blockPos);
                if (canDrip(blockState3) && canGrow(blockState3, world, blockPos)) {
                    if (random.nextBoolean()) {
                        tryGrow(world, blockPos, Direction.DOWN);
                    } else {
                        tryGrowStalagmite(world, blockPos);
                    }
                }
            }
        }
    }

    private static void tryGrowStalagmite(ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 0; i < 10; i++) {
            mutable.move(Direction.DOWN);
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.getFluidState().isEmpty()) {
                return;
            }

            if (isTip(blockState, Direction.UP) && canGrow(blockState, world, mutable)) {
                tryGrow(world, mutable, Direction.UP);
                return;
            }

            if (canPlaceAtWithDirection(world, mutable, Direction.UP) && !world.isWater(mutable.down())) {
                tryGrow(world, mutable.down(), Direction.UP);
                return;
            }

            if (!canDripThrough(world, mutable, blockState)) {
                return;
            }
        }
    }

    private static void tryGrow(ServerWorld world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (isTip(blockState, direction.getOpposite())) {
            growMerged(blockState, world, blockPos);
        } else if (blockState.isAir() || blockState.isOf(Blocks.WATER)) {
            place(world, blockPos, direction, Thickness.TIP);
        }
    }

    private static void place(WorldAccess world, BlockPos pos, Direction direction, Thickness thickness) {
        BlockState blockState = ModBlocks.POINTED_PERMAFROST
                .getDefaultState()
                .with(VERTICAL_DIRECTION, direction)
                .with(THICKNESS, thickness)
                .with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
        world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
    }

    private static void growMerged(BlockState state, WorldAccess world, BlockPos pos) {
        BlockPos blockPos2;
        BlockPos blockPos;
        if (state.get(VERTICAL_DIRECTION) == Direction.UP) {
            blockPos = pos;
            blockPos2 = pos.up();
        } else {
            blockPos2 = pos;
            blockPos = pos.down();
        }

        place(world, blockPos2, Direction.DOWN, Thickness.TIP_MERGE);
        place(world, blockPos, Direction.UP, Thickness.TIP_MERGE);
    }

    public static void createParticle(World world, BlockPos pos, BlockState state) {
        getFluid(world, pos, state).ifPresent(fluid -> createParticle(world, pos, state, fluid.fluid));
    }

    private static void createParticle(World world, BlockPos pos, BlockState state, Fluid fluid) {
        Vec3d vec3d = state.getModelOffset(pos);
        double d = 0.0625;
        double e = pos.getX() + 0.5 + vec3d.x;
        double f = pos.getY() + DOWN_TIP_Y - 0.0625;
        double g = pos.getZ() + 0.5 + vec3d.z;
        Fluid fluid2 = getDripFluid(world, fluid);
        ParticleEffect particleEffect = fluid2.isIn(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        world.addParticleClient(particleEffect, e, f, g, 0.0, 0.0, 0.0);
    }

    @Nullable
    private static BlockPos getTipPos(BlockState state, WorldAccess world, BlockPos pos, int range, boolean allowMerged) {
        if (isTip(state, allowMerged)) {
            return pos;
        } else {
            Direction direction = state.get(VERTICAL_DIRECTION);
            BiPredicate<BlockPos, BlockState> biPredicate = (posx, statex) -> statex.isOf(ModBlocks.POINTED_PERMAFROST) && statex.get(VERTICAL_DIRECTION) == direction;
            return (BlockPos)searchInDirection(world, pos, direction.getDirection(), biPredicate, statex -> isTip(statex, allowMerged), range).orElse(null);
        }
    }

    @Nullable
    private static Direction getDirectionToPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        Direction direction2;
        if (canPlaceAtWithDirection(world, pos, direction)) {
            direction2 = direction;
        } else {
            if (!canPlaceAtWithDirection(world, pos, direction.getOpposite())) {
                return null;
            }

            direction2 = direction.getOpposite();
        }

        return direction2;
    }

    private static Thickness getThickness(WorldView world, BlockPos pos, Direction direction, boolean tryMerge) {
        Direction direction2 = direction.getOpposite();
        BlockState blockState = world.getBlockState(pos.offset(direction));
        if (isPointedPermafrostFacingDirection(blockState, direction2)) {
            return !tryMerge && blockState.get(THICKNESS) != Thickness.TIP_MERGE ? Thickness.TIP : Thickness.TIP_MERGE;
        } else if (!isPointedPermafrostFacingDirection(blockState, direction)) {
            return Thickness.TIP;
        } else {
            Thickness thickness = blockState.get(THICKNESS);
            if (thickness != Thickness.TIP && thickness != Thickness.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.offset(direction2));
                return !isPointedPermafrostFacingDirection(blockState2, direction) ? Thickness.BASE : Thickness.MIDDLE;
            } else {
                return Thickness.FRUSTUM;
            }
        }
    }

    public static boolean canDrip(BlockState state) {
        return isPointingDown(state) && state.get(THICKNESS) == Thickness.TIP && !(Boolean)state.get(WATERLOGGED);
    }

    private static boolean canGrow(BlockState state, ServerWorld world, BlockPos pos) {
        Direction direction = state.get(VERTICAL_DIRECTION);
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockState.isAir() ? true : isTip(blockState, direction.getOpposite());
        }
    }

    private static Optional<BlockPos> getSupportingPos(World world, BlockPos pos, BlockState state, int range) {
        Direction direction = state.get(VERTICAL_DIRECTION);
        BiPredicate<BlockPos, BlockState> biPredicate = (posx, statex) -> statex.isOf(ModBlocks.POINTED_PERMAFROST) && statex.get(VERTICAL_DIRECTION) == direction;
        return searchInDirection(world, pos, direction.getOpposite().getDirection(), biPredicate, statex -> !statex.isOf(ModBlocks.POINTED_PERMAFROST), range);
    }

    private static boolean canPlaceAtWithDirection(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, direction) || isPointedPermafrostFacingDirection(blockState, direction);
    }

    private static boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.isOf(ModBlocks.POINTED_PERMAFROST)) {
            return false;
        } else {
            Thickness thickness = state.get(THICKNESS);
            return thickness == Thickness.TIP || allowMerged && thickness == Thickness.TIP_MERGE;
        }
    }

    private static boolean isTip(BlockState state, Direction direction) {
        return isTip(state, false) && state.get(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isPointingDown(BlockState state) {
        return isPointedPermafrostFacingDirection(state, Direction.DOWN);
    }

    private static boolean isPointingUp(BlockState state) {
        return isPointedPermafrostFacingDirection(state, Direction.UP);
    }

    private static boolean isHeldByPointedPermafrost(BlockState state, WorldView world, BlockPos pos) {
        return isPointingDown(state) && !world.getBlockState(pos.up()).isOf(ModBlocks.POINTED_PERMAFROST);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    private static boolean isPointedPermafrostFacingDirection(BlockState state, Direction direction) {
        return state.isOf(ModBlocks.POINTED_PERMAFROST) && state.get(VERTICAL_DIRECTION) == direction;
    }

    @Nullable
    public static BlockPos getDripPos(World world, BlockPos pos) {
        BiPredicate<BlockPos, BlockState> biPredicate = (posx, state) -> canDripThrough(world, posx, state);
        return (BlockPos)searchInDirection(world, pos, Direction.UP.getDirection(), biPredicate, PointedPermafrostBlock::canDrip, 11).orElse(null);
    }

    public static Fluid getDripFluid(ServerWorld world, BlockPos pos) {
        return (Fluid)getFluid(world, pos, world.getBlockState(pos)).map(fluid -> fluid.fluid).filter(PointedPermafrostBlock::isFluidLiquid).orElse(Fluids.EMPTY);
    }

    private static Optional<PointedPermafrostBlock.DrippingFluid> getFluid(World world, BlockPos pos, BlockState state) {
        return !isPointingDown(state) ? Optional.empty() : getSupportingPos(world, pos, state, 11).map(posx -> {
            BlockPos blockPos = posx.up();
            BlockState blockState = world.getBlockState(blockPos);
            Fluid fluid;
            if (blockState.isOf(Blocks.MUD) && !world.getDimension().ultrawarm()) {
                fluid = Fluids.WATER;
            } else {
                fluid = world.getFluidState(blockPos).getFluid();
            }

            return new PointedPermafrostBlock.DrippingFluid(blockPos, fluid, blockState);
        });
    }

    /**
     * {@return whether the provided {@code fluid} is liquid, namely lava or water}
     */
    private static boolean isFluidLiquid(Fluid fluid) {
        return fluid == Fluids.LAVA || fluid == Fluids.WATER;
    }

    private static boolean canGrow(BlockState PermafrostBlockState, BlockState waterState) {
        return PermafrostBlockState.isOf(ModBlocks.PERMAFROST) && waterState.isOf(Blocks.WATER) && waterState.getFluidState().isStill();
    }

    private static Fluid getDripFluid(World world, Fluid fluid) {
        if (fluid.matchesType(Fluids.EMPTY)) {
            return world.getDimension().ultrawarm() ? Fluids.LAVA : Fluids.WATER;
        } else {
            return fluid;
        }
    }

    private static Optional<BlockPos> searchInDirection(
            WorldAccess world,
            BlockPos pos,
            Direction.AxisDirection direction,
            BiPredicate<BlockPos, BlockState> continuePredicate,
            Predicate<BlockState> stopPredicate,
            int range
    ) {
        Direction direction2 = Direction.get(direction, Direction.Axis.Y);
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 1; i < range; i++) {
            mutable.move(direction2);
            BlockState blockState = world.getBlockState(mutable);
            if (stopPredicate.test(blockState)) {
                return Optional.of(mutable.toImmutable());
            }

            if (world.isOutOfHeightLimit(mutable.getY()) || !continuePredicate.test(mutable, blockState)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    /**
     * {@return whether it can drip through the block {@code block} at {@code pos}}
     *
     * @apiNote This is used for checking which block can obstruct the stalagmites
     * growing or the cauldrons filling with liquids.
     */
    private static boolean canDripThrough(BlockView world, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        } else if (state.isOpaqueFullCube()) {
            return false;
        } else if (!state.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape voxelShape = state.getCollisionShape(world, pos);
            return !VoxelShapes.matchesAnywhere(DRIP_COLLISION_SHAPE, voxelShape, BooleanBiFunction.AND);
        }
    }

    record DrippingFluid(BlockPos pos, Fluid fluid, BlockState sourceState) {
    }
}
