package acetil.magicalreactors.common.block;

import acetil.magicalreactors.common.MagicalReactors;
import acetil.magicalreactors.common.block.reactor.BlockReactorController;
import acetil.magicalreactors.common.block.reactor.BlockReactor;
import acetil.magicalreactors.common.block.reactor.BlockRuneBase;
import acetil.magicalreactors.common.machines.MachineBlocks;
import acetil.magicalreactors.common.tiles.*;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import acetil.magicalreactors.common.lib.LibMisc;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = LibMisc.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("unused")
@ObjectHolder(LibMisc.MODID)
public class ModBlocks {
    @ObjectHolder("temp_ore")
    public static Block TEMP_ORE1 = null; //TODO
    @ObjectHolder("temp_ore_b")
    public static Block TEMP_ORE2 = null; //TODO
    @ObjectHolder("reactor_controller")
    public static Block REACTOR_CONTROLLER = null;
    @ObjectHolder("rune_basic")
    public static Block RUNE_BASIC = null;
    @ObjectHolder("rune_stabilisation")
    public static Block RUNE_STABILISATION = null;
    @ObjectHolder("rune_transfer")
    public static Block RUNE_TRANSFER = null;
    @ObjectHolder("rune_harmonic")
    public static Block RUNE_HARMONIC = null;
    @ObjectHolder("rune_locus")
    public static Block RUNE_LOCUS = null;
    @ObjectHolder("reactor_block")
    public static Block REACTOR_BLOCK = null;

    @ObjectHolder("reactor")
    public static TileEntityType<?> REACTOR_TILE_ENTITY = null;
    @ObjectHolder("reactor_controller")
    public static TileEntityType<?> REACTOR_CONTROLLER_TILE_ENTITY = null;
    public static TileEntityType<?> BYPRODUCT_INTERFACE;
    public static TileEntityType<?> COOLING_INTERFACE;
    public static TileEntityType<?> ENERGY_INTERFACE;
    public static TileEntityType<?> FUEL_INTERFACE;
    @SubscribeEvent
    public static void registerBlocks (RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockOre("temp_ore", 4f));
        event.getRegistry().register(new BlockOre("temp_ore_b", 4f));
        event.getRegistry().register(new BlockReactorController());
        event.getRegistry().register(new BlockRuneBase("rune_basic"));
        event.getRegistry().register(new BlockRuneBase("rune_stabilisation"));
        event.getRegistry().register(new BlockRuneBase("rune_transfer"));
        event.getRegistry().register(new BlockRuneBase("rune_harmonic"));
        event.getRegistry().register(new BlockRuneBase("rune_locus"));
        event.getRegistry().register(new BlockReactor());
        MachineBlocks.registerMachineBlocks(event);
        /*GameRegistry.registerTileEntity(TileReactor.class, new ResourceLocation(LibMisc.MODID + ":reactor_entity"));
        GameRegistry.registerTileEntity(TileReactorController.class, new ResourceLocation(LibMisc.MODID + ":reactor_controller_entity"));
        GameRegistry.registerTileEntity(TileReactorNew.class, new ResourceLocation(LibMisc.MODID + ":reactor_entity_new"));*/
    }

    @SubscribeEvent
    public static void registerItems (RegistryEvent.Register<Item> event) {
        registerItemBlock(event, TEMP_ORE1);
        registerItemBlock(event, TEMP_ORE2);
        registerItemBlock(event, REACTOR_CONTROLLER);
        registerItemBlock(event, RUNE_BASIC);
        registerItemBlock(event, RUNE_STABILISATION);
        registerItemBlock(event, RUNE_TRANSFER);
        registerItemBlock(event, RUNE_HARMONIC);
        registerItemBlock(event, RUNE_LOCUS);
        registerItemBlock(event, REACTOR_BLOCK);
        MachineBlocks.registerMachineItems(event);
        System.out.println("Registered itemblocks");
    }
    public static void registerItemBlock (RegistryEvent.Register<Item> event, Block b) {
        MagicalReactors.LOGGER.info("Registered itemblock for {}", b.getRegistryName());
        event.getRegistry().register(new BlockItem(b, new Item.Properties()).setRegistryName(b.getRegistryName()));
    }
    @SubscribeEvent
    public static void registerTileEntities (RegistryEvent.Register<TileEntityType<?>> event) {
        registerTileEntity(event, TileReactor::new, "reactor");
        registerTileEntity(event, TileReactorController::new, "reactor_controller");
        registerTileEntity(event, TileReactorInterfaceByproduct::new, "byproduct_interface");
        registerTileEntity(event, TileReactorInterfaceCooling::new, "cooling_interface");
        registerTileEntity(event, TileReactorInterfaceEnergy::new, "energy_interface");
        registerTileEntity(event, TileReactorInterfaceFuelLoader::new, "fuel_interface");
        MachineBlocks.registerTileEntities(event);
        MagicalReactors.LOGGER.info("Registered tile entities!");
    }
    public static void registerTileEntity (RegistryEvent.Register<TileEntityType<?>> event, Supplier<? extends TileEntity> factory,
                                           String name) {
        event.getRegistry().register(TileEntityType.Builder.create(factory).build(null)
                .setRegistryName(new ResourceLocation(LibMisc.MODID, name)));
    }
}
