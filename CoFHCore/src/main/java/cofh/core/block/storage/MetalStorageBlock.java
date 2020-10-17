package cofh.core.block.storage;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class MetalStorageBlock extends Block {

    public MetalStorageBlock(int harvestLevel) {

        this(Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE));
    }

    public MetalStorageBlock(MaterialColor color, int harvestLevel) {

        this(Properties.create(Material.IRON, color).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE));
    }

    public MetalStorageBlock(Properties builder) {

        super(builder);
    }

}
