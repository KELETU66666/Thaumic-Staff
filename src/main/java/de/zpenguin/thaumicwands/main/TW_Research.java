package de.zpenguin.thaumicwands.main;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import static thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType.OBSERVATION;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.api.research.ResearchEntry;
import thaumcraft.api.research.ResearchStage;
import thaumcraft.common.config.ConfigResearch;
import thaumcraft.common.lib.CommandThaumcraft;

import java.util.LinkedHashMap;

@EventBusSubscriber
public class TW_Research {

    public static final ResourceLocation iconThaumaturgy = new ResourceLocation(ThaumicWands.modID, "textures/research/icon_thaumaturgy.png");
    public static final ResourceLocation backThaumaturgy = new ResourceLocation(ThaumicWands.modID, "textures/research/tab_thaumaturgy.jpg");
    public static final ResourceLocation backOverlay = new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png");
    public static final AspectList tabAspects = new AspectList().add(Aspect.MAGIC, 20)
            .add(Aspect.CRAFT, 15)
            .add(Aspect.AURA, 15)
            .add(Aspect.PLANT, 15)
            .add(Aspect.ENERGY, 10);


    @SubscribeEvent
    public static void commandEvent(CommandEvent ce) {
        if (ce.getCommand() instanceof CommandThaumcraft && ce.getParameters().length > 0 && ce.getParameters()[0].equalsIgnoreCase("reload")) {
            new Thread(() -> {
                while (ResearchCategories.getResearchCategory("BASICS").research.containsKey("THAUMATURGY"))
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                init();
            }).start();
        }
    }


    public static void init() {
        ResearchCategories.registerCategory("THAUMATURGY", "FIRSTSTEPS", tabAspects, iconThaumaturgy, backThaumaturgy, backOverlay);
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(ThaumicWands.modID, "research/thaumaturgy.json"));
    }

    public static ResearchCategory getCategory(String cat) {
        return ResearchCategories.getResearchCategory(cat);
    }

    public static void postInit() {
        ResearchEntry entry = ResearchCategories.getResearch("FIRSTSTEPS");
        ResearchStage[] stages = entry.getStages();
        stages[0].setRecipes(new ResourceLocation[]{TW_Recipes.recipes.get("FIRSTSTEPS.1"), TW_Recipes.recipes.get("BASETHAUMATURGY.1"), TW_Recipes.recipes.get("BASETHAUMATURGY.2")});
        stages[0].setKnow(new ResearchStage.Knowledge[]{new ResearchStage.Knowledge(OBSERVATION, getCategory("BASICS"), 1)});
        stages[0].setCraft(null);
        stages[1].setRecipes(new ResourceLocation[]{TW_Recipes.recipes.get("BASETHAUMATURGY.1"), TW_Recipes.recipes.get("BASETHAUMATURGY.2"), TW_Recipes.recipes.get("FIRSTSTEPS.1")});
        stages[1].setKnow(new ResearchStage.Knowledge[]{new ResearchStage.Knowledge(OBSERVATION, getCategory("BASICS"), 1)});
        stages[2].setRecipes(new ResourceLocation[]{TW_Recipes.recipes.get("BASETHAUMATURGY.1"), TW_Recipes.recipes.get("BASETHAUMATURGY.2"), TW_Recipes.recipes.get("FIRSTSTEPS.1")});
        entry.setStages(stages);

        ConfigResearch.TCCategories = new String[]{"BASICS", "THAUMATURGY", "ALCHEMY", "AUROMANCY", "ARTIFICE", "INFUSION", "GOLEMANCY", "ELDRITCH"};
        LinkedHashMap<String, ResearchCategory> map = new LinkedHashMap<>();
        map.put("BASICS", ResearchCategories.researchCategories.get("BASICS"));
        map.put("THAUMATURGY", ResearchCategories.researchCategories.get("THAUMATURGY"));
        for (String s : ResearchCategories.researchCategories.keySet()) {
            if (!map.containsKey(s))
                map.put(s, ResearchCategories.researchCategories.get(s));
        }

        ResearchCategories.researchCategories = map;
    }

}
