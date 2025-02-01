package projects;

// Import the required classes
import io.pinecone.clients.Index;
import io.pinecone.clients.Inference;
import io.pinecone.clients.Pinecone;
import io.pinecone.proto.DescribeIndexStatsResponse;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import org.openapitools.db_control.client.model.DeletionProtection;
import org.openapitools.inference.client.ApiException;
import org.openapitools.inference.client.model.Embedding;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateEmbeddings {
    // API KEY from Pinecode project
    private static final String API_KEY = "pcsk_4P3ceg_5w184Xtqg3eRwNehY4JrABGC5UaQTcqYzmU5ct4nKVGQUS2f9JPQsRMWzLuuwF1";

    public static void main(String[] args) throws ApiException {
        // Initialize a Pinecone client with your API key
        Pinecone pc = new Pinecone.Builder("YOUR_API_KEY").build();
        Inference inference = pc.getInferenceClient();

        // Prepare input sentences to be embedded
        List<DataObject> data = Arrays.asList(
                new DataObject("vec1", "Apple is a popular fruit known for its sweetness and crisp texture."),
                new DataObject("vec2", "The tech company Apple is known for its innovative products like the iPhone."),
                new DataObject("vec3", "Many people enjoy eating apples as a healthy snack."),
                new DataObject("vec4", "Apple Inc. has revolutionized the tech industry with its sleek designs and user-friendly interfaces."),
                new DataObject("vec5", "An apple a day keeps the doctor away, as the saying goes."),
                new DataObject("vec6", "Apple Computer Company was founded on April 1, 1976, by Steve Jobs, Steve Wozniak, and Ronald Wayne as a partnership.")
        );

        List<String> inputs = data.stream()
                .map(DataObject::getText)
                .collect(Collectors.toList());

        // Specify the embedding model and parameters
        String embeddingModel = "multilingual-e5-large";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("input_type", "passage");
        parameters.put("truncate", "END");

        // Convert the text into numerical vectors that Pinecone can index
        List<Embedding> embeddingsList = inference.embed(embeddingModel, parameters, inputs).getData();
        // Search the index for the three most similar vectors
        QueryResponseWithUnsignedIndices queryResponse = index.query(3, convertBigDecimalToFloat(queryVector.get(0).getValues()), null, null, null, "example-namespace", null, true, false);


    }

    private static List<Float> convertBigDecimalToFloat(List<BigDecimal> bigDecimalValues) {
        return bigDecimalValues.stream()
                .map(BigDecimal::floatValue)
                .collect(Collectors.toList());
    }

    private static void createServerlessIndex(Pinecone pc, String indexName) {
        pc.createServerlessIndex(indexName, "cosine", 2, "aws", "us-east-1", DeletionProtection.DISABLED);
    }
}



