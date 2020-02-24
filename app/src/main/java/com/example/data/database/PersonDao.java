package com.example.data.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.data.model.PersonModel;

import java.util.List;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM Person ORDER BY ID")
    LiveData<List<PersonModel>> loadAllPersons();

    @Insert
    void insertPerson(PersonModel person);

    @Update
    void updatePerson(PersonModel person);

    @Delete
    void delete(PersonModel person);

    @Query("SELECT * FROM Person WHERE id = :id")
    PersonModel loadPersonById(int id);
}
