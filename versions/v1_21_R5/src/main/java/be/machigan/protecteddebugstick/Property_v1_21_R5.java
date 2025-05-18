package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.actions.SegmentAmountAction;
import be.machigan.protecteddebugstick.actions.TestModeAction;
import be.machigan.protecteddebugstick.property.Property;
import org.bukkit.block.data.type.LeafLitter;
import org.bukkit.block.data.type.TestBlock;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Property_v1_21_R5 implements PropertyHandler {
    @Override
    public List<Property> getUsableProperties() {
        List<Property> properties = new ArrayList<>(new Property_v1_21_R4().getUsableProperties());
        properties.add(new Property("segment-amount", LeafLitter.class, new SegmentAmountAction()));
        properties.add(new Property("test-mode", TestBlock.class, new TestModeAction()));
        return properties;
    }
}
