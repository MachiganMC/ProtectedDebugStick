package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.actions.WaxedAction;
import be.machigan.protecteddebugstick.property.Property;
import org.bukkit.block.data.type.Sign;

import java.util.List;

@SuppressWarnings("unused")
public class Property_v1_20_R1 implements PropertyHandler {
    @Override
    public List<Property> getUsableProperties() {
        List<Property> properties = new Property_v1_20_R0().getUsableProperties();
        properties.add(new Property("waxed", Sign.class, new WaxedAction()));
        return properties;
    }
}
