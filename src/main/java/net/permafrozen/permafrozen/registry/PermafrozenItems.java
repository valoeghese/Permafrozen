package net.permafrozen.permafrozen.registry;

import com.terraformersmc.terraform.boat.TerraformBoatItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;
import net.permafrozen.permafrozen.item.PermafrozenArmorMaterial;
import net.permafrozen.permafrozen.item.PermafrozenAxeItem;
import net.permafrozen.permafrozen.item.PermafrozenHoeItem;
import net.permafrozen.permafrozen.item.PermafrozenPickaxeItem;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.permafrozen.permafrozen.registry.PermafrozenArmorMaterials.CRYORITE_ARMOR_MATERIAL;
import static net.permafrozen.permafrozen.registry.PermafrozenArmorMaterials.WULFRAM_ARMOR_MATERIAL;

public class PermafrozenItems {
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();



	public static final Item CRYORITE_INGOT = create("cryorite_ingot", new Item(new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_SWORD = create("cryorite_sword", new SwordItem(PermafrozenMaterials.CRYORITE, 3, -2.4f, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_SHOVEL = create("cryorite_shovel", new ShovelItem(PermafrozenMaterials.CRYORITE, 0.5f, -3, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_AXE = create("cryorite_axe", new PermafrozenAxeItem(PermafrozenMaterials.CRYORITE, 4.5f, -3, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_PICKAXE = create("cryorite_pickaxe", new PermafrozenPickaxeItem(PermafrozenMaterials.CRYORITE, 1, -3, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_HOE = create("cryorite_hoe", new PermafrozenHoeItem(PermafrozenMaterials.CRYORITE, -3, 0, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item WULFRAM_INGOT = create("wulfram_ingot", new Item(new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item WULFRAM_SWORD = create("wulfram_sword", new SwordItem(PermafrozenMaterials.WULFRAM, 3, -2.4f, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item WULFRAM_SHOVEL = create("wulfram_shovel", new ShovelItem(PermafrozenMaterials.WULFRAM, 0.5f, -3, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item WULFRAM_AXE = create("wulfram_axe", new PermafrozenAxeItem(PermafrozenMaterials.WULFRAM, 4.5f, -3, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item WULFRAM_PICKAXE = create("wulfram_pickaxe", new PermafrozenPickaxeItem(PermafrozenMaterials.WULFRAM, 1, -3, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item WULFRAM_HOE = create("wulfram_hoe", new PermafrozenHoeItem(PermafrozenMaterials.WULFRAM, -1, 0, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item LUNAR_KOI_BUCKET = create("lunar_koi_bucket", new EntityBucketItem(PermafrozenEntities.LUNAR_KOI, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item LUNAR_KOI = create("lunar_koi", new Item((new Item.Settings()).group(Permafrozen.GROUP).food(FoodComponents.SALMON)));
	public static final Item FATFISH_BUCKET = create("fatfish_bucket", new EntityBucketItem(PermafrozenEntities.FATFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item FATFISH = create("fatfish", new Item((new Item.Settings()).group(Permafrozen.GROUP).food(FoodComponents.COD)));
	public static final Item FIR_PINECONE = create("fir_cone", new Item(new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item FIR_DOOR_ITEM = create("fir_door", new TallBlockItem(PermafrozenBlocks.FIR_DOOR, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item FIR_SIGN_ITEM = create("fir_sign", new SignItem(new Item.Settings().group(Permafrozen.GROUP).maxCount(16), PermafrozenBlocks.FIR_SIGN, PermafrozenBlocks.FIR_WALL_SIGN));
	public static final TerraformBoatItem FIR_BOAT = create("fir_boat", new TerraformBoatItem(() -> PermafrozenEntities.FIR_BOAT, new Item.Settings().group(Permafrozen.GROUP).maxCount(1)));
	public static final Item NUDIFAE_SPAWN_EGG = create("nudifae_spawn_egg", new SpawnEggItem(PermafrozenEntities.NUDIFAE, 0xb3e5fc, 0x0090ea, (new Item.Settings()).group(Permafrozen.GROUP)));
	public static final Item LUNAR_KOI_SPAWN_EGG = create("lunar_koi_spawn_egg", new SpawnEggItem(PermafrozenEntities.LUNAR_KOI, 0x25dbe4, 0xdefafc, (new Item.Settings()).group(Permafrozen.GROUP)));
	public static final Item FATFISH_SPAWN_EGG = create("fatfish_spawn_egg", new SpawnEggItem(PermafrozenEntities.FATFISH, 0x58705f, 0x655b7c, (new Item.Settings()).group(Permafrozen.GROUP)));
	public static final Item AURORA_FAE_SPAWN_EGG = create("aurora_fae_spawn_egg", new SpawnEggItem(PermafrozenEntities.AURORA_FAE, 0x1de9b6, 0xd400f9, (new Item.Settings()).group(Permafrozen.GROUP)));
	public static final Item WULFRAM_HELMET = create( "wulfram_helmet", new ArmorItem(WULFRAM_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item WULFRAM_CHESTPLATE = create( "wulfram_chestplate", new ArmorItem(WULFRAM_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item WULFRAM_LEGGINGS = create( "wulfram_leggings", new ArmorItem(WULFRAM_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item WULFRAM_BOOTS = create( "wulfram_boots", new ArmorItem(WULFRAM_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_HELMET = create( "cryorite_helmet", new ArmorItem(CRYORITE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_CHESTPLATE = create( "cryorite_chestplate", new ArmorItem(CRYORITE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_LEGGINGS = create( "cryorite_leggings", new ArmorItem(CRYORITE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(Permafrozen.GROUP)));
	public static final Item CRYORITE_BOOTS = create( "cryorite_boots", new ArmorItem(CRYORITE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(Permafrozen.GROUP)));


	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, new Identifier(Permafrozen.MOD_ID, name));
		return item;
	}
	
	public static void init() {
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}