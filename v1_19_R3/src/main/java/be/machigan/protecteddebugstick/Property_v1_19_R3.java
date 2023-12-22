package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.action.OccupiedSlotAction;
import be.machigan.protecteddebugstick.property.Property;
import org.bukkit.block.data.type.ChiseledBookshelf;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Property_v1_19_R3 implements PropertyHandler {
    @Override
    public List<Property> getUsableProperties() {
        List<Property> properties = new ArrayList<>(new Property_v1_19_R2().getUsableProperties());
        properties.add(new Property("occupied-slot", ChiseledBookshelf.class, new OccupiedSlotAction()));
        return properties;
    }
}
