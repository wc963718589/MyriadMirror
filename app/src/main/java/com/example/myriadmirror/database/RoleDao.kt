package com.example.myriadmirror.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myriadmirror.model.RoleData
import kotlinx.coroutines.flow.Flow

@Dao
interface RoleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(role: RoleData)

    @Update
    suspend fun update(role: RoleData)

    @Delete
    suspend fun delete(role: RoleData)

    @Query("SELECT * from roles WHERE roleId = :roleId")
    fun getRole(roleId: Int): Flow<RoleData>

    @Query("SELECT * from roles ORDER BY roleId ASC")
    fun getAllRoles(): Flow<List<RoleData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(roles: List<RoleData>)
}