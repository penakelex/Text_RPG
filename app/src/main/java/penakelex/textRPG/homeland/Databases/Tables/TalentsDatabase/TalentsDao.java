package penakelex.textRPG.homeland.Databases.Tables.TalentsDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TalentsDao {
    @Query("SELECT * FROM talents")
    List<TalentItem> getAllTalents();

    @Insert
    void add(TalentItem talentItem);

    @Query("UPDATE talents SET is_having=:isHaving WHERE id=:ID")
    void changeIsHaving(boolean isHaving, byte ID);

    @Query("SELECT * FROM talents WHERE id=:ID")
    TalentItem getTalent(byte ID);

    @Query("SELECT * FROM talents WHERE is_having=:isHaving")
    List<TalentItem> getHavingTalents(boolean isHaving);
}
