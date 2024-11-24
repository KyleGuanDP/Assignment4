package org.example;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static org.example.generated.Tables.YOUTUBE_SEARCH_CACHE;

@Service
public class YouTubeSearchService {

    private static final String API_KEY = System.getenv("API_Key_Google"); // 替换为你的API密钥

    @Autowired
    private DSLContext dsl;

    public List<VideoResult> searchVideos(String query) throws IOException, GeneralSecurityException {
        // 检查数据库中是否已有该查询的结果
        List<VideoResult> cachedResults = getCachedResults(query);
        if (cachedResults != null) {
            return cachedResults; // 如果有缓存数据，直接返回
        }

        // 如果没有缓存结果，从 YouTube API 获取
        YouTube youtubeService = getYouTubeService();
        YouTube.Search.List search = youtubeService.search().list("snippet");
        search.setQ(query);
        search.setType("video");
        search.setKey(API_KEY);
        search.setMaxResults(10L);

        SearchListResponse response = search.execute();
        List<SearchResult> searchResultList = response.getItems();

        // 将结果格式化并存储到数据库
        List<VideoResult> videoResults = formatResults(searchResultList);
        cacheSearchResults(query, videoResults);

        return videoResults;
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

    // 从数据库中获取缓存结果
    private List<VideoResult> getCachedResults(String query) {
        var record = dsl.select()
                .from(YOUTUBE_SEARCH_CACHE)
                .where(YOUTUBE_SEARCH_CACHE.QUERY.eq(query))
                .fetchOne();

        if (record != null) {
            String resultsJson = String.valueOf(record.get(YOUTUBE_SEARCH_CACHE.RESULTS));
            // 假设 VideoResult 可以通过 JSON 反序列化
            System.out.println("fetched");
            return deserializeVideoResults(resultsJson);
        }
        return null;
    }

    // 将搜索结果缓存到数据库
    private void cacheSearchResults(String query, List<VideoResult> videoResults) {
        String resultsJson = serializeVideoResults(videoResults);
        dsl.insertInto(YOUTUBE_SEARCH_CACHE, YOUTUBE_SEARCH_CACHE.QUERY, YOUTUBE_SEARCH_CACHE.RESULTS)
                .values(query, JSONB.valueOf(resultsJson))
                .execute();
    }

    // 将 VideoResult 列表序列化为 JSON 字符串
    private String serializeVideoResults(List<VideoResult> videoResults) {
        // 使用 Gson 或其他 JSON 序列化工具
        return new Gson().toJson(videoResults);
    }

    // 从 JSON 字符串反序列化为 VideoResult 列表
    private List<VideoResult> deserializeVideoResults(String json) {
        // 使用 Gson 或其他 JSON 序列化工具
        return new Gson().fromJson(json, new TypeToken<List<VideoResult>>(){}.getType());
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
