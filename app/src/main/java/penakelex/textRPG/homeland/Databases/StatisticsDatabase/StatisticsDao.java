package penakelex.textRPG.homeland.Databases.StatisticsDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StatisticsDao {
    @Query("SELECT * FROM statistics")
    List<StatisticItem> getAllStatistic();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(StatisticItem item);

    @Query("UPDATE statistics SET count=:newCount WHERE id=:ID")
    void updateCount(short newCount, byte ID);

    @Query("DELETE FROM statistics")
    void deleteAll();
}
