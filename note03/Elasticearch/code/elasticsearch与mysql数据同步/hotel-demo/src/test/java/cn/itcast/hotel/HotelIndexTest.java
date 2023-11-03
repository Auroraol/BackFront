package cn.itcast.hotel;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static cn.itcast.hotel.constants.HotelIndexConstants.MAPPING_TEMPLATE;

/**
 * RestClient操作索引库
 */
@SpringBootTest
class HotelIndexTest {

	private RestHighLevelClient client;

	/**
	 * 创建索引库
	 * PUT/索引库名称
	 * {
	 * ....
	 * }
	 *
	 * @throws IOException
	 */
	@Test
	void testCreateIndex() throws IOException {
		// 1.准备Request      PUT /hotel
		CreateIndexRequest request = new CreateIndexRequest("hotel");
		// 2.准备请求参数
		request.source(MAPPING_TEMPLATE, XContentType.JSON);  //MAPPING_TEMPLATE是自定义的创建索引库语句
		// 3.发送请求
		client.indices().create(request, RequestOptions.DEFAULT);
	}

	/**
	 * 查询索引库
	 * GET /索引库名称
	 *
	 * @throws IOException
	 */
	@Test
	void testExistsIndex() throws IOException {
		// 1.准备Request
		GetIndexRequest request = new GetIndexRequest("hotel");
		// 3.发送请求
		boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);

		System.out.println(isExists ? "存在" : "不存在");
	}
	
	/**
	 * 删除索引库
	 * DELETE /索引名称1,索引名称2,索引名称3...
	 */
	@Test
	void testDeleteIndex() throws IOException {
		// 1.准备Request
		DeleteIndexRequest request = new DeleteIndexRequest("hotel");
		// 3.发送请求
		client.indices().delete(request, RequestOptions.DEFAULT);
	}

	@BeforeEach
	void setUp() {
		client = new RestHighLevelClient(RestClient.builder(
				HttpHost.create("http://192.168.200.134:9200")
		));
	}

	@AfterEach
	void tearDown() throws IOException {
		client.close();
	}


}
