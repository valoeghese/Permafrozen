package permafrozen.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import permafrozen.Permafrozen;

public class ChilloriteIngot extends Item {

    public ChilloriteIngot() {

        super(new Properties().group(Permafrozen.ITEM_GROUP));
        setRegistryName("chillorite_ingot");
    }
}
