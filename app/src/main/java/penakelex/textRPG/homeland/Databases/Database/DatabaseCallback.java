package penakelex.textRPG.homeland.Databases.Database;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.Databases.Tables.HealthDatabase.HealthItem;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestItem;
import penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase.ReputationItem;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase.StatisticItem;
import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.ViewModels.HealthViewModel.HealthViewModel;
import penakelex.textRPG.homeland.ViewModels.InventoryViewModel.InventoryViewModel;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.ViewModels.QuestsViewModel.QuestsViewModel;
import penakelex.textRPG.homeland.ViewModels.ReputationViewModel.ReputationViewModel;
import penakelex.textRPG.homeland.ViewModels.SkillsViewModel.SkillsViewModel;
import penakelex.textRPG.homeland.ViewModels.StatisticsViewModel.StatisticsViewModel;
import penakelex.textRPG.homeland.ViewModels.TalentsViewModel.TalentsViewModel;

/** DatabaseCallback
 *      Класс для установки стартовых значений в базу данных, либо обновления имеющихся
 * */
public class DatabaseCallback {
    private final CharacteristicsViewModel characteristicsViewModel;
    private final HealthViewModel healthViewModel;
    private final InventoryViewModel inventoryViewModel;
    private final QuestsViewModel questsViewModel;
    private final ReputationViewModel reputationViewModel;
    private final SkillsViewModel skillsViewModel;
    private final StatisticsViewModel statisticsViewModel;
    private final TalentsViewModel talentsViewModel;
    private final OtherInformationViewModel otherInformationViewModel;

    public DatabaseCallback(@NonNull Application application, ViewModelStoreOwner owner) {
        this.characteristicsViewModel = new ViewModelProvider(owner).get(CharacteristicsViewModel.class);
        this.characteristicsViewModel.initiate(application);
        this.healthViewModel = new ViewModelProvider(owner).get(HealthViewModel.class);
        this.healthViewModel.initiate(application);
        this.inventoryViewModel = new ViewModelProvider(owner).get(InventoryViewModel.class);
        this.inventoryViewModel.initiate(application);
        this.questsViewModel = new ViewModelProvider(owner).get(QuestsViewModel.class);
        this.questsViewModel.initiate(application);
        this.reputationViewModel = new ViewModelProvider(owner).get(ReputationViewModel.class);
        this.reputationViewModel.initiate(application);
        this.skillsViewModel = new ViewModelProvider(owner).get(SkillsViewModel.class);
        this.skillsViewModel.initiate(application);
        this.statisticsViewModel = new ViewModelProvider(owner).get(StatisticsViewModel.class);
        this.statisticsViewModel.initiate(application);
        this.talentsViewModel = new ViewModelProvider(owner).get(TalentsViewModel.class);
        this.talentsViewModel.initiate(application);
        this.otherInformationViewModel = new ViewModelProvider(owner).get(OtherInformationViewModel.class);
        this.otherInformationViewModel.initiate(application);
        initiate();
    }

    private void initiate() {
        characteristics();
        health();
        inventory();
        quests();
        reputation();
        skills();
        statistics();
        talents();
        otherInformation();
    }

    private void characteristics() {
        List<CharacteristicItem> characteristicItems = characteristicsViewModel.getAllCharacteristics();
        if (characteristicItems.size() == 0) {
            characteristicsViewModel.add(new CharacteristicItem(R.string.strength));
            characteristicsViewModel.add(new CharacteristicItem(R.string.physique));
            characteristicsViewModel.add(new CharacteristicItem(R.string.dexterity));
            characteristicsViewModel.add(new CharacteristicItem(R.string.mentality));
            characteristicsViewModel.add(new CharacteristicItem(R.string.luckiness));
            characteristicsViewModel.add(new CharacteristicItem(R.string.watchfulness));
            characteristicsViewModel.add(new CharacteristicItem(R.string.attractiveness));
        } else {
            for (CharacteristicItem item : characteristicItems) {
                characteristicsViewModel.update((byte) 2, item.getID());
            }
        }
    }

    private void health() {
        List<HealthItem> healthStatuses = healthViewModel.getAllHealthStatuses();
        if (healthStatuses.size() == 0) {
            healthViewModel.add(new HealthItem(R.string.health_points));
            healthViewModel.add(new HealthItem(R.string.left_arm));
            healthViewModel.add(new HealthItem(R.string.right_arm));
            healthViewModel.add(new HealthItem(R.string.head));
            healthViewModel.add(new HealthItem(R.string.left_leg));
            healthViewModel.add(new HealthItem(R.string.right_leg));
            healthViewModel.add(new HealthItem(R.string.torso));
        }
    }

    private void inventory() {
        List<InventoryItem> inventoriesItems = inventoryViewModel.getAllInventoriesItems();
        if (inventoriesItems.size() != 0) {
            inventoryViewModel.deleteAll();
        }
    }

    private void quests() {
        List<QuestItem> quests = questsViewModel.getAllQuests();
        if (quests.size() != 0) {
            for (QuestItem item : quests) {
                questsViewModel.updateQuestStage((short) 0, item.getID());
            }
            questsViewModel.updateQuestStage((short) 1, 1);
        } else {
            questsViewModel.add(new QuestItem(R.string.quest_registration));
        }
    }

    private void reputation() {
        List<ReputationItem> reputations = reputationViewModel.getAllReputation();
        if (reputations.size() != 0) {
            for (ReputationItem item : reputations) {
                reputationViewModel.update((byte) 0, item.getID());
            }
        }
    }

    private void skills() {
        List<SkillsItem> skills = skillsViewModel.getAllSkills();
        if (skills.size() == 0) {
            skillsViewModel.add(new SkillsItem(R.string.lightWeapons));
            skillsViewModel.add(new SkillsItem(R.string.heavyWeapons));
            skillsViewModel.add(new SkillsItem(R.string.meleeWeapons));
            skillsViewModel.add(new SkillsItem(R.string.communication));
            skillsViewModel.add(new SkillsItem(R.string.trading));
            skillsViewModel.add(new SkillsItem(R.string.survival));
            skillsViewModel.add(new SkillsItem(R.string.medicine));
            skillsViewModel.add(new SkillsItem(R.string.scince));
            skillsViewModel.add(new SkillsItem(R.string.repair));
        } else {
            for (SkillsItem skillsItem : skills) {
                skillsViewModel.updateIsMain((byte) 0, skillsItem.getID());
            }
        }
    }

    private void statistics() {
        List<StatisticItem> statistics = statisticsViewModel.getAllStatistic();
        if (statistics.size() == 0) {
            statisticsViewModel.add(new StatisticItem(R.string.successful_skill_using));
        } else {
            for (StatisticItem item : statistics) {
                statisticsViewModel.updateCount((short) 0, item.getID());
            }
        }
    }

    private void talents() {
        List<TalentItem> talents = talentsViewModel.getAllTalents();
        if (talents.size() == 0) {
            talentsViewModel.add(new TalentItem(R.string.singer));
            talentsViewModel.add(new TalentItem(R.string.bull));
            talentsViewModel.add(new TalentItem(R.string.strong_kick));
            talentsViewModel.add(new TalentItem(R.string.experienced));
            talentsViewModel.add(new TalentItem(R.string.trained));
            talentsViewModel.add(new TalentItem(R.string.heavyweight));
            talentsViewModel.add(new TalentItem(R.string.kind_one));
        } else {
            for (TalentItem item : talents) {
                talentsViewModel.changeIsHaving(false, item.getID());
            }
        }
    }

    private void otherInformation() {
        List<OtherInformationItem> information = otherInformationViewModel.getAllOtherInformation();
        if (information.size() == 0) {
            otherInformationViewModel.add(new OtherInformationItem(R.string.armor_class));
            otherInformationViewModel.add(new OtherInformationItem(R.string.ap));
            otherInformationViewModel.add(new OtherInformationItem(R.string.max_carry_weight));
            otherInformationViewModel.add(new OtherInformationItem(R.string.max_carry_volume));
            otherInformationViewModel.add(new OtherInformationItem(R.string.melee_damage));
            otherInformationViewModel.add(new OtherInformationItem(R.string.critical_damage));
            otherInformationViewModel.add(new OtherInformationItem(R.string.health_points));
        }
    }
}
