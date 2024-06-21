package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.action.BottlesAction;
import be.machigan.protecteddebugstick.property.Property;
import org.bukkit.block.data.type.BrewingStand;

import java.util.List;

@SuppressWarnings("unused")
public class Property_v1_20_R5 implements PropertyHandler {
    @Override
    public List<Property> getUsableProperties() {
        List<Property> properties = new Property_v1_20_R4().getUsableProperties();
        properties.add(new Property("bottles", BrewingStand.class, new BottlesAction()));
        return properties;
    }
}
