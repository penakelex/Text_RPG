package penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SkillsDao {
    @Query("SELECT * FROM skills")
    LiveData<List<SkillsItem>> getAllSkills();

    @Query("UPDATE skills SET value=:newValue WHERE id=:ID")
    void updateValue(byte newValue, byte ID);

    @Insert
    void add(SkillsItem item);

    @Query("UPDATE skills SET is_main=:isMain WHERE id=:ID")
    void updateIsMain(byte isMain, byte ID);

    @Query("SELECT * FROM skills WHERE id=:ID")
    LiveData<SkillsItem> getSkill(byte ID);
}
