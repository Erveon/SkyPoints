package me.laekh.skypoints.profiles;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {
	
	Map<UUID, SkyProfile> profiles;
	
	public ProfileManager() {
		profiles = new HashMap<UUID, SkyProfile>();
	}
	
	public SkyProfile getProfile(UUID uuid) {
		if(profiles.containsKey(uuid))
			return profiles.get(uuid);
		profiles.put(uuid, new SkyProfile(uuid));
		return profiles.get(uuid);
	}
	
	public SkyProfile getProfile(UUID uuid, String name) {
		if(profiles.containsKey(uuid))
			return profiles.get(uuid);
		profiles.put(uuid, new SkyProfile(uuid, name));
		return profiles.get(uuid);
	}
	
	public void unload(UUID uuid) {
		if(profiles.containsKey(uuid))
			profiles.remove(uuid);
	}

}
