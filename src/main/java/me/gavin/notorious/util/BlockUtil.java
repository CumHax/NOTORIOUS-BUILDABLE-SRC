package me.gavin.notorious.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.mixin.mixins.accessor.IMinecraftMixin;
import me.gavin.notorious.stuff.IMinecraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;

public class BlockUtil implements IMinecraft {

    public static List emptyBlocks = Arrays.asList(new Block[] { Blocks.AIR, Blocks.FLOWING_LAVA, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, Blocks.TALLGRASS, Blocks.FIRE});
    public static List rightclickableBlocks = Arrays.asList(new Block[] { Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, Blocks.UNPOWERED_COMPARATOR, Blocks.UNPOWERED_REPEATER, Blocks.POWERED_REPEATER, Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, Blocks.BEACON, Blocks.BED, Blocks.FURNACE, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE});

    public static ArrayList getSurroundingBlocks(int radius, boolean motion) {
        ArrayList posList = new ArrayList();
        BlockPos playerPos = BlockUtil.mc.player.getPosition().add(0, 1, 0);

        if (motion) {
            playerPos.add(BlockUtil.mc.player.motionX, BlockUtil.mc.player.motionY, BlockUtil.mc.player.motionZ);
        }

        for (int x = -radius; x < radius; ++x) {
            for (int y = -radius; y < radius; ++y) {
                for (int z = -radius; z < radius; ++z) {
                    posList.add((new BlockPos(x, y, z)).add(playerPos));
                }
            }
        }

        return posList;
    }

    public static BlockPos isCityable(EntityPlayer player, boolean end_crystal) {
        BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);

        if (BlockUtil.mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.north();
            }

            if (BlockUtil.mc.world.getBlockState(pos.north().north()).getBlock() == Blocks.AIR) {
                return pos.north();
            }
        }

        if (BlockUtil.mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.east();
            }

            if (BlockUtil.mc.world.getBlockState(pos.east().east()).getBlock() == Blocks.AIR) {
                return pos.east();
            }
        }

        if (BlockUtil.mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.south();
            }

            if (BlockUtil.mc.world.getBlockState(pos.south().south()).getBlock() == Blocks.AIR) {
                return pos.south();
            }
        }

        if (BlockUtil.mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN) {
            if (end_crystal) {
                return pos.west();
            }

            if (BlockUtil.mc.world.getBlockState(pos.west().west()).getBlock() == Blocks.AIR) {
                return pos.west();
            }
        }

        return null;
    }

    public static ArrayList getSurroundingBlocksOtherPlayers(int radius, boolean motion) {
        Iterator iterator = BlockUtil.mc.world.loadedEntityList.iterator();

        if (!iterator.hasNext()) {
            return null;
        } else {
            Entity e = (Entity) iterator.next();
            ArrayList posList = new ArrayList();
            BlockPos playerPos = e.getPosition().add(0, 1, 0);

            if (motion) {
                playerPos.add(e.motionX, e.motionY, e.motionZ);
            }

            for (int x = -radius; x < radius; ++x) {
                for (int y = -radius; y < radius; ++y) {
                    for (int z = -radius; z < radius; ++z) {
                        posList.add((new BlockPos(x, y, z)).add(playerPos));
                    }
                }
            }

            return posList;
        }
    }

    public static void damageBlock(BlockPos position, boolean packet, boolean rotations) {
        damageBlock(position, EnumFacing.getDirectionFromEntityLiving(position, BlockUtil.mc.player), packet, rotations);
    }

    public static void damageBlock(BlockPos position, EnumFacing facing, boolean packet, boolean rotations) {
        if (rotations) {
            float[] r = MathUtil.calculateLookAt((double) position.getX() + 0.5D, (double) position.getY() + 0.5D, (double) position.getZ() + 0.5D, BlockUtil.mc.player);

            Notorious.INSTANCE.rotationManager.desiredYaw = r[0];
            Notorious.INSTANCE.rotationManager.desiredPitch = r[1];
        }

        BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        if (packet) {
            BlockUtil.mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.START_DESTROY_BLOCK, position, facing));
            BlockUtil.mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.STOP_DESTROY_BLOCK, position, facing));
        } else if (BlockUtil.mc.getConnection().getPlayerInfo(BlockUtil.mc.player.getUniqueID()).getGameType() == GameType.CREATIVE) {
            BlockUtil.mc.playerController.clickBlock(position, facing);
        } else {
            BlockUtil.mc.playerController.onPlayerDamageBlock(position, facing);
        }

    }

    public static void rotatePacket(double x, double y, double z) {
        double diffX = x - BlockUtil.mc.player.posX;
        double diffY = y - (BlockUtil.mc.player.posY + (double) BlockUtil.mc.player.getEyeHeight());
        double diffZ = z - BlockUtil.mc.player.posZ;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));

        BlockUtil.mc.player.connection.sendPacket(new Rotation(yaw, pitch, BlockUtil.mc.player.onGround));
    }

    public static int isPositionPlaceable(BlockPos pos, boolean rayTrace, boolean entityCheck) {
        Block block = BlockUtil.mc.world.getBlockState(pos).getBlock();

        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) {
            return 0;
        } else if (!rayTracePlaceCheck(pos, rayTrace, 0.0F)) {
            return -1;
        } else {
            Iterator iterator;

            if (entityCheck) {
                iterator = BlockUtil.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos)).iterator();

                while (iterator.hasNext()) {
                    Entity side = (Entity) iterator.next();

                    if (!(side instanceof EntityItem) && !(side instanceof EntityXPOrb)) {
                        return 1;
                    }
                }
            }

            iterator = getPossibleSides(pos).iterator();

            EnumFacing side1;

            do {
                if (!iterator.hasNext()) {
                    return 2;
                }

                side1 = (EnumFacing) iterator.next();
            } while (!canBeClicked(pos.offset(side1)));

            return 3;
        }
    }

    public static boolean placeBlock(BlockPos pos, EnumHand hand, boolean rotate, boolean packet, boolean isSneaking) {
        boolean sneaking = false;
        EnumFacing side = getFirstFacing(pos);

        if (side == null) {
            return isSneaking;
        } else {
            BlockPos neighbour = pos.offset(side);
            EnumFacing opposite = side.getOpposite();
            Vec3d hitVec = (new Vec3d(neighbour)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(opposite.getDirectionVec())).scale(0.5D));
            Block neighbourBlock = BlockUtil.mc.world.getBlockState(neighbour).getBlock();

            if (!BlockUtil.mc.player.isSneaking() && (BlockUtil.emptyBlocks.contains(neighbourBlock) || BlockUtil.rightclickableBlocks.contains(neighbourBlock))) {
                BlockUtil.mc.player.connection.sendPacket(new CPacketEntityAction(BlockUtil.mc.player, net.minecraft.network.play.client.CPacketEntityAction.Action.START_SNEAKING));
                BlockUtil.mc.player.setSneaking(true);
                sneaking = true;
            }

            if (rotate) {
                RotationUtil.faceVector(hitVec, true);
            }

            rightClickBlock(neighbour, hitVec, hand, opposite, packet);
            BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
            ((IMinecraftMixin) BlockUtil.mc).setRightClickDelayTimerAccessor(4);
            return sneaking || isSneaking;
        }
    }

    public static void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
        if (packet) {
            float f = (float) (vec.x - (double) pos.getX());
            float f1 = (float) (vec.y - (double) pos.getY());
            float f2 = (float) (vec.z - (double) pos.getZ());

            BlockUtil.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
        } else {
            BlockUtil.mc.playerController.processRightClickBlock(BlockUtil.mc.player, BlockUtil.mc.world, pos, direction, vec, hand);
        }

        BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        ((IMinecraftMixin) BlockUtil.mc).setRightClickDelayTimerAccessor(4);
    }

    public static EnumFacing getFirstFacing(BlockPos pos) {
        Iterator iterator = getPossibleSides(pos).iterator();

        if (iterator.hasNext()) {
            EnumFacing facing = (EnumFacing) iterator.next();

            return facing;
        } else {
            return null;
        }
    }

    public static int isPositionPlaceable(BlockPos pos, boolean rayTrace) {
        return isPositionPlaceable(pos, rayTrace, true);
    }

    public static List getPossibleSides(BlockPos pos) {
        ArrayList facings = new ArrayList();

        if (BlockUtil.mc.world != null && pos != null) {
            EnumFacing[] aenumfacing = EnumFacing.values();
            int i = aenumfacing.length;

            for (int j = 0; j < i; ++j) {
                EnumFacing side = aenumfacing[j];
                BlockPos neighbour = pos.offset(side);
                IBlockState blockState = BlockUtil.mc.world.getBlockState(neighbour);

                if (blockState != null && blockState.getBlock().canCollideCheck(blockState, false) && !blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }

            return facings;
        } else {
            return facings;
        }
    }

    public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck, float height) {
        return !shouldCheck || BlockUtil.mc.world.rayTraceBlocks(new Vec3d(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY + (double) BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ), new Vec3d((double) pos.getX(), (double) ((float) pos.getY() + height), (double) pos.getZ()), false, true, false) == null;
    }

    public static boolean canBeClicked(BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }

    private static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    private static IBlockState getState(BlockPos pos) {
        return BlockUtil.mc.world.getBlockState(pos);
    }
}
