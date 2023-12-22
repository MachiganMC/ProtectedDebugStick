package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.action.*;
import be.machigan.protecteddebugstick.property.Property;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Property_v1_19_R0 implements PropertyHandler {
    @Override
    public List<Property> getUsableProperties() {
        return Arrays.asList(
                new Property("ageable", Ageable.class, new AgeableAction()),
                new Property("analogue-powerable", AnaloguePowerable.class, new AnaloguePowerableAction()),
                new Property("attachable", Attachable.class,new AttachableAction()),
                new Property("berry", CaveVinesPlant.class, new BerryAction()),
                new Property("bloom", SculkCatalyst.class, new BloomAction()),
                new Property("bamboo-leaves", Bamboo.class, new BambooLeavesAction()),
                new Property("bisected", Bisected.class, new BisectedAction()),
                new Property("cake", Cake.class, new CakeAction()),
                new Property("charges", RespawnAnchor.class, new ChargesAction()),
                new Property("conditional", CommandBlock.class, new ConditionalAction()),
                new Property("directional", Directional.class, new DirectionalAction()),
                new Property("disarmed",Tripwire.class,new DisarmedAction()),
                new Property("distance", Leaves.class, new DistanceAction()),
                new Property("eggs", TurtleEgg.class, new EggsAction()),
                new Property("end-portal-frame", EndPortalFrame.class, new EndPortalFrameAction()),
                new Property("extendable", Piston.class, new ExtendableAction()),
                new Property("face-attachable", FaceAttachable.class, new FaceAttachableAction()),
                new Property("gate", Gate.class, new GateAction()),
                new Property("hangable", Hangable.class, new HangableAction()),
                new Property("honey-level", Beehive.class, new HoneyLevelAction()),
                new Property("layers", Snow.class, new LayersAction()),
                new Property("levelled", Levelled.class, new LevelledAction()),
                new Property("lightable",Lightable.class, new LightableAction()),
                new Property("locked", Repeater.class, new LockedAction()),
                new Property("moisture", Farmland.class, new MoistureAction()),
                new Property("multiple-facing", MultipleFacing.class, new MultiplefacingAction()),
                new Property("openable", Openable.class, new OpenableAction()),
                new Property("orientable",Orientable.class,new OrientableAction()),
                new Property("persistent", Leaves.class, new PersistentAction()),
                new Property("pickles", SeaPickle.class, new PicklesAction()),
                new Property("phase", SculkShrieker.class, new PhaseAction()),
                new Property("powerable", Powerable.class, new PowerableAction()),
                new Property("redstone-wire", RedstoneRail.class, new RedstoneWireAction()),
                new Property("rotatable", Rotatable.class, new RotatableAction()),
                new Property("shape-rail", Rail.class, new ShapeRailAction()),
                new Property("shape-stairs", Stairs.class, new ShapeStairsAction()),
                new Property("shrieking", SculkShrieker.class, new ShriekingAction()),
                new Property("signal-fire", Fire.class, new SignalFireAction()),
                new Property("slab", Slab.class, new SlabAction()),
                new Property("snowable", Snowable.class, new SnowableAction()),
                new Property("stage", Sapling.class, new StageAction()),
                new Property("summon", SculkShrieker.class, new SummonAction()),
                new Property("thickness", PointedDripstone.class, new ThicknessAction()),
                new Property("tilt", BigDripleaf.class, new TiltAction()),
                new Property("vertical-direction", PointedDripstone.class, new VerticalDirectionAction()),
                new Property("wall", Wall.class, new WallAction()),
                new Property("water-logged", Waterlogged.class, new WaterLoggedAction())
        );
    }
}
