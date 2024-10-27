package com.zosh.treading.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zosh.treading.model.Coin;
import com.zosh.treading.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service

public class CoinServiceImpl implements CoinService {
    @Autowired
    private CoinRepository coinRepository;

    private ObjectMapper  objectMapper;
    @Override
    public List<Coin> getCoinList(int page) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=106page="+page;
        RestTemplate restTemplate= new RestTemplate();
        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

          List<Coin>coinList=objectMapper.readValue(response.getBody(),new TypeReference<List<Coin>>(){});
          return coinList;
        }
        catch (HttpClientErrorException| HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public String getMarksChart(String coinId, int days) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/"+coinId+"/market_chart?vs_currency=usd&days="+days;
        RestTemplate restTemplate= new RestTemplate();
        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();
        }
        catch (HttpClientErrorException| HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public String getCoinDetails(String coinId) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId;

        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Set headers (if needed)
            HttpHeaders headers = new HttpHeaders();

            // You can remove the "parameters" string, as it's not required here
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make the REST call
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            JsonNode jsonNode=objectMapper.readTree(response.getBody());

            Coin coin=new Coin();
            coin.setId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData=jsonNode.get("market_data");
            coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());

            coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
            coin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
            coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            coin.setHigh24h(marketData.get("high_24").get("usd").asDouble());
            coin.setLow24h(marketData.get("low_24").get("usd").asDouble());
            coin.setPriceChange24h(marketData.get("price_change2_4th").get("usd").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_change2_24th").get("usd").asDouble());
            coin.setMarketCapChange24h(marketData.get("market_cap_change24").asLong());
            coin.setPriceChangePercentage24h(marketData.get("market_cap_change_percentage_24").asLong());
            coin.setTotalSupply(marketData.get("total_supply").get("usd").asLong());
            coinRepository.save(coin);

            // Return the body of the response
            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handle client or server errors
            throw new Exception(e.getMessage());

        } catch (RestClientException e) {
            // Handle other rest-related errors, such as network issues
            throw new Exception("Network error: " + e.getMessage());
        }
    }


    @Override
    public Coin findById(String coinId) throws Exception {
        Optional<Coin> optionalCoin = coinRepository.findById(coinId);
        if (optionalCoin.isEmpty()) throw new Exception("coin not found");

            return optionalCoin.get();

    }

    @Override
    public String searchCoin(String keyword) throws Exception {
        String url="https://api.coingecko.com/api/v3/search?query="+keyword;
        RestTemplate restTemplate= new RestTemplate();
        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();
        }
        catch (HttpClientErrorException| HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public String getTop50CoinByMarketCapRank() throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/markets/vs_currency=usd&per_page=50&page=1";
        RestTemplate restTemplate= new RestTemplate();
        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();
        }
        catch (HttpClientErrorException| HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public String getTreadingCoins() throws Exception {
        String url="https://api.coingecko.com/api/v3/search/treading";
        RestTemplate restTemplate= new RestTemplate();
        try{
            HttpHeaders headers=new HttpHeaders();
            HttpEntity<String> entity=new HttpEntity<String>("parameters",headers);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();
        }
        catch (HttpClientErrorException| HttpServerErrorException e) {
            throw new Exception(e.getMessage());
        }

    }
}
