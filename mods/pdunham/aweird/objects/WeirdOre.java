package pdunham.aweird.objects;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import pdunham.aweird.common.StandardLogger;
import pdunham.aweird.common.WeirdConstants;
import pdunham.aweird.common.WeirdMain;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WeirdOre extends Block {

	private static StandardLogger logger;

	public WeirdOre(int id) {
		
		// Call the parent class c'tor first.
		super(id, Material.iron);

		// Set the internal reference name
		setBlockName("weirdOre");
		
		// Put this block on the block tab
		setCreativeTab(CreativeTabs.tabBlock);
		
		// The one for dirt is 0.5F. The one for obsidian is 50.0F
		setHardness(5.6F);
		
		// Resistance to explosions. The resistance for bedrock is 6000000.0F
		setResistance(56.34F);
		
		// The sound we make when walked on it and when we break it.
		setStepSound(this.soundMetalFootstep);
		
		// Make it glow some.
		setLightValue(0.25f);

		// Pick the correct icon from the .png file.
		blockIndexInTexture = 2;
		
        logger = StandardLogger.getLogger(logger, this.getClass().getSimpleName());
        logger.info("c'tor() complete id: " + id);
	}

	public void postInit() {
		// Set the graphics texture from a .png file
		setTextureFile(getTextureFile());

		// Register the block w/ MineCraft
	    GameRegistry.registerBlock(this, "weirdOre");
	
	    // Only iron and above pick axe can mine this block 
	    MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 2);
	
	    // Add a human readable name
	    LanguageRegistry.addName(this, "Weird ore");

		logger.info("postInit() complete newId: " + blockID);
	}

	// trigger the acheivement
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
    		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
    		par2EntityPlayer.triggerAchievement(WeirdMain.weirdAchievementOre);
		logger.info("harvestBlock()");
	}
	
	// Define what the block drops when mined
	public int idDropped(int zero1, Random random, int zero2) {
		int itemId = WeirdMain.weirdOre.blockID;
		logger.info("idDropped() returns weirdOre: " + itemId);
        return itemId;
	}	
	
	// Define how many are dropped.
	public int quantityDropped(Random par1Random) {
		logger.info("quantityDropped() returns 1");
		return 1;
	}
	
	// Marks a method as client side only, typically for graphics and rendering
	@SideOnly(Side.CLIENT) 
	public int getBlockTextureFromSide(int i) {
		// Which texture to use from the .png file
		return 1; // The first icon on the spritesheet
	}

	@Override
	public String getTextureFile(){
		return WeirdConstants.pathTexturesIcons;
	}	
}