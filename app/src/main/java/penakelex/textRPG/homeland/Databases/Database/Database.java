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

/** Database
 *      База данных с таблицами
 */
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

    //Объект доступа к данным для характеристик
    public abstract CharacteristicsDao characteristicsDao();

    //Объект доступа к данным для статусов здоровья
    public abstract HealthDao healthDao();

    //Объект доступа к данным для инвентаря
    public abstract InventoryDao inventoryDao();

    //Объект доступа к данным для прочей информации
    public abstract OtherInformationDao otherInformationDao();

    //Объект доступа к данным для заданий
    public abstract QuestsDao questsDao();

    //Объект доступа к данным для репутаций
    public abstract ReputationDao reputationDao();

    //Объект доступа к данным для навыков
    public abstract SkillsDao skillsDao();

    //Объект доступа к данным для статистики
    public abstract StatisticsDao statisticsDao();

    //Объект доступа к данным для талантов
    public abstract TalentsDao talentsDao();
}