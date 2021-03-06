package pdunham.weird.objects;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;

import pdunham.weird.common.StandardLogger;
import pdunham.weird.common.WeirdConfig;
import pdunham.weird.common.WeirdConstants;
import pdunham.weird.common.WeirdMain;
import pdunham.weird.entity.TileEntityWeirdFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

// This is a modified copy of BlockFurnace. The main BlockFurance class cannot
// be properly derived from because of the various static components of it.
public class WeirdFurnace extends BlockContainer
{
	private static StandardLogger logger = new StandardLogger();

	// Is the random generator used by furnace to drop the inventory contents in random directions.
    private Random furnaceRand = new Random();

    // True if this is an active furnace, false if idle
    private final boolean isActive = true;

    public WeirdFurnace(int par1)
    {
        super(par1, Material.rock);
        
        this.blockIndexInTexture = 45;

        // Set the internal reference name
		setBlockName("weirdFurnace");
		
		// Put this block on the block tab
		setCreativeTab(CreativeTabs.tabDecorations);
		
		// The one for dirt is 0.5F. The one for obsidian is 50.0F
		setHardness(0.5F);
		
		// Resistance to explosions. The resistance for bedrock is 6000000.0F
		setResistance(56.34F);
		
		// The sound we make when walked on it and when we break it.
		setStepSound(this.soundMetalFootstep);
		
		// Make it glow bright.
		setLightValue(1);
    }

     // Returns the ID of the items to drop on destruction.
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return WeirdConfig.weirdFurnaceID;
    }

    // Called whenever the block is added into the world. Args: world, x, y, z
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    // set a blocks direction
    private void setDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
            int var5 = par1World.getBlockId(par2, par3, par4 - 1);
            int var6 = par1World.getBlockId(par2, par3, par4 + 1);
            int var7 = par1World.getBlockId(par2 - 1, par3, par4);
            int var8 = par1World.getBlockId(par2 + 1, par3, par4);
            byte var9 = 3;

            if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6])
            {
                var9 = 3;
            }

            if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5])
            {
                var9 = 2;
            }

            if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8])
            {
                var9 = 5;
            }

            if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7])
            {
                var9 = 4;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, var9);
        }
    }

    @SideOnly(Side.CLIENT)
    // Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1)
        {
            return this.blockIndexInTexture + 17;
        }
        else if (par5 == 0)
        {
            return this.blockIndexInTexture + 17;
        }
        else
        {
            int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
            return par5 != var6 ? this.blockIndexInTexture : (this.isActive ? this.blockIndexInTexture + 16 : this.blockIndexInTexture - 1);
        }
    }

    @SideOnly(Side.CLIENT)
    // A randomly called display update to be able to add particles or other items for display
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (this.isActive)
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4);
            float var7 = (float)par2 + 0.5F;
            float var8 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
            float var9 = (float)par4 + 0.5F;
            float var10 = 0.52F;
            float var11 = par5Random.nextFloat() * 0.6F - 0.3F;

            if (var6 == 4)
            {
                par1World.spawnParticle("smoke", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 5)
            {
                par1World.spawnParticle("smoke", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 2)
            {
                par1World.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 3)
            {
                par1World.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    // Returns the block texture based on the side being looked at.  Args: side
    public int getBlockTextureFromSide(int par1)
    {
        return par1 == 1 ? this.blockIndexInTexture + 17 : (par1 == 0 ? this.blockIndexInTexture + 17 : (par1 == 3 ? this.blockIndexInTexture - 1 : this.blockIndexInTexture));
    }

    // Called upon block activation (right click on the block.)
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityFurnace var10 = (TileEntityFurnace)par1World.getBlockTileEntity(par2, par3, par4);

            if (var10 != null)
            {
                par5EntityPlayer.displayGUIFurnace(var10);
            }

            return true;
        }
    }

    // Update which block ID the furnace is using depending on whether or not it is burning
    public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);

        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5);

        if (var6 != null)
        {
            var6.validate();
            par1World.setBlockTileEntity(par2, par3, par4, var6);
        }
    }

    // Returns a new instance of a block's tile entity class. Called on placing the block.
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityWeirdFurnace();
    }

    // Called when the block is placed in the world.
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
        }

        if (var6 == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
        }

        if (var6 == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
        }

        if (var6 == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
        }
    }

    // ejects contained items into the world, and notifies neighbours of an update, as appropriate
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntityFurnace var7 = (TileEntityFurnace)par1World.getBlockTileEntity(par2, par3, par4);

        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                    float var12 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                    while (var9.stackSize > 0)
                    {
                        int var13 = this.furnaceRand.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));

                        if (var9.hasTagCompound())
                        {
                            var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }

                        float var15 = 0.05F;
                        var14.motionX = (double)((float)this.furnaceRand.nextGaussian() * var15);
                        var14.motionY = (double)((float)this.furnaceRand.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double)((float)this.furnaceRand.nextGaussian() * var15);
                        par1World.spawnEntityInWorld(var14);
                    }
                }
            }
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
	public void postInit() {
		// Set the graphics texture from a .png file
		setTextureFile(getTextureFile());

		// Register the block w/ MineCraft
		GameRegistry.registerBlock(this, "weirdFurnace");
		GameRegistry.registerTileEntity(TileEntityWeirdFurnace.class, "WeirdFuranceTile");

		// Set the external name
		LanguageRegistry.addName(this, "A Weird Furnace");

		// A solid block is crafted from 8 weirdIngots and a stone furnace in the crafting table
		GameRegistry.addRecipe(new ItemStack(WeirdMain.weirdFurnace), "www", "wfw", "www",
							'w', new ItemStack(WeirdMain.weirdBlock),
							'f', new ItemStack(Block.stoneOvenIdle));

		logger.info("postInit() complete newId: " + blockID);
	}

    
    @Override
	public String getTextureFile(){
		return WeirdConstants.pathTexturesIcons;
	}	
}
