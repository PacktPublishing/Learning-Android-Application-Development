package com.packt.rrafols.example.model;

public class List {
    private Meta meta;
    private ResourceWrapper[] resources;

    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public ResourceWrapper[] getResources() {
        return resources;
    }
    public void setResources(ResourceWrapper[] resources) {
        this.resources = resources;
    }
}



