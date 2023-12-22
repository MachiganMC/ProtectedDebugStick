package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.action.BrushableAction;
import be.machigan.protecteddebugstick.action.HatchAction;
import be.machigan.protecteddebugstick.action.PetalsAction;
import be.machigan.protecteddebugstick.property.Property;
import org.bukkit.block.data.Brushable;
import org.bukkit.block.data.Hatchable;
import org.bukkit.block.data.type.PinkPetals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Property_v1_20_R0 implements PropertyHandler {
    @Override
    public List<Property> getUsableProperties() {
        List<Property> properties = new ArrayList<>(Arrays.asList(
                new Property("brushable", Brushable.class,new BrushableAction()),
                new Property("hatch", Hatchable.class, new HatchAction()),
                new Property("petals", PinkPetals.class, new PetalsAction())
        ));
        properties.addAll(new Property_v1_19_R3().getUsableProperties());
        return properties;
    }
}
