package penakelex.textRPG.homeland.Databases;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDao;
import penakelex.textRPG.homeland.Databases.HealthDatabase.HealthDao;
import penakelex.textRPG.homeland.Databases.HealthDatabase.HealthItem;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDao;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDao;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestItem;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDao;
import penakelex.textRPG.homeland.Databases.ReputationsDatabase.ReputationDao;
import penakelex.textRPG.homeland.Databases.ReputationsDatabase.ReputationItem;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDao;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.Databases.StatisticsDatabase.StatisticItem;
import penakelex.textRPG.homeland.Databases.StatisticsDatabase.StatisticsDao;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentItem;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDao;

@androidx.room.Database(entities = {CharacteristicItem.class, HealthItem.class, InventoryItem.class, OtherInformationItem.class, QuestItem.class, ReputationItem.class, SkillsItem.class, StatisticItem.class, TalentItem.class}, version = 1)
public abstract class Database extends RoomDatabase {
    private static final String Database_Name = "main_database.db";
    private static Database database;
    public static synchronized Database getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), Database.class, Database_Name).fallbackToDestructiveMigration().build();
        }
        return database;
    }
    public abstract CharacteristicsDao characteristicsDao();
    public abstract HealthDao healthDao();
    public abstract InventoryDao inventoryDao();
    public abstract OtherInformationDao otherInformationDao();
    public abstract QuestsDao questsDao();
    public abstract ReputationDao reputationDao();
    public abstract SkillsDao skillsDao();
    public abstract StatisticsDao statisticsDao();
    public abstract TalentsDao talentsDao();
}