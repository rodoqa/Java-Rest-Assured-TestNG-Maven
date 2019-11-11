package dto;

public class CatDTO {
    private String cat_taxonomy;
    private String cat_name;
    private String cat_slug;
    private String cat_description;

    public String getCat_taxonomy() {
        return cat_taxonomy;
    }

    public String getCat_name() {
        return cat_name;
    }

    public String getCat_slug() {
        return cat_slug;
    }

    public String getCat_description() {
        return cat_description;
    }

    public void setCat_taxonomy(String cat_taxonomy) {
        this.cat_taxonomy = cat_taxonomy;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public void setCat_slug(String cat_slug) {
        this.cat_slug = cat_slug;
    }

    public void setCat_description(String cat_description) {
        this.cat_description = cat_description;
    }
}
