package rndm_access.assorteddiscoveries.common.util;

import com.google.common.collect.ImmutableList;import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.HashMap;
import java.util.List;

public final class ADVoxelShapeHelper {
    private ADVoxelShapeHelper() {}

    public static HashMap<Direction, VoxelShape> makeShapeRotationMap(VoxelShape northShape) {
        HashMap<Direction, VoxelShape> shapes = new HashMap<>();

        for(Direction direction : Direction.values()) {
            if(direction.getAxis().isHorizontal()) {
                shapes.put(direction, rotate(northShape, direction));
            }
        }
        return shapes;
    }

    public static List<VoxelShape> makeShapeRotationList(VoxelShape northShape) {
        ImmutableList.Builder<VoxelShape> builder = ImmutableList.builder();

        for(Direction direction : Direction.values()) {
            if(direction.getAxis().isHorizontal()) {
                builder.add(rotate(northShape, direction));
            }
        }
        return builder.build();
    }

    private static VoxelShape rotate(VoxelShape source, Direction direction) {
        VoxelShape rotatedShape = VoxelShapes.empty();

        for(Box box : source.getBoundingBoxes()) {
            VoxelShape tempShape = rotateValues(direction, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
            rotatedShape = VoxelShapes.union(tempShape, rotatedShape);
        }
        return rotatedShape;
    }

    private static VoxelShape rotateValues(Direction direction, double minX, double minY, double minZ, double maxX,
                                           double maxY, double maxZ) {
        double tempMinX = minX;
        double tempMaxX = maxX;
        double tempMinZ = minZ;

        switch (direction) {
            case EAST -> {
                minX = 1.0F - maxZ;
                minZ = tempMinX;
                maxX = 1.0F - tempMinZ;
                maxZ = tempMaxX;
            }
            case SOUTH -> {
                minX = 1.0F - maxX;
                minZ = 1.0F - maxZ;
                maxX = 1.0F - tempMinX;
                maxZ = 1.0F - tempMinZ;
            }
            case WEST -> {
                minX = minZ;
                minZ = 1.0F - maxX;
                maxX = maxZ;
                maxZ = 1.0F - tempMinX;
            }
            default -> {}
        }
        return VoxelShapes.cuboid(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
