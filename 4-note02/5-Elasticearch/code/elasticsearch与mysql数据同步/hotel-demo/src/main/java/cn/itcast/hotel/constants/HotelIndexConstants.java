package cn.itcast.hotel.constants;

public class HotelIndexConstants {

	// 创建索引库语句
//    public static final String MAPPING_TEMPLATE = "{\n" +
//            "  \"mappings\": {\n" +
//            "    \"properties\": {\n" +
//            "      \"id\": {\n" +
//            "        \"type\": \"keyword\"\n" +
//            "      },\n" +
//            "      \"name\": {\n" +
//            "        \"type\": \"text\",\n" +
//            "        \"analyzer\": \"ik_max_word\",\n" +
//            "        \"copy_to\": \"all\"\n" +
//            "      },\n" +
//            "      \"address\": {\n" +
//            "        \"type\": \"keyword\",\n" +
//            "        \"index\": false\n" +
//            "      },\n" +
//            "      \"price\": {\n" +
//            "        \"type\": \"integer\"\n" +
//            "      },\n" +
//            "      \"score\": {\n" +
//            "        \"type\": \"integer\"\n" +
//            "      },\n" +
//            "      \"brand\": {\n" +
//            "        \"type\": \"keyword\",\n" +
//            "        \"copy_to\": \"all\"\n" +
//            "      },\n" +
//            "      \"city\": {\n" +
//            "        \"type\": \"keyword\"\n" +
//            "      },\n" +
//            "      \"starName\": {\n" +
//            "        \"type\": \"keyword\"\n" +
//            "      },\n" +
//            "      \"business\": {\n" +
//            "        \"type\": \"keyword\",\n" +
//            "        \"copy_to\": \"all\"\n" +
//            "      },\n" +
//            "      \"pic\": {\n" +
//            "        \"type\": \"keyword\",\n" +
//            "        \"index\": false\n" +
//            "      },\n" +
//            "      \"location\": {\n" +
//            "        \"type\": \"geo_point\"\n" +
//            "      },\n" +
//            "      \"all\": {\n" +
//            "        \"type\": \"text\",\n" +
//            "        \"analyzer\": \"ik_max_word\"\n" +
//            "      }\n" +
//            "    }\n" +
//            "  }\n" +
//            "}";


	// 增加自动补全
	public static final String MAPPING_TEMPLATE = "\n" +
			"PUT /hotel\n" +
			"{\n" +
			"   \n" +
			"  \"settings\": {\n" +
			"    \"analysis\": {\n" +
			"      \"analyzer\": {\n" +
			"        \"text_anlyzer\": {\n" +
			"          \"tokenizer\": \"ik_max_word\",\n" +
			"          \"filter\": \"py\"\n" +
			"        },\n" +
			"        \"completion_analyzer\": {\n" +
			"          \"tokenizer\": \"keyword\",  \n" +
			"          \"filter\": \"py\"           \n" +
			"        }\n" +
			"      },\n" +
			"      \"filter\": {\n" +
			"        \"py\": {\n" +
			"          \"type\": \"pinyin\",\n" +
			"          \"keep_full_pinyin\": false,\n" +
			"          \"keep_joined_full_pinyin\": true,\n" +
			"          \"keep_original\": true,\n" +
			"          \"limit_first_letter_length\": 16,\n" +
			"          \"remove_duplicated_term\": true,\n" +
			"          \"none_chinese_pinyin_tokenize\": false\n" +
			"        }\n" +
			"      }\n" +
			"    }\n" +
			"  },\n" +
			"  \"mappings\": {\n" +
			"    \"properties\": {\n" +
			"      \"id\":{\n" +
			"        \"type\": \"keyword\"\n" +
			"      },\n" +
			"      \"name\":{\n" +
			"        \"type\": \"text\",\n" +
			"        \"analyzer\": \"text_anlyzer\",   \n" +
			"        \"search_analyzer\": \"ik_smart\",\n" +
			"        \"copy_to\": \"all\"\n" +
			"      },\n" +
			"      \"address\":{\n" +
			"        \"type\": \"keyword\",\n" +
			"        \"index\": false\n" +
			"      },\n" +
			"      \"price\":{\n" +
			"        \"type\": \"integer\"\n" +
			"      },\n" +
			"      \"score\":{\n" +
			"        \"type\": \"integer\"\n" +
			"      },\n" +
			"      \"brand\":{\n" +
			"        \"type\": \"keyword\",\n" +
			"        \"copy_to\": \"all\"\n" +
			"      },\n" +
			"      \"city\":{\n" +
			"        \"type\": \"keyword\"\n" +
			"      },\n" +
			"      \"starName\":{\n" +
			"        \"type\": \"keyword\"\n" +
			"      },\n" +
			"      \"business\":{\n" +
			"        \"type\": \"keyword\",\n" +
			"        \"copy_to\": \"all\"\n" +
			"      },\n" +
			"      \"location\":{\n" +
			"        \"type\": \"geo_point\"\n" +
			"      },\n" +
			"      \"pic\":{\n" +
			"        \"type\": \"keyword\",\n" +
			"        \"index\": false\n" +
			"      },\n" +
			"      \"all\":{\n" +
			"        \"type\": \"text\",\n" +
			"        \"analyzer\": \"text_anlyzer\",\n" +
			"        \"search_analyzer\": \"ik_smart\"\n" +
			"      },\n" +
			"      \"suggestion\":{\n" +
			"          \"type\": \"completion\",   \n" +
			"          \"analyzer\": \"completion_analyzer\"\n" +
			"      }\n" +
			"    }\n" +
			"  }\n" +
			"}";
}
