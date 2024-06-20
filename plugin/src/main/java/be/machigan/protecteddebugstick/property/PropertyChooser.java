package be.machigan.protecteddebugstick.property;

import be.machigan.protecteddebugstick.PropertyHandler;
import be.machigan.protecteddebugstick.ProtectedDebugStick;
import com.google.common.primitives.Ints;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyChooser {
    public static PropertyHandler getPropertyHandler() {
        try {
            Class<?> implementation = Class.forName("be.machigan.protecteddebugstick.Property_" + getCurrentVersion());
            if (PropertyHandler.class.isAssignableFrom(implementation)) {
                return (PropertyHandler) implementation.getConstructor().newInstance();
            }
        } catch (Exception ignore) {}
        return null;
    }

    private static String getCurrentVersion() {
        Matcher matcher = Pattern.compile("(?<version>\\d+\\.\\d+)(?<patch>\\.\\d+)?").matcher(Bukkit.getBukkitVersion());
        StringBuilder stringBuilder = new StringBuilder();
        if (matcher.find()) {
            stringBuilder.append("v").append(matcher.group("version").replace(".", "_"));
            String patch = matcher.group("patch");
            if (patch == null)
                stringBuilder.append("_R0");
            else
                stringBuilder.append(patch.replace(".", "_R"));
        }
        return stringBuilder.toString();
    }

    private PropertyChooser() {}
}
