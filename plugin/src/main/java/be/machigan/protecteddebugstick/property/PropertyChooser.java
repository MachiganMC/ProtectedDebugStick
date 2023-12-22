package be.machigan.protecteddebugstick.property;

import be.machigan.protecteddebugstick.PropertyHandler;
import be.machigan.protecteddebugstick.ProtectedDebugStick;

public class PropertyChooser {
    public static PropertyHandler getPropertyHandler() {
        String serverPackage = ProtectedDebugStick.getInstance().getServer().getClass().getPackage().getName();
        String serverVersion = serverPackage.substring(serverPackage.lastIndexOf('.') + 1);
        try {
            Class<?> implementation = Class.forName("be.machigan.protecteddebugstick.Property_" + serverVersion);
            if (PropertyHandler.class.isAssignableFrom(implementation)) {
                return (PropertyHandler) implementation.getConstructor().newInstance();
            }
        } catch (Exception ignore) {}
        return null;
    }

    private PropertyChooser() {}
}
