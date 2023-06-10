package penakelex.textRPG.homeland.Databases.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicsDao;
import penakelex.textRPG.homeland.Databases.Tables.HealthDatabase.HealthDao;
import penakelex.textRPG.homeland.Databases.Tables.HealthDatabase.HealthItem;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryDao;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationDao;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestItem;
import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestsDao;
import penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase.ReputationDao;
import penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase.ReputationItem;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsDao;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase.StatisticItem;
import penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase.StatisticsDao;
import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentItem;
import penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase.TalentsDao;

@androidx.room.Database(entities = {CharacteristicItem.class, HealthItem.class, InventoryItem.class, OtherInformationItem.class, QuestItem.class, ReputationItem.class, SkillsItem.class, StatisticItem.class, TalentItem.class}, version = 2)
public abstract class Database extends RoomDatabase {
    private static final String Database_Name = "main_database.db";
    private static Database database;

    public static synchronized Database getDatabase(Context context) {
        if (database == null) {
            database = Room.
                    databaseBuilder(context.getApplicationContext(), Database.class, Database_Name).
                    fallbackToDestructiveMigration().build();
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