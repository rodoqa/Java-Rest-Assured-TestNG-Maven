package dto;

public class TagDTO {
    private String tag_taxonomy;
    private String tag_name;
    private String tag_slug;
    private String tag_description;

    public String getTag_taxonomy() {
        return tag_taxonomy;
    }

    public String getTag_name() {
        return tag_name;
    }

    public String getTag_slug() {
        return tag_slug;
    }

    public String getTag_description() {
        return tag_description;
    }

    public void setTag_taxonomy(String tag_taxonomy) {
        this.tag_taxonomy = tag_taxonomy;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public void setTag_slug(String tag_slug) {
        this.tag_slug = tag_slug;
    }

    public void setTag_description(String tag_description) {
        this.tag_description = tag_description;
    }
}
