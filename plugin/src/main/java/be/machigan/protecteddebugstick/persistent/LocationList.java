package be.machigan.protecteddebugstick.persistent;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

public class LocationList extends HashSet<LocationSerializable> implements Serializable {
    @Serial private static final long serialVersionUID = 5464L;
}
