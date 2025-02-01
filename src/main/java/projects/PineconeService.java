package projects;

import io.pinecone.clients.Inference;
import io.pinecone.clients.Pinecone;
import org.openapitools.db_control.client.model.DeletionProtection;
import org.openapitools.inference.client.ApiException;
import org.openapitools.inference.client.model.Embedding;

import java.math.BigDecimal;
import java.util.*;

import java.util.List;
import java.util.stream.Collectors;

public class PineconeService {

    private static final String API_KEY = "pcsk_4P3ceg_5w184Xtqg3eRwNehY4JrABGC5UaQTcqYzmU5ct4nKVGQUS2f9JPQsRMWzLuuwF1";

    public static void main(String[] args) throws ApiException, InterruptedException {
        Pinecone pc = new Pinecone.Builder(API_KEY).build();

        // Prepare input sentences to be embedded

        List<DataObject> data = Arrays.asList(
                new DataObject("vec1", "Apple is a popular fruit known for its sweetness and crisp texture."),
                new DataObject("vec1", "Apple is a popular fruit known for its sweetness and crisp texture.")
        );

        List<String> inputs = data.stream()
                .map(DataObject::getText)
                .collect(Collectors.toList());

        List<Embedding> embeddings = generateEmbeddings(pc, inputs);
    }

    private static List<Embedding> generateEmbeddings(Pinecone pc, List<String> inputs) throws ApiException {
        Inference inference = pc.getInferenceClient();

        String embeddingModel = "multilingual-e5-large";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("input_type", "passage");
        parameters.put("truncate", "END");

        return inference.embed(embeddingModel, parameters, inputs).getData();

    }

    private static void createServerlessIndex(io.pinecone.clients.Pinecone pc, String indexName) {
        pc.createServerlessIndex(indexName, "cosine", 2, "aws", "us-east-1", DeletionProtection.DISABLED);
    }

    private static List<Float> convertBigDecimalToFloat(List<BigDecimal> bigDecimalValues) {
        return bigDecimalValues.stream()
                .map(BigDecimal::floatValue)
                .collect(Collectors.toList());
    }
}
