package penakelex.textRPG.homeland.Databases.Tables.HealthDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HealthDao {
    @Query("SELECT * FROM health_status")
    List<HealthItem> getAllHealthStatuses();

    @Insert
    void insert(HealthItem item);

    @Query("UPDATE health_status SET value=:newValue WHERE id=:ID")
    void updateValue(short newValue, byte ID);

    @Query("UPDATE health_status SET base_value=:newValue WHERE id=:ID")
    void updateBaseValue(short newValue, byte ID);
}
