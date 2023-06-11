package penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CharacteristicsDao {
    @Query("SELECT * FROM characteristics")
    List<CharacteristicItem> getAllCharacteristics();

    @Insert
    void addCharacteristic(CharacteristicItem item);

    @Query("UPDATE characteristics SET value=:newValue WHERE id=:ID")
    void updateValue(byte newValue, byte ID);

    @Query("SELECT * FROM characteristics WHERE id=:ID")
    CharacteristicItem getCharacteristic(byte ID);
}
