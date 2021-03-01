package com.studenttomsk.upYourParty.Classes;

public class ClassFavorites {
    private Long id;
    private SearchAnnouncementRes adsPerson;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SearchAnnouncementRes getAdsPerson() {
        return adsPerson;
    }

    public void setAdsPerson(SearchAnnouncementRes adsPerson) {
        this.adsPerson = adsPerson;
    }

    public ClassFavorites(long id, SearchAnnouncementRes adsPerson) {
        this.id = id;
        this.adsPerson = adsPerson;
    }
}
