package com.example.textrpg.SkillsDatabase;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SkillsModel.class}, version = 1)
public abstract class SkillsDatabase extends RoomDatabase {
    public abstract SkillsDao skillsDao();
}
