package com.starbettipz.data.db.daos

//@Dao
//interface TipDao {
//    //get all free items
//    @Query("SELECT * FROM tips")
//    fun getAll():Flow<List<TipModel>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun create(data: TipModel)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun createMany(data: List<TipModel>)
//    @Delete
//    suspend fun delete(data: TipModel)
//
//    @Update
//    suspend fun update(data: TipModel)
//}