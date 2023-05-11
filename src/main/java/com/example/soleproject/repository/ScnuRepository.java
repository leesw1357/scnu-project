package com.example.soleproject.repository;

import com.example.soleproject.entity.Player;
import com.example.soleproject.entity.Scnu;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ScnuRepository extends CrudRepository<Scnu,Long> {
    @Override
    ArrayList<Scnu> findAll();
}
