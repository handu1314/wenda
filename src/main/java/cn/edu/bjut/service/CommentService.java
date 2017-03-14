package cn.edu.bjut.service;

import cn.edu.bjut.dao.CommentDAO;
import cn.edu.bjut.model.Comment;
import cn.edu.bjut.model.EntityType;
import cn.edu.bjut.model.HostLoginUser;
import cn.edu.bjut.util.WendaUtil;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */
@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDAO.addComment(comment) > 0 ? comment.getId() : 0;
    }

    public List<Comment> getCommentsByEntity(int entityId, int entityType, int offset, int limit){
        return commentDAO.selectCommentByEntity(entityId,entityType,offset,limit);
    }

    public int getCommentCount(int entityId,int entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }

    public boolean deleteComment(int entityId,int entityType,int status){
        return commentDAO.updateStatus(entityId,entityType,status) > 0 ? true : false;
    }
}
