package dao;

import dto.CatDTO;

import static utils.SupportFactory.*;

public class CatDAO {

    public CatDAO(){}

    public CatDTO filler(){
        CatDTO CatDTO = new CatDTO();;
        try{
            setExcelFile("base");

            String name = getCellData(1, 9);
            String description = getCellData(1, 10);
            String slug = getCellData(1, 11);
            String taxonomy = getCellData(1, 12);

            CatDTO.setCat_name(name);
            CatDTO.setCat_description(description);
            CatDTO.setCat_slug(slug);
            CatDTO.setCat_taxonomy(taxonomy);

        }catch(Exception e){
            System.out.println("i couldn't retrieve nothing from excel file" + e.getMessage());
        }
        return CatDTO;
    }
}
