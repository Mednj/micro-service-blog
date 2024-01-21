package com.naja.springblog.controller;



import javax.validation.Valid;

import com.naja.springblog.kafka.JsonKafkaProducer;
import com.naja.springblog.payload.AppResponse;
import com.naja.springblog.payload.CommentRequest;
import com.naja.springblog.payload.SuccessResponse;
import com.naja.springblog.security.CurrentUser;
import com.naja.springblog.security.UserPrincipal;
import com.naja.springblog.service.CommentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final JsonKafkaProducer jsonKafkaProducer;  // Inject the JsonKafkaProducer
    // inject logger
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    public CommentController(CommentService commentService, JsonKafkaProducer jsonKafkaProducer) {
        this.commentService = commentService;
        this.jsonKafkaProducer = jsonKafkaProducer;
    }

    @ApiOperation(value = "Save Comment in Post by id")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping(value = "/{postId}")
    public ResponseEntity<AppResponse> saveComment(
        @ApiParam(value = "post id") @PathVariable("postId") Long postId, @RequestBody @Valid CommentRequest commentRequest) {     
        commentService.save(postId,commentRequest);
        // Publish an event to Kafka when a comment is created
        jsonKafkaProducer.sendMessage(commentService.save(postId,commentRequest));
        return ResponseEntity.ok().body(new SuccessResponse("Comment Created Successfully"));
    }

    @ApiOperation(value = "Save sub Comment")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping(value = "/{postId}/subComment/{commentId}")
    public ResponseEntity<AppResponse> saveSubComment(
        @ApiParam(value = "post id") @PathVariable("postId") Long postId,
        @ApiParam(value = "comment id") @PathVariable("commentId") Long commentId,
        @RequestBody @Valid CommentRequest commentRequest, @CurrentUser UserPrincipal currentUser) {       
        commentService.addCommentToParentId(postId,commentId, commentRequest, currentUser);
        jsonKafkaProducer.sendMessage(commentService.save(postId,commentRequest));
        // log
        LOGGER.info("Sub Comment created successfully");
        return ResponseEntity.ok().body(new SuccessResponse("Comment Created Successfully"));
    }

    @ApiOperation(value = "Update Comment by id")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping(value = "/{commentId}")
    public ResponseEntity<AppResponse> update(
        @ApiParam(value = "comment id") @PathVariable("commentId") Long commentId,
        @RequestBody @Valid CommentRequest commentRequest,
        @CurrentUser UserPrincipal currentUser) {       
        commentService.update(commentId,commentRequest,currentUser);
        return ResponseEntity.ok().body(new SuccessResponse("Comment Updated Successfully"));
    }

    @ApiOperation(value = "Find Comment by id")
   
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {       
        return ResponseEntity.ok().body(commentService.findById(id));
    }

    @ApiOperation(value = "Delete Comment by id")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping(value = "/{id}/delete")
    public ResponseEntity<AppResponse> deleteById(@PathVariable("id") Long id,@CurrentUser UserPrincipal currentUser) {  
        commentService.deleteById(id, currentUser);     
        return ResponseEntity.ok().body(new SuccessResponse("Comment Deleted Successfully"));
    }
    
}
