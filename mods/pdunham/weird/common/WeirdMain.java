package pdunham.weird.common;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.EntityList;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;

import pdunham.weird.achievements.CraftingHandler;
import pdunham.weird.achievements.WeirdAchievementBetterBoom;
import pdunham.weird.achievements.WeirdAchievementGrenadeCreeper;
import pdunham.weird.achievements.WeirdAchievementOre;
import pdunham.weird.achievements.WeirdAchievementPowder;
import pdunham.weird.achievements.WeirdAchievementStartingOff;
import pdunham.weird.achievements.WeirdAchievementStickyToIt;
import pdunham.weird.armor.WeirdBoots;
import pdunham.weird.armor.WeirdChestPlate;
import pdunham.weird.armor.WeirdHelmet;
import pdunham.weird.armor.WeirdLeggins;
import pdunham.weird.armor.WeirdPlating;
import pdunham.weird.common.core.handlers.ClientPacketHandler;
import pdunham.weird.common.core.handlers.ServerPacketHandler;
import pdunham.weird.entity.EntityGrenade;
import pdunham.weird.entity.EntityPebble;
import pdunham.weird.entity.EntityWeirdBaby;
import pdunham.weird.objects.WeirdBlock;
import pdunham.weird.objects.WeirdIngot;
import pdunham.weird.objects.WeirdOre;
import pdunham.weird.objects.WeirdPoop;
import pdunham.weird.objects.WeirdPowder;
import pdunham.weird.renderer.RenderPebble;
import pdunham.weird.tools.WeirdAxe;
import pdunham.weird.tools.WeirdHoe;
import pdunham.weird.tools.WeirdPickaxe;
import pdunham.weird.tools.WeirdShovel;
import pdunham.weird.weapons.Pebble;
import pdunham.weird.weapons.WeirdCasing;
import pdunham.weird.weapons.WeirdGrenade;
import pdunham.weird.weapons.WeirdSlingShot;
import pdunham.weird.weapons.WeirdStickyCasing;
import pdunham.weird.weapons.WeirdStrongCasing;
import pdunham.weird.weapons.WeirdSword;
import pdunham.weird.weapons.WeirdTNT;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@NetworkMod(clientSideRequired = true, serverSideRequired = false, // Whether client side and server side are needed
            clientPacketHandlerSpec = @SidedPacketHandler(channels = {"pdunhamWeird" }, 
            packetHandler = ClientPacketHandler.class), //For clientside packet handling
            serverPacketHandlerSpec = @SidedPacketHandler(channels = {"pdunhamWeird"}, 
            packetHandler = ServerPacketHandler.class)) //For serverside packet handling
@Mod(modid = "weird", name = "Weird stuff", version = "0.1.0")

public class WeirdMain {
    
	@Instance("WeirdMain")
    public static WeirdMain instance = new WeirdMain();

    @Instance("WeirdOre")
  	public static WeirdOre weirdOre;

    @Instance("WeirdSolidBlock")
 	public static WeirdBlock weirdBlock;

    @Instance("WeirdIngot")
  	public static WeirdIngot weirdIngot;

    @Instance("WeirdPickaxe")
  	public static WeirdPickaxe weirdPickaxe;

    @Instance("WeirdAxe")
  	public static WeirdAxe weirdAxe;

    @Instance("WeirdShovel")
    public static WeirdShovel weirdShovel;

    @Instance("WeirdHoe")
    public static WeirdHoe weirdHoe;

    @Instance("WeirdSlingShot")
    public static WeirdSlingShot weirdSlingShot;
    
    @Instance("WeirdPowder")
    public static WeirdPowder weirdPowder;

    @Instance("WeirdTNT")
    public static WeirdTNT weirdTNT;

    @Instance("WeirdCasing")
    public static WeirdCasing weirdCasing;
    
    @Instance("WeirdStrongCasing")
    public static WeirdStrongCasing weirdStrongCasing;

    @Instance("WeirdStickyCasing")
    public static WeirdStickyCasing weirdStickyCasing;
    
    @Instance("WeirdPlating")
    public static WeirdPlating weirdPlating;
    
    @Instance("WeirdGrenade")
    public static WeirdGrenade weirdGrenade;
    @Instance("WeirdStickyGrenade")
    public static WeirdGrenade weirdStickyGrenade;
    @Instance("WeirdStrongGrenade")
    public static WeirdGrenade weirdStrongGrenade;

    @Instance("WeirdSword")
    public static WeirdSword weirdSword;

    @Instance("Pebble")
  	public static Pebble pebble;
    
    @Instance("WeirdHelmet")
    public static WeirdHelmet weirdHelmet;
    
    @Instance("WeirdChestPlate")
    public static WeirdChestPlate weirdChestPlate;
    
    @Instance("WeirdLeggins")
    public static WeirdLeggins weirdLeggins;
    
    @Instance("WeirdBoots")
    public static WeirdBoots weirdBoots;

    @Instance("WeirdPoop")
    public static WeirdPoop weirdPoop;
    
    @Instance("WeirdAchievementOre")
    public static WeirdAchievementOre weirdAchievementOre;

    @Instance("WeirdAchievementGrenadeCreeper")
    public static WeirdAchievementGrenadeCreeper weirdAchievementGrenadeCreeper;

    @Instance("WeirdAchievementPowder")
    public static WeirdAchievementStartingOff weirdAchievementStartingOff;

    @Instance("WeirdAchievementPowder")
    public static WeirdAchievementPowder weirdAchievementPowder;

    @Instance("WeirdAchievementBetterBoom")
    public static WeirdAchievementBetterBoom weirdAchievementBetterBoom;

    @Instance("WeirdAchievementStickToIt")
    public static WeirdAchievementStickyToIt weirdAchievementStickyToIt;

    @Instance("CraftingHandler")
    public static CraftingHandler craftingHandler = new CraftingHandler();

    @Instance("WeirdWorldGenerator")
    public static WeirdWorldGenerator worldGen = new WeirdWorldGenerator();

	private static StandardLogger logger;
    
    // Tell Forge where the proxies are.
    @SidedProxy(clientSide="pdunham.weird.client.WeirdClProxy", 
                serverSide="pdunham.weird.common.WeirdCoProxy")

	@Instance("WeirdCoProxy")
    public static WeirdCoProxy proxy;

	public static int configFirstBlockID = 3125;
	public static int configFirstItemID = 7238; // 7237 is taken
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
    		logger = StandardLogger.getLogger(logger, this.getClass().getSimpleName());
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		configFirstBlockID = config.getBlock("FirstBlockID", 3125, "This first Block ID to use for this mod. Blocks assign sequentially starting from this ID").getInt();
		configFirstItemID = config.getItem("FirstItemID", 7238, "This first Item ID to use for this mod. Items assign sequentially starting from this ID").getInt();
		config.save();    
        logger.info("configuration file loaded.  1st Block ID " + configFirstBlockID + ", 1st item ID " + configFirstBlockID);
	}

    @Init
    public void init(FMLInitializationEvent event) {
        logger.info("init()");

        // Registers this class that deals with GUI data
        NetworkRegistry.instance().registerGuiHandler(this, proxy);
        
        // Call help functions in the common proxy to register all of the weird objects
        proxy.init();
        proxy.registerTextures();
        proxy.registerRenderers(this);
        proxy.registerTiles();
        proxy.registerBlocks();
        proxy.registerItems();
        proxy.registerAchievements();
        proxy.registerSounds();

        // Register our self with the world generator so weird ore will be inserted into new worlds.
        GameRegistry.registerWorldGenerator(worldGen);
        
        logger.info("init() complete");
    }
    
    @PostInit
    public static void postInit(FMLPostInitializationEvent event) {
    		proxy.postInit();
        logger.info("postInit() complete");
    }
}
