package com.tiarf.wprestapitest;

import java.util.Objects;

public class Media {
    private int id;
    private String date;
    private Media_Rendered guid;
    private String slug;
    private String type;
    private String link;
    private Media_Rendered title;
    private int author;
    private String alt_text;
    private String caption;
    private String description;
    private String media_type;
    private String mime_type;
    private Media_Details media_details;
    private String source_url;
}

class Media_Rendered {

    private String rendered;

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}

class Media_Details {

    private int width;
    private int height;
    private String file;
    private Media_Details_Sizes sizes;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Media_Details_Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Media_Details_Sizes sizes) {
        this.sizes = sizes;
    }
}

class Media_Details_Sizes {

    private Media_Details_Sizes_Media thumbnail;
    private Media_Details_Sizes_Media medium;
    private Media_Details_Sizes_Media medium_large;
    private Media_Details_Sizes_Media large;
    private Media_Details_Sizes_Media full;

    public Media_Details_Sizes_Media getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Media_Details_Sizes_Media thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Media_Details_Sizes_Media getMedium() {
        return medium;
    }

    public void setMedium(Media_Details_Sizes_Media medium) {
        this.medium = medium;
    }

    public Media_Details_Sizes_Media getMedium_large() {
        return medium_large;
    }

    public void setMedium_large(Media_Details_Sizes_Media medium_large) {
        this.medium_large = medium_large;
    }

    public Media_Details_Sizes_Media getLarge() {
        return large;
    }

    public void setLarge(Media_Details_Sizes_Media large) {
        this.large = large;
    }

    public Media_Details_Sizes_Media getFull() {
        return full;
    }

    public void setFull(Media_Details_Sizes_Media full) {
        this.full = full;
    }
}

class Media_Details_Sizes_Media {
    private String file;
    private int width;
    private int height;
    private String mime_type;
    private String source_url;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }
}