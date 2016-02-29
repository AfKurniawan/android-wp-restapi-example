package com.tiarf.wprestapitest;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Post implements Parcelable {
    private int id;
    private String date; // "yyyy-MM-dd'T'HH:mm:ss"
    private String slug;
    private String type;
    private Post_Rendered title;
    private Post_Rendered content;
    private Post_Rendered excerpt;
    private int author;
    private int featured_media;
    private int[] categories;
    private int[] tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Just get a i18n formated date
     * @param \String based_date
     * @param \Locale lang
     * @return String
     */
    public String getI18nFormatedDate ( String based_date, String format, Locale lang) {

        // Format the post date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = sdf.parse(based_date);
            return new SimpleDateFormat( format, lang ).format(date);
        } catch ( ParseException e ) {
            return "error";
        }

    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Post_Rendered getTitle() {
        return title;
    }

    public void setTitle(Post_Rendered title) {
        this.title = title;
    }

    public Post_Rendered getContent() {
        return content;
    }

    public void setContent(Post_Rendered content) {
        this.content = content;
    }

    public Post_Rendered getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(Post_Rendered excerpt) {
        this.excerpt = excerpt;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public int getFeatured_media() {
        return featured_media;
    }

    public void setFeatured_media(int featured_media) {
        this.featured_media = featured_media;
    }

    public int[] getCategories() {
        return categories;
    }

    public void setCategories(int[] categories) {
        this.categories = categories;
    }

    public int[] getTags() {
        return tags;
    }

    public void setTags(int[] tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(slug);
        dest.writeString(type);
        dest.writeInt(author);
        dest.writeInt(featured_media);
        dest.writeIntArray(categories);
        dest.writeIntArray(tags);
//
//        dest.writeParcelable(title, flags);
//        dest.writeParcelable(excerpt, flags);
//        dest.writeParcelable(content, flags);
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>()
    {
        @Override
        public Post createFromParcel(Parcel source)
        {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size)
        {
            return new Post[size];
        }
    };

    public Post(Parcel in) {
        this.id = in.readInt();
        this.date = in.readString();
        this.slug = in.readString();
        this.type = in.readString();
        this.author = in.readInt();
        this.featured_media = in.readInt();
//        this.categories = in.readIntArray();
//        this.tags = in.readIntArray();

//        this.title = in.readParcelable( Post_Rendered.class.getClassLoader() );
//        this.excerpt = in.readParcelable( Post_Rendered.class.getClassLoader() );
//        this.content = in.readParcelable( Post_Rendered.class.getClassLoader() );
        this.title = Post_Rendered.CREATOR.createFromParcel(in);
        this.excerpt = Post_Rendered.CREATOR.createFromParcel(in);
        this.content = Post_Rendered.CREATOR.createFromParcel(in);
    }
}

class Post_Rendered implements Parcelable{

    private String rendered;

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(rendered);
    }


    public static final Parcelable.Creator<Post_Rendered> CREATOR = new Parcelable.Creator<Post_Rendered>()
    {
        @Override
        public Post_Rendered createFromParcel(Parcel source)
        {
            return new Post_Rendered(source);
        }

        @Override
        public Post_Rendered[] newArray(int size)
        {
            return new Post_Rendered[size];
        }
    };

    public Post_Rendered(Parcel in) {
        this.rendered = in.readString();
    }
}