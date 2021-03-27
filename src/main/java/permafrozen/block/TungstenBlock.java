package permafrozen.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import permafrozen.Permafrozen;

public class TungstenBlock extends Block {

    public TungstenBlock() {

        super(Properties
                        .create(Material.IRON)
                        .hardnessAndResistance(5F, 6F)
                        .harvestTool(ToolType.PICKAXE)
                        .harvestLevel(1)
                        .sound(SoundType.METAL)
        );

        setRegistryName(Permafrozen.MODID, "tungsten_block");

    }

}
