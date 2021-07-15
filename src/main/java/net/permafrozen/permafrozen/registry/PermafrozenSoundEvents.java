package net.permafrozen.permafrozen.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.Permafrozen;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermafrozenSoundEvents {
	private static final Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();
	
	public static final SoundEvent ENTITY_AURORA_FAE_AMBIENT = create("entity.aurora_fae.ambient");
	public static final SoundEvent ENTITY_AURORA_FAE_HURT = create("entity.aurora_fae.hurt");
	public static final SoundEvent ENTITY_AURORA_FAE_DEATH = create("entity.aurora_fae.death");
	
	private static SoundEvent create(String name) {
		Identifier id = new Identifier(Permafrozen.MOD_ID, name);
		SoundEvent soundEvent = new SoundEvent(id);
		SOUND_EVENTS.put(soundEvent, id);
		return soundEvent;
	}
	
	public static void init() {
		SOUND_EVENTS.keySet().forEach(effect -> Registry.register(Registry.SOUND_EVENT, SOUND_EVENTS.get(effect), effect));
	}
}