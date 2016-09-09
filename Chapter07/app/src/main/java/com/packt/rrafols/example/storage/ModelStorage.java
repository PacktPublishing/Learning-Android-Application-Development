package com.packt.rrafols.example.storage;

import com.packt.rrafols.example.model.Fields;
import com.packt.rrafols.example.model.Model;

import java.util.List;

public interface ModelStorage {
    void storeModel(Model model);
    List<Fields> retrieveFieldList();
}


