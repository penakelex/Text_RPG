package com.example.textrpg.SkillsDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface SkillsDao {
    @Query("select * from SkillsModel")
    Flowable<List<SkillsModel>> getAll();

    @Insert
    Comparable insert(SkillsModel skillsModel);

    @Delete
    Comparable delete(SkillsModel skillsModel);

    @Update
    Comparable update(SkillsModel skillsModel);
}
