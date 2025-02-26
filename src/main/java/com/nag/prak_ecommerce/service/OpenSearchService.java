package com.nag.prak_ecommerce.service;

import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.get.GetResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.index.query.MatchAllQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.SearchHit;
import org.opensearch.search.SearchHits;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpenSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<Map<String, Object>> searchProducts(String indexName, String searchQuery) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", searchQuery));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit hit : hits) {
            results.add(hit.getSourceAsMap());
        }
        return results;
    }

    public List<Map<String, Object>> searchMovies(String indexName, String searchQuery) throws IOException {
        System.out.println("inside movies method");
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // Example: Search by title or director. You can add more fields as needed.
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchQuery, "title", "director", "actor"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit hit : hits) {
            results.add(hit.getSourceAsMap());
        }
        return results;
    }

    public void addProduct(Map<String, Object> product) throws IOException {
        String productId = (String) product.get("id"); // Assuming "id" field exists
        String productJson = objectMapper.writeValueAsString(product);
        IndexRequest request = new IndexRequest("products");
        request.id(productId);
        request.source(productJson, XContentType.JSON);
        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    public void deleteProduct(String id) throws IOException {
        DeleteRequest request = new DeleteRequest("products", id);
        restHighLevelClient.delete(request, RequestOptions.DEFAULT);
    }

    public Map<String, Object> getProduct(String id) throws IOException {
        GetRequest request = new GetRequest("products", id);
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        return response.getSourceAsMap();
    }

    public List<Map<String, Object>> getAllProducts() throws IOException {
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(new MatchAllQueryBuilder());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String, Object>> products = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            products.add(hit.getSourceAsMap());
        }
        return products;
    }
}
