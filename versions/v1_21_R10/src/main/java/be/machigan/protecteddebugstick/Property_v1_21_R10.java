package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.actions.GolemPoseAction;
import be.machigan.protecteddebugstick.property.Property;
import org.bukkit.block.data.type.CopperGolemStatue;

import java.util.List;

@SuppressWarnings("unused")
public class Property_v1_21_R10 implements PropertyHandler {
    @Override
    public List<Property> getUsableProperties() {
        List<Property> properties = new Property_v1_21_R8().getUsableProperties();
        properties.add(
                new Property("golem-pose", CopperGolemStatue.class, new GolemPoseAction())
        );
        return properties;
    }
}
