package com.capstone.joonggo.controller;

import com.capstone.joonggo.domain.*;
import com.capstone.joonggo.dto.*;
import com.capstone.joonggo.search.PostSearch;
import com.capstone.joonggo.service.CommentService;
import com.capstone.joonggo.service.MemberService;
import com.capstone.joonggo.service.PostService;
import com.capstone.joonggo.service.UploadFileService;
import com.capstone.joonggo.session.SessionConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MarketController {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final UploadFileService uploadFileService;


    @GetMapping("/market")
    public String market(@ModelAttribute PostSearch search, Model model,
                         @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                         @PageableDefault(page = 0, size = 20, direction = Sort.Direction.DESC) Pageable pageable) {
        Member member = null;
        Page<Post> posts = postService.findAll(search, pageable);
        if (memberId != null) {
            member = memberService.findById(memberId);
        }
        MemberDto memberDto = member != null ? new MemberDto(member) : null;

        List<MarketDto> marketDtoList = new ArrayList<>();
        for (Post post : posts) {
            MarketDto marketDto = convertToMarketDto(post);
            marketDtoList.add(marketDto);
        }


        model.addAttribute("member", memberDto);
        model.addAttribute("posts", marketDtoList);
        model.addAttribute("paging", posts);
        return "market";
    }

    @GetMapping("/market/{postId}")
    public String postForm(@PathVariable(name = "postId") Long postId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                           Model model) {
        Post post = postService.findById(postId);

        boolean authorFlag = post.getMember().getId().equals(memberId);

        List<Comment> comments = commentService.findByPostId(postId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDto = convertToCommentDto(comment);
            commentDtoList.add(commentDto);
        }

        PostDto postDto = new PostDto(post);
        model.addAttribute("post", postDto);
        model.addAttribute("comments", commentDtoList);
        model.addAttribute("authorFlag", authorFlag);

        return "post";
    }

    @ResponseBody
    @GetMapping("/images/{storeName}")
    public Resource downloadImage(@PathVariable String storeName) throws MalformedURLException {
        return new UrlResource("file:" + uploadFileService.getFullPath(storeName));
    }


    @GetMapping("/market/create")
    public String createPostForm(Model model) {
        model.addAttribute("createPostDto", new CreatePostDto());
        return "createPost2";
    }

    @PostMapping("/market/create")
    public String createPost(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                             @Valid @ModelAttribute CreatePostDto createPostDto,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "createPost2";
        }

        List<UploadFile> uploadFiles = uploadFileService.saveFiles(createPostDto.getImageFiles());
        Member member = memberService.findById(memberId);
        createPostDto.setMemberId(memberId);
        Long postId = postService.save(createPostDto, uploadFiles);
        redirectAttributes.addAttribute("postId", postId);

        return "redirect:/market/{postId}";
    }

    @PostMapping("/market/{postId}/comment")
    public String createComment(@PathVariable(name = "postId") Long postId,
                                @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                                @Valid @ModelAttribute CommentDto commentDto,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Post post = postService.findById(postId);
        Member member = memberService.findById(memberId);
        Comment comment = Comment.createComment(post, member, commentDto.content);

        if (post == null) {
            return "redirect:/market";
        }

        Comment savedComment = commentService.save(comment);
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/market/{postId}";
    }

    @PostMapping("/market/delete")
    public String deletePost(Long postId) {
        Post post = postService.findById(postId);
        if (post == null) {
            return "redirect:/market";
        }
        postService.delete(postId);
        return "redirect:/market";
    }

    @PostMapping("/deleteComment")
    public String deleteComment(Long postId, Long commentId, RedirectAttributes redirectAttributes) {
        commentService.delete(commentId);
        Post post = postService.findById(postId);
        if (post == null) {
            return "redirect:/market";
        }
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/market/{postId}";
    }

    @PostMapping("/market/updateStatus")
    public String updatePostStatus(Long postId, RedirectAttributes redirectAttributes) {
        postService.updateStatus(postId, PostStatus.COMPLETED);
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/market/{postId}";
    }

    @GetMapping("/market/{postId}/update")
    public String updatePostForm(@PathVariable Long postId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Long memberId,
                                 CreatePostDto createPostDto,Model model) {
        Member member = memberService.findById(memberId);
        Post post = postService.findById(postId);
        if (post.getMember() != member) {
            return "redirect:/market";
        }

        PostDto postDto = new PostDto(post);

        model.addAttribute("post", postDto);
        return "updatePost";
    }

    @PostMapping("/market/{postId}/update")
    public String updatePost(@PathVariable Long postId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER)Long memberId,
                             CreatePostDto createPostDto) throws IOException {
        List<UploadFile> uploadFiles = uploadFileService.saveFiles(createPostDto.getImageFiles());
        postService.update(postId, createPostDto.getTitle(), createPostDto.getContent(), createPostDto.getPrice(), uploadFiles);
        return "redirect:/market/" + postId;
    }

    public MarketDto convertToMarketDto(Post post) {
        List<UploadFile> uploadFiles = post.getUploadFiles();
        String thumbnailName = null;
        if (uploadFiles != null && !uploadFiles.isEmpty()) {
            UploadFile uploadFile = uploadFiles.get(0);
            thumbnailName = uploadFile.getStoreName();
        } else {
//            thumbnailName = "default.png";
            thumbnailName = "default.jpg";
        }

        return new MarketDto(post.getTitle(), post.getPrice(), post.getId(), thumbnailName, post.getCreatedDate());
    }

    public CommentDto convertToCommentDto(Comment comment) {
        String author = comment.getMember().getNickName();
        String email = comment.getMember().getEmail();
        return new CommentDto(comment.getId(), author, email, comment.getContent(), comment.getCreatedDate());
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
