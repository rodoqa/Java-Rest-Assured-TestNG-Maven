package dao;

import dto.TagDTO;

import static utils.SupportFactory.*;

public class TagDAO {
    public TagDAO(){}

    public TagDTO filler(){
        TagDTO TagDTO = new TagDTO();;
        try{
            setExcelFile("base");

            String name = getCellData(1, 13);
            String description = getCellData(1, 14);
            String slug = getCellData(1, 15);
            String taxonomy = getCellData(1, 16);

            TagDTO.setTag_name(name);
            TagDTO.setTag_description(description);
            TagDTO.setTag_slug(slug);
            TagDTO.setTag_taxonomy(taxonomy);

        }catch(Exception e){
            System.out.println("i couldn't retrieve nothing from excel file" + e.getMessage());
        }
        return TagDTO;
    }
}
