package com.example.DiscoverTheUniverse.OnboardingAdapters;

public class ScreenItem {

    private String Title;
    private String Description;
    private int ScreenImg;

    public ScreenItem(String title, String description, int screenImg) {
        Title = title;
        Description = description;
        ScreenImg = screenImg;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    String getTitle() {
        return Title;
    }

    String getDescription() {
        return Description;
    }

    int getScreenImg() {
        return ScreenImg;
    }


}
