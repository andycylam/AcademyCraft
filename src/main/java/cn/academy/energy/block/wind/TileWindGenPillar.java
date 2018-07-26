package cn.academy.energy.block.wind;

import cn.academy.energy.client.render.block.RenderWindGenPillar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;

/**
 * @author WeAthFolD
 */
@RegTileEntity
@RegTileEntity.HasRender
public class TileWindGenPillar extends TileEntity {
    
    @SideOnly(Side.CLIENT)
    @RegTileEntity.Render
    public static RenderWindGenPillar renderer;
    
}