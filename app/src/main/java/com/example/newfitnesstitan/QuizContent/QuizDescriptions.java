package com.example.newfitnesstitan.QuizContent;

public class QuizDescriptions {

    private String name;
    private String description;
    private String mImageUrl;

    public QuizDescriptions() {

    }

    public QuizDescriptions(String name, String description, String mImageUrl) {
        this.name = name;
        this.description = description;
        this.mImageUrl = mImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
