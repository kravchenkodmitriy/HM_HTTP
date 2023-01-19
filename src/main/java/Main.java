import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.RequestLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static final String REMOTE_SERVICE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        //Добавление объекта запроса
        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        RequestLine requestLine = request.getRequestLine();
        System.out.println(requestLine);

        //Вызов удаленного сервиса
        CloseableHttpResponse response = httpClient.execute(request);

        //преобразование
        List<Transformation> convertedList = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Transformation>>() {
        });

        List<Transformation> filteredUpvotes = convertedList.stream()
                .filter(transformation -> transformation.getUpvotes() != null && transformation.getUpvotes() != 0)
                .collect(Collectors.toList());


        filteredUpvotes.forEach(System.out::println);
//    }
    }
}
