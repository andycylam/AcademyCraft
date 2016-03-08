package cn.academy.ability.develop.action;

import cn.academy.ability.ModuleAbility;
import cn.academy.ability.api.Category;
import cn.academy.ability.api.data.AbilityData;
import cn.academy.ability.develop.DeveloperType;
import cn.academy.ability.develop.IDeveloper;
import cn.academy.ability.item.ItemInductionFactor;
import com.google.common.base.Objects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.Arrays;
import java.util.Optional;

public class DevelopActionReset implements IDevelopAction {

    public static boolean canReset(EntityPlayer player, IDeveloper developer) {
        AbilityData data = AbilityData.get(player);
        ItemStack equip = player.getCurrentEquippedItem();

        int level = data.getLevel();

        return level >= 3 &&
                developer.getType() == DeveloperType.ADVANCED &&
                equip != null && equip.getItem() == ModuleAbility.magneticCoil &&
                getFactor(player).isPresent();
    }

    private static Optional<ItemStack> getFactor(EntityPlayer player) {
        return Arrays.stream(player.inventory.mainInventory)
                .filter(stack -> stack != null && stack.getItem() instanceof ItemInductionFactor)
                .findAny();
    }

    @Override
    public int getStimulations(EntityPlayer player) {
        AbilityData data = AbilityData.get(player);
        return data.getLevel() * 10;
    }

    @Override
    public boolean validate(EntityPlayer player, IDeveloper developer) {
        return canReset(player, developer);
    }

    @Override
    public void onLearned(EntityPlayer player) {
        AbilityData data = AbilityData.get(player);

        ItemStack factor = getFactor(player).get();

        Category newCat = ItemInductionFactor.getCategory(factor);

        int prevLevel = data.getLevel();

        data.setCategory(newCat);
        data.setLevel(prevLevel - 1);

        player.setCurrentItemOrArmor(0, null);

        int factorIdx = Arrays.asList(player.inventory.mainInventory).indexOf(factor);
        player.inventory.mainInventory[factorIdx] = null;
    }

    @Override
    public ResourceLocation getIcon(EntityPlayer player) {
        return getFactor(player)
                .map(s -> ItemInductionFactor.getCategory(s).getIcon())
                .orElseGet(null);
    }

    @Override
    public String getName(EntityPlayer player) {
        return StatCollector.translateToLocalFormatted("ac.skill_tree.reset.desc",
                getFactor(player).map(ItemInductionFactor::getCategory).get().getDisplayName());
    }
}
