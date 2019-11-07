package dao;

import dto.PostDTO;

import static utils.SupportFactory.*;

public class PostDAO {

    public PostDAO(){}

    public PostDTO filler(){
        PostDTO PostDTO = new PostDTO();;
        try{
            setExcelFile("base");

            String title = getCellData(1, 4);
            String content = getCellData(1, 5);
            int author = getCellDataI(1, 6);
            String status = getCellData(1, 7);
            String format = getCellData(1, 8);

            PostDTO.setAuthor(author);
            PostDTO.setFormat(format);
            PostDTO.setTitle(title);
            PostDTO.setPostContent(content);
            PostDTO.setStatus(status);
        }catch(Exception e){
            System.out.println("i couldn't retrieve nothing from excel file" + e.getMessage());
        }
        return PostDTO;
    }
}
