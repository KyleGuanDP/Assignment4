package org.example;

import org.example.YouTubeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
public class YouTubeSearchController {

    @Autowired
    private YouTubeSearchService youTubeSearchService;

    @GetMapping("/")
    public String youtubeSearchPage(@RequestParam(required = false) String topic, Model model) throws IOException, GeneralSecurityException {
        if (topic != null) {
            List<YouTubeSearchService.VideoResult> results = youTubeSearchService.searchVideos(topic);
            model.addAttribute("results", results);
        }
        return "youtube-search";  // 返回模板文件名
    }
}
