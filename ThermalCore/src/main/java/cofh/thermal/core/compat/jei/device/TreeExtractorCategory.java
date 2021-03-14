package cofh.thermal.core.compat.jei.device;

import cofh.thermal.core.util.recipes.device.TreeExtractorMapping;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_TREE_EXTRACTOR_BLOCK;

public class TreeExtractorCategory implements IRecipeCategory<TreeExtractorMapping> {

    protected final ResourceLocation uid;
    protected IDrawable background;
    protected IDrawable icon;
    protected ITextComponent name;

    public TreeExtractorCategory(IGuiHelper guiHelper, ItemStack icon, ResourceLocation uid) {

        this.uid = uid;
        this.icon = guiHelper.createDrawableIngredient(icon);
        this.name = getTextComponent(DEVICE_TREE_EXTRACTOR_BLOCK.getTranslationKey());
    }

    // region IRecipeCategory
    @Override
    public ResourceLocation getUid() {

        return uid;
    }

    @Override
    public Class<? extends TreeExtractorMapping> getRecipeClass() {

        return TreeExtractorMapping.class;
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
    public void setIngredients(TreeExtractorMapping recipe, IIngredients ingredients) {

    }

    @Override
    public void setRecipe(IRecipeLayout layout, TreeExtractorMapping recipe, IIngredients ingredients) {

    }

}
