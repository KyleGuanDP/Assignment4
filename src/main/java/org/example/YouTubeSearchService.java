package org.example.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class YouTubeSearchService {

    private static final String API_KEY = System.getenv("API_KEY"); // 替换为你的API密钥

    public List<VideoResult> searchVideos(String query) throws IOException, GeneralSecurityException {
        YouTube youtubeService = getYouTubeService();
        YouTube.Search.List search = youtubeService.search().list("snippet");
        search.setQ(query);
        search.setType("video");
        search.setKey(API_KEY);
        search.setMaxResults(10L);

        SearchListResponse response = search.execute();
        List<SearchResult> searchResultList = response.getItems();

        return formatResults(searchResultList);
    }

    private YouTube getYouTubeService() throws IOException, GeneralSecurityException {
        return new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                request -> {})
                .setApplicationName("youtube-search-example")
                .build();
    }

    private List<VideoResult> formatResults(List<SearchResult> searchResultList) {
        List<VideoResult> videoResults = new ArrayList<>();
        if (searchResultList != null) {
            for (SearchResult result : searchResultList) {
                String videoId = result.getId().getVideoId();
                String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
                String title = result.getSnippet().getTitle();
                String thumbnailUrl = result.getSnippet().getThumbnails().getDefault().getUrl();

                videoResults.add(new VideoResult(title, videoUrl, thumbnailUrl));
            }
        }
        return videoResults;
    }

    public static class VideoResult {
        private String title;
        private String url;
        private String thumbnailUrl;

        public VideoResult(String title, String url, String thumbnailUrl) {
            this.title = title;
            this.url = url;
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }
    }
}
