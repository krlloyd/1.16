package cofh.thermal.core.compat.jei.device;

import cofh.thermal.core.client.gui.device.DeviceRockGenScreen;
import cofh.thermal.core.util.recipes.device.RockGenMapping;
import cofh.thermal.lib.compat.jei.Drawables;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_ROCK_GEN_BLOCK;

public class RockGenCategory implements IRecipeCategory<RockGenMapping> {

    protected final ResourceLocation uid;
    protected IDrawable background;
    protected IDrawable icon;
    protected ITextComponent name;

    protected IDrawableStatic progressFluidBackground;
    protected IDrawableAnimated progressFluid;

    public RockGenCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid) {

        this.uid = uid;
        this.icon = guiHelper.createDrawableIngredient(icon);

        background = guiHelper.drawableBuilder(DeviceRockGenScreen.TEXTURE, 26, 11, 124, 62)
                .addPadding(0, 0, 16, 24)
                .build();
        name = getTextComponent(DEVICE_ROCK_GEN_BLOCK.getTranslationKey());

        progressFluidBackground = Drawables.getDrawables(guiHelper).getProgressFill(Drawables.PROGRESS_ARROW_FLUID);
        progressFluid = guiHelper.createAnimatedDrawable(Drawables.getDrawables(guiHelper).getProgress(Drawables.PROGRESS_ARROW_FLUID), 40, IDrawableAnimated.StartDirection.LEFT, true);
    }

    // region IRecipeCategory
    @Override
    public ResourceLocation getUid() {

        return uid;
    }

    @Override
    public Class<? extends RockGenMapping> getRecipeClass() {

        return RockGenMapping.class;
    }

    @Override
    public String getTitle() {

        return name.getString();
    }

    @Override
    public IDrawable getBackground() {

        return background;
    }

    @Override
    public IDrawable getIcon() {

        return icon;
    }

    @Override
    public void setIngredients(RockGenMapping recipe, IIngredients ingredients) {

    }

    @Override
    public void setRecipe(IRecipeLayout layout, RockGenMapping recipe, IIngredients ingredients) {

    }

    @Override
    public void draw(RockGenMapping recipe, MatrixStack matrixStack, double mouseX, double mouseY) {

    }

}
