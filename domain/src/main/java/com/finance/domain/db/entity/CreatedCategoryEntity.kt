package com.finance.domain.db.entity

import androidx.room.Entity


@Entity(tableName = "created_category")
data class CreatedCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon")
    val icon: Int
)