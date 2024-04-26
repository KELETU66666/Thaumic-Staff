package de.zpenguin.thaumicwands.item;

import com.google.common.collect.Multimap;
import de.zpenguin.thaumicwands.api.ThaumicWandsAPI;
import de.zpenguin.thaumicwands.api.item.wand.*;
import de.zpenguin.thaumicwands.util.LocalizationHelper;
import de.zpenguin.thaumicwands.util.WandHelper;
import de.zpenguin.thaumicwands.wand.TW_Wands;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.casters.*;
import thaumcraft.api.items.IArchitect;
import thaumcraft.api.items.RechargeHelper;
import thaumcraft.common.items.casters.CasterManager;
import thaumcraft.common.items.casters.ItemFocus;
import thaumcraft.common.lib.utils.BlockUtils;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemStaff extends ItemBase implements IStaff {

    DecimalFormat formatter = new DecimalFormat("#######.#");

    public ItemStaff(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == this.getCreativeTab()) {
            for (ItemStack wand : new ItemStack[]{WandHelper.getStaffWithParts("primal", "void")}) {
                items.add(wand.copy());
                RechargeHelper.rechargeItemBlindly(wand, null, getMaxCharge(wand, null));
                items.add(wand);
            }
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot p_getItemAttributeModifiers_1_) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(p_getItemAttributeModifiers_1_);
        if (p_getItemAttributeModifiers_1_ == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316, 0));
        }

        return multimap;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);
        if (stack != null && stack.getItem() instanceof ItemStaff)
            if (entity instanceof EntityPlayer && itemSlot < 9)
                if (getCore(stack).hasUpdate())
                    getCore(stack).getUpdate().onUpdate(stack, (EntityPlayer) entity);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String name = I18n.translateToLocal("item.wand.name");
        name = name.replace("%CAP", LocalizationHelper.localize("item.wand." + getCap(stack).getTag() + ".cap"));
        name = name.replace("%ROD", LocalizationHelper.localize("item.wand." + getCore(stack).getTag() + ".rod"));
        name = name.replace("%OBJ", LocalizationHelper.localize("item.wand.staff.obj"));
        return name;
    }

    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
                                           float hitY, float hitZ, EnumHand hand) {
        IBlockState bs = world.getBlockState(pos);
        if (bs.getBlock() instanceof IInteractWithCaster && ((IInteractWithCaster) bs.getBlock())
                .onCasterRightClick(world, player.getHeldItem(hand), player, pos, side, hand))
            return EnumActionResult.PASS;
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof IInteractWithCaster && ((IInteractWithCaster) tile).onCasterRightClick(world,
                player.getHeldItem(hand), player, pos, side, hand))
            return EnumActionResult.PASS;
        if (CasterTriggerRegistry.hasTrigger(bs))
            return CasterTriggerRegistry.performTrigger(world, player.getHeldItem(hand), player, pos, side, bs)
                    ? EnumActionResult.SUCCESS
                    : EnumActionResult.FAIL;
        ItemStack fb = getFocusStack(player.getHeldItem(hand));
        if (fb != null && !fb.isEmpty()) {
            FocusPackage core = ItemFocus.getPackage(fb);
            for (IFocusElement fe : core.nodes) {
                if (fe instanceof IFocusBlockPicker && player.isSneaking() && world.getTileEntity(pos) == null)
                    if (!world.isRemote) {
                        ItemStack isout = new ItemStack(bs.getBlock(), 1, bs.getBlock().getMetaFromState(bs));
                        try {
                            if (bs != Blocks.AIR) {
                                ItemStack is = BlockUtils.getSilkTouchDrop(bs);
                                if (is != null && !is.isEmpty())
                                    isout = is.copy();
                            }
                        } catch (Exception exception) {
                        }
                        storePickedBlock(player.getHeldItem(hand), isout);
                    } else {
                        player.swingArm(hand);
                        return EnumActionResult.PASS;
                    }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack focusStack = getFocusStack(player.getHeldItem(hand));
        ItemFocus focus = getFocus(player.getHeldItem(hand));
        if (focus != null && !isOnCooldown(player)) {
            CasterManager.setCooldown(player, focus.getActivationTime(focusStack));
            FocusPackage core = ItemFocus.getPackage(focusStack);

            if (player.isSneaking())
                for (IFocusElement fe : core.nodes)
                    if (fe instanceof IFocusBlockPicker && player.isSneaking())
                        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));

            if (world.isRemote) {
                player.swingArm(hand);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
            if (consumeVis(player.getHeldItem(hand), player, focus.getVisCost(focusStack), false, false)) {
                FocusEngine.castFocusPackage(player, core);
                player.swingArm(hand);
                return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
            return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
        }
        return super.onItemRightClick(world, player, hand);
    }

    public void storePickedBlock(ItemStack stack, ItemStack stackout) {
        NBTTagCompound item = new NBTTagCompound();
        stack.setTagInfo("picked", stackout.writeToNBT(item));
    }

    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack stack1) {
        return EnumAction.BOW;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound()) {
            String text = "";

            tooltip.add(TextFormatting.DARK_PURPLE + LocalizationHelper.localize("tc.vis.cost") + " " + (int) (WandHelper.getTotalDiscount(stack, null) * 100F) + "%");

            ItemStack focus = getFocusStack(stack);
            if (focus != null && !focus.isEmpty()) {
                float amt = ((ItemFocus) focus.getItem()).getVisCost(focus) * getConsumptionModifier(stack, null, false);
                if (amt > 0.0F)
                    text = this.formatter.format(amt) + " " + LocalizationHelper.localize("item.Focus.cost1");

                tooltip.add(String.valueOf(TextFormatting.ITALIC) + TextFormatting.AQUA + LocalizationHelper.localize("tc.vis.cost") + " " + text);
            }
        }
        if (getFocus(stack) != null) {
            tooltip.add(String.valueOf(TextFormatting.BOLD) + TextFormatting.ITALIC + TextFormatting.GREEN + getFocus(stack).getItemStackDisplayName(getFocusStack(stack)));
            getFocus(stack).addFocusInformation(getFocusStack(stack), worldIn, tooltip, flagIn);
        }
    }

    static boolean isOnCooldown(EntityLivingBase entityLiving) {
        try {
            Method m = CasterManager.class.getDeclaredMethod("isOnCooldown", EntityLivingBase.class);
            m.setAccessible(true);
            return (boolean) m.invoke(null, entityLiving);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // IRechargeable

    @Override
    public boolean consumeVis(ItemStack stack, EntityPlayer player, float amount, boolean crafting, boolean sim) {
        amount *= getConsumptionModifier(stack, player, crafting);
        float total = RechargeHelper.getCharge(stack);

        if (amount > total)
            return false;
        if (sim)
            return true;

        RechargeHelper.consumeCharge(stack, player, Math.round(Math.max(1, amount)));
        return true;
    }

    @Override
    public float getConsumptionModifier(ItemStack wand, EntityPlayer player, boolean crafting) {
        float consumptionModifier = 1.0F;
        if (player != null && wand != null && !wand.isEmpty())
            consumptionModifier = WandHelper.getTotalDiscount(wand, player);
        return Math.max(consumptionModifier, 0.1F) + 0.05F;
    }

    public ItemFocus getFocus(ItemStack stack) {
        ItemStack fs = getFocusStack(stack);
        if (fs != null && !fs.isEmpty())
            return (ItemFocus) fs.getItem();
        return null;
    }

    public ItemStack getFocusStack(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("focus")) {
            NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("focus");
            return new ItemStack(nbt);
        }
        return null;
    }

    @Override
    public ItemStack getPickedBlock(ItemStack stack) {
        if (stack == null || stack.isEmpty())
            return ItemStack.EMPTY;
        ItemStack out = null;
        ItemFocus focus = getFocus(stack);
        if (focus != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("picked")) {
            FocusPackage fp = ItemFocus.getPackage(getFocusStack(stack));
            if (fp != null)
                for (IFocusElement fe : fp.nodes)
                    if (fe instanceof IFocusBlockPicker) {
                        out = new ItemStack(Blocks.AIR);
                        try {
                            out = new ItemStack(stack.getTagCompound().getCompoundTag("picked"));
                            break;
                        } catch (Exception exception) {
                        }
                        return out;
                    }
        }
        return out;
    }

    public void setFocus(ItemStack stack, ItemStack focus) {
        if (focus == null || focus.isEmpty())
            stack.getTagCompound().removeTag("focus");
        else
            stack.setTagInfo("focus", focus.writeToNBT(new NBTTagCompound()));

    }

    @Override
    public IWandCap getCap(ItemStack stack) {
        if (stack.hasTagCompound()) {
            String s = stack.getTagCompound().getString("cap");
            IWandCap cap = ThaumicWandsAPI.getWandCap(s);
            return cap != null ? cap : TW_Wands.capIron;
        }
        return TW_Wands.capIron;
    }

    @Override
    public IStaffCore getCore(ItemStack stack) {
        if (stack.hasTagCompound()) {
            String s = stack.getTagCompound().getString("rod");
            IStaffCore rod = ThaumicWandsAPI.getStaffCore(s);
            return rod != null ? rod : TW_Wands.coreGreatwood;
        }
        return TW_Wands.coreGreatwood;
    }

    public static ItemStack fromParts(IStaffCore rod, IWandCap cap) {
        ItemStack is = new ItemStack(TW_Items.itemStaff);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("cap", cap.getTag());
        nbt.setString("rod", rod.getTag());
        is.setTagCompound(nbt);
        return is;
    }

    @Override
    public int getMaxCharge(ItemStack stack, EntityLivingBase player) {
        return getCore(stack).getCapacity();
    }

    @Override
    public EnumChargeDisplay showInHud(ItemStack arg0, EntityLivingBase arg1) {
        return EnumChargeDisplay.NORMAL;
    }

    // IArchitect

    @Override
    public ArrayList<BlockPos> getArchitectBlocks(ItemStack stack, World world, BlockPos pos, EnumFacing side,
                                                  EntityPlayer player) {
        ItemFocus focus = getFocus(stack);
        if (focus != null) {
            FocusPackage fp = ItemFocus.getPackage(getFocusStack(stack));
            if (fp != null)
                for (IFocusElement fe : fp.nodes)
                    if (fe instanceof IArchitect)
                        return ((IArchitect) fe).getArchitectBlocks(stack, world, pos, side, player);

        }
        return null;
    }

    @Override
    public boolean showAxis(ItemStack stack, World world, EntityPlayer player, EnumFacing side,
                            EnumAxis axis) {
        ItemFocus focus = getFocus(stack);
        if (focus != null) {
            FocusPackage fp = ItemFocus.getPackage(getFocusStack(stack));
            if (fp != null)
                for (IFocusElement fe : fp.nodes)
                    if (fe instanceof IArchitect)
                        return ((IArchitect) fe).showAxis(stack, world, player, side, axis);

        }
        return false;
    }

    @Override
    public RayTraceResult getArchitectMOP(ItemStack stack, World world, EntityLivingBase player) {
        ItemFocus focus = getFocus(stack);
        if (focus != null) {
            FocusPackage fp = ItemFocus.getPackage(getFocusStack(stack));
            if (fp != null && FocusEngine.doesPackageContainElement(fp, "thaumcraft.PLAN"))
                return ((IArchitect) FocusEngine.getElement("thaumcraft.PLAN")).getArchitectMOP(getFocusStack(stack),
                        world, player);
        }
        return null;
    }

    public boolean useBlockHighlight(ItemStack stack) {
        return false;
    }

}
