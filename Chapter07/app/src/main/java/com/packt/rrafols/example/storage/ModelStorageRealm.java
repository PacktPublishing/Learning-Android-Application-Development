package com.packt.rrafols.example.storage;

import android.content.Context;

import com.packt.rrafols.example.model.Fields;
import com.packt.rrafols.example.model.Model;
import com.packt.rrafols.example.model.Resource;
import com.packt.rrafols.example.model.ResourceWrapper;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ModelStorageRealm implements ModelStorage {
    private static final String TAG = ModelStorageRealm.class.getSimpleName();

    private RealmConfiguration realmConfig;
    private Realm realm;
    private Context context;

    public ModelStorageRealm(Context context) {
        this.context = context;
    }

    @Override
    public List<Fields> retrieveFieldList() {
        checkAndCreate();

        return realm.where(Fields.class).findAll();
    }

    @Override
    public void storeModel(Model model) {
        for(ResourceWrapper resourceWrapper : model.getList().getResources()) {
            Resource res = resourceWrapper.getResource();
            Fields fields = res.getFields();

            checkAndCreate();

            realm.beginTransaction();
            realm.copyToRealm(fields);
            realm.commitTransaction();
        }
    }

    private void checkAndCreate() {
        if(realm == null) {
            realmConfig = new RealmConfiguration.Builder(context).build();
            realm = Realm.getInstance(realmConfig);
        }
    }
}


