package permafrozen.registry;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import permafrozen.Permafrozen;
import permafrozen.item.*;
import permafrozen.item.tools.*;
import permafrozen.util.PermafrozenArmorMaterial;
import permafrozen.util.PermafrozenItemTier;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Permafrozen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    public static final DeferredRegister<Item> itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, Permafrozen.MOD_ID);

    // Declare all items in the mod

    // Mob things
    // broken atm; will implement as a bucket laterrrr  public static final RegistryObject<PermafrozenSpawnEgg> NUDIFAE_SPAWN_EGG = createItem("nudifae_spawn_egg", () -> new PermafrozenSpawnEgg(EntityRegistry.NUDIFAE.get(), -1, 1605609));

    public static final RegistryObject<Item> LUNAR_KOI           = createItem("lunar_koi", () -> new PermafrozenItem(new Item.Properties().food(Foods.LUNAR_KOI)));
    public static final RegistryObject<Item> LUNAR_KOI_BUCKET    = createItem("lunar_koi_bucket", () -> new PermafrozenMobBucket(EntityRegistry.LUNAR_KOI::get));

    public static final RegistryObject<Item> FATFISH           = createItem("fatfish", () -> new PermafrozenItem(new Item.Properties().food(Foods.FATFISH)));
    public static final RegistryObject<Item> FATFISH_BUCKET    = createItem("fatfish_bucket", () -> new PermafrozenMobBucket(EntityRegistry.FATFISH::get));

    // Wulfram
    public static final RegistryObject<Item> WULFRAM_INGOT       = createItem("wulfram_ingot", PermafrozenItem::new);
    public static final RegistryObject<Item> WULFRAM_NUGGET      = createItem("wulfram_nugget", PermafrozenItem::new);

    public static final RegistryObject<Item> WULFRAM_SWORD       = createItem("wulfram_sword", () -> new PermafrozenSword(PermafrozenItemTier.WULFRAM));
    public static final RegistryObject<Item> WULFRAM_PICKAXE     = createItem("wulfram_pickaxe", () -> new PermafrozenPickaxe(PermafrozenItemTier.WULFRAM));
    public static final RegistryObject<Item> WULFRAM_AXE         = createItem("wulfram_axe", () -> new PermafrozenAxe(PermafrozenItemTier.WULFRAM));
    public static final RegistryObject<Item> WULFRAM_SHOVEL      = createItem("wulfram_shovel", () -> new PermafrozenShovel(PermafrozenItemTier.WULFRAM));
    public static final RegistryObject<Item> WULFRAM_HOE         = createItem("wulfram_hoe", () -> new PermafrozenHoe(PermafrozenItemTier.WULFRAM));

    public static final RegistryObject<Item> WULFRAM_HELMET      = createItem("wulfram_helmet", () -> new PermafrozenArmor(PermafrozenArmorMaterial.WULFRAM, EquipmentSlotType.HEAD).setArmorTexture("wulfram_layer_1"));
    public static final RegistryObject<Item> WULFRAM_CHESTPLATE  = createItem("wulfram_chestplate", () -> new PermafrozenArmor(PermafrozenArmorMaterial.WULFRAM, EquipmentSlotType.CHEST).setArmorTexture("wulfram_layer_1"));
    public static final RegistryObject<Item> WULFRAM_LEGS        = createItem("wulfram_leggings", () -> new PermafrozenArmor(PermafrozenArmorMaterial.WULFRAM, EquipmentSlotType.LEGS).setArmorTexture("wulfram_layer_2"));
    public static final RegistryObject<Item> WULFRAM_BOOTS       = createItem("wulfram_boots", () -> new PermafrozenArmor(PermafrozenArmorMaterial.WULFRAM, EquipmentSlotType.FEET).setArmorTexture("wulfram_layer_1"));

    // Cryorite
    public static final RegistryObject<Item> FROZEN_SCRAPS       = createItem("frozen_scraps", PermafrozenItem::new);
    public static final RegistryObject<Item> CRYORITE_INGOT      = createItem("cryorite_ingot", PermafrozenItem::new);

    public static final RegistryObject<Item> CRYORITE_SWORD      = createItem("cryorite_sword", () -> new PermafrozenSword(PermafrozenItemTier.CRYORITE));
    public static final RegistryObject<Item> CRYORITE_PICKAXE    = createItem("cryorite_pickaxe", () -> new PermafrozenPickaxe(PermafrozenItemTier.CRYORITE));
    public static final RegistryObject<Item> CRYORITE_AXE        = createItem("cryorite_axe", () -> new PermafrozenAxe(PermafrozenItemTier.CRYORITE));
    public static final RegistryObject<Item> CRYORITE_SHOVEL     = createItem("cryorite_shovel", () -> new PermafrozenShovel(PermafrozenItemTier.CRYORITE));
    public static final RegistryObject<Item> CRYORITE_HOE        = createItem("cryorite_hoe", () -> new PermafrozenHoe(PermafrozenItemTier.CRYORITE));

    public static final RegistryObject<Item> CRYORITE_HELMET     = createItem("cryorite_helmet", () -> new PermafrozenArmor(PermafrozenArmorMaterial.CRYORITE, EquipmentSlotType.HEAD).setArmorTexture("cryorite_layer_1"));
    public static final RegistryObject<Item> CRYORITE_CHESTPLATE = createItem("cryorite_chestplate", () -> new PermafrozenArmor(PermafrozenArmorMaterial.CRYORITE, EquipmentSlotType.CHEST).setArmorTexture("cryorite_layer_1"));
    public static final RegistryObject<Item> CRYORITE_LEGS       = createItem("cryorite_leggings", () -> new PermafrozenArmor(PermafrozenArmorMaterial.CRYORITE, EquipmentSlotType.LEGS).setArmorTexture("cryorite_layer_2"));
    public static final RegistryObject<Item> CRYORITE_BOOTS      = createItem("cryorite_boots", () -> new PermafrozenArmor(PermafrozenArmorMaterial.CRYORITE, EquipmentSlotType.FEET).setArmorTexture("cryorite_layer_1"));

    // Cobalt
    public static final RegistryObject<Item> COBALT_NUGGET       = createItem("cobalt_nugget", PermafrozenItem::new);
    public static final RegistryObject<Item> COBALT_INGOT        = createItem( "cobalt_ingot", PermafrozenItem::new);

    public static class Foods {

        public static final Food LUNAR_KOI = new Food.Builder().hunger(3).saturation(0.1F).effect(() -> new EffectInstance(Effects.NAUSEA, 1200, 255), 1.0F).build();
        public static final Food FATFISH = new Food.Builder().hunger(4).saturation(0.4F).build();

    }

    // Make things a lil bit prettier
    public static <I extends Item> RegistryObject<I> createItem(String name, Supplier<? extends I> supplier) {

        return itemRegister.register(name, supplier);

    }

    public static void register(IEventBus eventBus) {

        itemRegister.register(eventBus);

    }

    /*@SuppressWarnings("unchecked")
    private void registerSpawnEggs(RegistryEvent.Register<EntityType<?>> event) {
        if (!this.spawnEggs.isEmpty()) {
            try {
                Map<EntityType<?>, SpawnEggItem> map = (Map<EntityType<?>, SpawnEggItem>) EGGS_FIELD.get(null);
                this.spawnEggs.forEach(egg -> map.put(egg.getType(null), egg));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }*/


}