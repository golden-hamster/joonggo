package com.capstone.joonggo.controller;

import com.capstone.joonggo.domain.Comment;
import com.capstone.joonggo.domain.Member;
import com.capstone.joonggo.domain.Post;
import com.capstone.joonggo.domain.UploadFile;
import com.capstone.joonggo.dto.*;
import com.capstone.joonggo.service.CommentService;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.service.PostService;
import com.capstone.joonggo.service.UploadFileService;
import com.capstone.joonggo.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MarketController {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final UploadFileService uploadFileService;


    @GetMapping("/market")
    public String market(Model model) {
        List<Post> posts = postService.findAll();
        List<MarketDto> marketDtoList = new ArrayList<>();
        for (Post post : posts) {
            MarketDto marketDto = convertToMarketDto(post);
            marketDtoList.add(marketDto);
        }
        model.addAttribute("posts", marketDtoList);
        return "market";
    }

    @GetMapping("/market/{postId}")
    public String postForm(@PathVariable(name = "postId") Long postId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                           Model model) {
        Post post = postService.findById(postId);
        Member member = memberService.findById(memberId);

        boolean authorFlag = post.getMember().getId().equals(memberId);

        boolean loginMemberFlag = (memberId != null);

        List<Comment> comments = commentService.findByPostId(postId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDto = convertToCommentDto(comment);
            commentDtoList.add(commentDto);
        }

        PostDto postDto = convertToPostDto(post);
        model.addAttribute("post", postDto);
        model.addAttribute("authorFlag", authorFlag);
        model.addAttribute("loginMemberFlag", loginMemberFlag);


        return "post";
    }

    @ResponseBody
    @GetMapping("/images/{storeName}")
    public Resource downloadImage(@PathVariable String storeName) throws MalformedURLException {
        return new UrlResource("file:" + uploadFileService.getFullPath(storeName));
    }


    @GetMapping("/market/create")
    public String createPostForm() {
        return "createPost";
    }

    @PostMapping("/market/create")
    public String createPost(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId ,
                             @ModelAttribute CreatePostDto createPostDto,
                             RedirectAttributes redirectAttributes) throws IOException {
        List<UploadFile> uploadFiles = uploadFileService.saveFiles(createPostDto.getImageFiles());
        Member member = memberService.findById(memberId);
        Post post = Post.createPost(member, createPostDto.getTitle(), createPostDto.getContent(), createPostDto.getPrice(), uploadFiles);
        Long postId = postService.save(post);

        redirectAttributes.addAttribute("postId", postId);

        return "redirect:/market/{postId}";
    }

    @PostMapping("/market/{postId}/comment")
    public String createComment(@PathVariable(name = "postId") Long postId,
                                @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                                @ModelAttribute CommentDto commentDto) {
        Post findPost = postService.findById(postId);
        Member findMember = memberService.findById(memberId);
        Comment comment = Comment.createComment(findPost, findMember, commentDto.content);
        commentService.save(comment);
        return "redirect:/market/" + postId;
    }

    @PostMapping("/market/delete")
    public String deletePost(Long postId) {
        postService.delete(postId);
        return "redirect:/market";
    }

    @PostMapping("/market/{postId}/deleteComment")
    public String deleteComment(@PathVariable(name = "postId") Long postId, long commentId) {
        commentService.delete(commentId);
        return "/redirect:/market/" + postId;
    }

    public MarketDto convertToMarketDto(Post post) {
        return new MarketDto(post.getTitle(), post.getPrice(), post.getId(), post.getCreatedDate());
    }

    public CommentDto convertToCommentDto(Comment comment) {
        String author = comment.getMember().getNickName();
        String loginId = comment.getMember().getLoginId();
        return new CommentDto(author, loginId, comment.getContent(), comment.getCreatedDate());
    }


    public PostDto convertToPostDto(Post post) {
        String nickName = post.getMember().getNickName();
        List<UploadFile> uploadFiles = post.getUploadFiles();
        List<String> storeNames = new ArrayList<>();
        for (UploadFile uploadFile : uploadFiles) {
            String storeName = uploadFile.getStoreName();
            storeNames.add(storeName);
        }
        return new PostDto(post.getTitle(), nickName, post.getPrice(), post.getContent(), storeNames, post.getCreatedDate());
    }
}
