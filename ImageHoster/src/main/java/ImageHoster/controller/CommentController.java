package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ImageController imageController;

    @RequestMapping(value = "/images/{imageId}/{title}/comments", method = RequestMethod.POST)
    public String uploadComments(@PathVariable("imageId") Integer imageId,
                                 @PathVariable("title") String title,
                                 Comment newComment,
                                 @RequestParam("comment") String commentText,
                                 Model model, HttpSession session) {

        User user = (User)session.getAttribute("loggeduser");
        Image image = imageService.getImage(imageId);

        newComment.setImages(image);
        newComment.setUser(user);
        newComment.setCreatedDate(new Date());
        newComment.setText(commentText);

        System.out.println("image Id -------------------- " + image.getId());
        System.out.println("user id --------------------- " + user.getId());
        System.out.println("comment text ------------------- " + commentText);
        System.out.println("date --------------------- " + newComment.getCreatedDate());
        System.out.println("comment id ------------------ " + newComment.getId());

        Comment persistedComment = commentService.createComment(newComment);

        model.addAttribute("comment", persistedComment);
        model.addAttribute("image", image);
        return imageController.showImage(title, imageId, model, session);
    }
}
