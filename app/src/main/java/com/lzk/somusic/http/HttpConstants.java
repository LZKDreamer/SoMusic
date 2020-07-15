package com.lzk.somusic.http;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/1
 * Function:
 */
public class HttpConstants {

    public static final int ERR_CODE = -1;

    public static final String BASE_URL = "https://raw.githubusercontent.com/";

    public static final String RECOMMEND = "LZKDreamer/SoMusic/master/app/mock/recommend.json";

    public static final String RECOMMEND_JSON = "{\n" +
            "  \"errCode\": 0,\n" +
            "  \"errMsg\": \"\",\n" +
            "  \"data\": {\n" +
            "    \"banner\": [\n" +
            "      {\n" +
            "        \"name\": \"爱不老\",\n" +
            "        \"author\": \"罗震环\",\n" +
            "        \"img\": \"http://img2.kuwo.cn/star/albumcover/300/15/35/3531395738.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_86387282&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:56\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"歌词祖国\",\n" +
            "        \"author\": \"杨沛宜\",\n" +
            "        \"img\": \"http://img1.kuwo.cn/star/albumcover/300/74/12/172216196.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_703784&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"04:14\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"我和我的祖国(全民合唱版)\",\n" +
            "        \"author\": \"群星\",\n" +
            "        \"img\": \"http://img2.kuwo.cn/star/albumcover/300/76/18/3462999483.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_77911349&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:03\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"今天是你的生日\",\n" +
            "        \"author\": \"儿童歌曲\",\n" +
            "        \"img\": \"http://img1.kuwo.cn/star/albumcover/300/64/92/1232598615.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_6219314&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"05:03\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"My Love\",\n" +
            "        \"author\": \"Westlife\",\n" +
            "        \"img\": \"http://img4.kuwo.cn/star/albumcover/300/9/52/2714122363.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_26035689&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:53\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"recommend\": [\n" +
            "      {\n" +
            "        \"name\": \"山楂树の恋\",\n" +
            "        \"author\": \"程jiajia\",\n" +
            "        \"img\": \"http://img1.kuwo.cn/star/albumcover/300/81/43/1063260811.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_73433206&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:17\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"根本你不懂得爱我\",\n" +
            "        \"author\": \"童珺\",\n" +
            "        \"img\": \"http://img4.kuwo.cn/star/albumcover/300/13/56/1389855506.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_85153419&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:54\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"慕夏\",\n" +
            "        \"author\": \"等什么君\",\n" +
            "        \"img\": \"http://img2.kuwo.cn/star/albumcover/300/10/80/3868039862.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_73114989&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"02:50\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"芒种\",\n" +
            "        \"author\": \"音阙诗听 / 赵方婧\",\n" +
            "        \"img\": \"http://img2.kuwo.cn/star/albumcover/300/59/39/950986041.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_70136644&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:36\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"你笑起来真好看\",\n" +
            "        \"author\": \"李昕融 / 樊桐舟 / 李凯稠\",\n" +
            "        \"img\": \"http://img4.kuwo.cn/star/albumcover/300/37/92/1895437893.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_68481158&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"02:52\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"红豆(Live)\",\n" +
            "        \"author\": \"林允儿\",\n" +
            "        \"img\": \"http://star.kuwo.cn/star/starheads/300/29/82/3362138331.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_28050604&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"04:42\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"guess\": [\n" +
            "      {\n" +
            "        \"name\": \"骑上我心爱的小摩托\",\n" +
            "        \"author\": \"1908公社\",\n" +
            "        \"img\": \"http://img4.kuwo.cn/star/albumcover/300/53/47/594084408.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_76210016&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:01\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"Only The Young\",\n" +
            "        \"author\": \"Taylor Swift\",\n" +
            "        \"img\": \"http://img3.kuwo.cn/star/albumcover/300/51/58/1475607789.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_89133721&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"02:37\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"TiK ToK\",\n" +
            "        \"author\": \"Kesha\",\n" +
            "        \"img\": \"http://img4.kuwo.cn/star/albumcover/300/71/55/4044447991.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_12513453&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:48\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"你的答案\",\n" +
            "        \"author\": \"阿冗\",\n" +
            "        \"img\": \"http://img1.kuwo.cn/star/albumcover/300/19/94/3268290676.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_80488731&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:39\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"世界这么大还是遇见你\",\n" +
            "        \"author\": \"程响\",\n" +
            "        \"img\": \"http://img2.kuwo.cn/star/albumcover/300/3/49/3996405032.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_85595508&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:57\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"余情未了\",\n" +
            "        \"author\": \"魏新雨\",\n" +
            "        \"img\": \"http://img2.kuwo.cn/star/albumcover/300/26/35/1877109495.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_69284035&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"03:36\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"夜空中最亮的星\",\n" +
            "        \"author\": \"逃跑计划\",\n" +
            "        \"img\": \"http://img2.kuwo.cn/star/albumcover/300/1/56/2415820681.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_1217815&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"04:12\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"最好的朋友在身边\",\n" +
            "        \"author\": \"A-Lin\",\n" +
            "        \"img\": \"http://img2.kuwo.cn/star/albumcover/300/14/6/3601102300.jpg\",\n" +
            "        \"url\": \"http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_51307873&response=res&type=convert_url&\",\n" +
            "        \"duration\": \"02:40\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";
}
