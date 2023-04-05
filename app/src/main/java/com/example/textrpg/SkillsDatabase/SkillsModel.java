package com.example.textrpg.SkillsDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class SkillsModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name ="skill_name")
    public String skillName;
    @ColumnInfo(name = "is_main_skill")
    public boolean isMainSkill;

    public SkillsModel(String skillName, boolean isMainSkill) {
        this.skillName = skillName;
        this.isMainSkill = isMainSkill;
    }
}
