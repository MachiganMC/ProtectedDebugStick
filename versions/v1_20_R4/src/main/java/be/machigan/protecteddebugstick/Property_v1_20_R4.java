package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.property.Property;

import java.util.List;

@SuppressWarnings("unused")
public class Property_v1_20_R4 implements PropertyHandler {
    @Override
    public List<Property> getUsableProperties() {
        return new Property_v1_20_R3().getUsableProperties();
    }
}
