package permafrozen.block;

import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import permafrozen.Permafrozen;

public class PolishedHertzstoneSlab extends SlabBlock {

    public PolishedHertzstoneSlab() {

        super(Properties
                .create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .sound(SoundType.STONE)
        );

        setRegistryName(Permafrozen.MODID, "polished_hertzstone_slab");

    }

}
