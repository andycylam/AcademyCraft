package cn.academy.block.block;

import cn.academy.block.container.ContainerWindGenMain;
import cn.academy.energy.ModuleEnergy;
import cn.academy.block.tileentity.TileWindGenMain;
import cn.academy.energy.client.ui.GuiWindGenMain;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 */
public class BlockWindGenMain extends ACBlockMulti {
    
    @RegGuiHandler
    public static GuiHandlerBase guiHandler = new GuiHandlerBase() {
        @SideOnly(Side.CLIENT)
        @Override
        protected Object getClientContainer(EntityPlayer player, World world, int x, int y, int z) {
            ContainerWindGenMain container = (ContainerWindGenMain) getServerContainer(player, world, x, y, z);
            return container == null ? null : GuiWindGenMain.apply(container);
        }
        
        @Override
        protected Object getServerContainer(EntityPlayer player, World world, int x, int y, int z) {
            TileWindGenMain gen = locate(world, x, y, z);
            return gen == null ? null : new ContainerWindGenMain(player, gen);
        }
        
        TileWindGenMain locate(World world, int x, int y, int z) {
            Block block = world.getBlock(x, y, z);
            if(block != ModuleEnergy.windgenMain)
                return null;
            TileEntity te = ModuleEnergy.windgenMain.getOriginTile(world, x, y, z);
            return (TileWindGenMain) ((te instanceof TileWindGenMain) ? te : null);
        }
    };

    public BlockWindGenMain() {
        super("windgen_main", Material.rock);
        setHardness(4.0f);
        setHarvestLevel("pickaxe", 2);
        this.addSubBlock(new int[][] {
            { 0, 0, -1 },
            { 0, 0, 1 }
        });
        finishInit();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileWindGenMain();
    }

    @Override
    public double[] getRotCenter() {
        return new double[] { 0.5, 0, 0.4 };
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, 
            float tx, float ty, float tz) {
        if(!player.isSneaking()) {
            guiHandler.openGuiContainer(player, world, x, y, z);
            return true;
        }
        return false;
    }
    
}