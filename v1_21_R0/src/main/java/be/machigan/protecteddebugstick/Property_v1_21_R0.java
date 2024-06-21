package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.action.*;
import be.machigan.protecteddebugstick.property.Property;
import org.bukkit.block.data.type.Crafter;
import org.bukkit.block.data.type.TrialSpawner;
import org.bukkit.block.data.type.Vault;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Property_v1_21_R0 implements PropertyHandler {
    @Override
    public List<Property> getUsableProperties() {
        List<Property> properties = new Property_v1_20_R6().getUsableProperties();
        properties.addAll(Arrays.asList(
                new Property("triggered", Crafter.class, new CrafterTriggeredAction()),
                new Property("crafting", Crafter.class, new CraftingAction()),
                new Property("orientable", Crafter.class, new CrafterOrientationAction()),
                new Property("trial-spawner-state", TrialSpawner.class, new TrialSpawnerStateAction()),
                new Property("ominous", TrialSpawner.class, new TrialSpawnerOminousAction()),
                new Property("vault-state", Vault.class, new VaultStateAction()),
                new Property("ominous", Vault.class, new VaultOminousAction())
        ));
        return properties;
    }
}
