package com.zhang.musicrs;

import com.zhang.musicrs.dao.*;
import com.zhang.musicrs.entity.AdministratorDO;
import com.zhang.musicrs.entity.RecordDO;
import com.zhang.musicrs.entity.SongDO;
import com.zhang.musicrs.service.*;
import com.zhang.musicrs.util.RandomUtil;
import com.zhang.musicrs.util.httpUtil.SongDetail;
import com.zhang.musicrs.util.redisUtil.RedisUtils;
import com.zhang.musicrs.util.timeUtil.MyTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecommendationSystemApplicationTests {
    @Autowired
    private AdminMapper adminMapper;


    @Test
    void contextLoads() {
        System.out.println("测试");
    }

    @Test
    void queryByNameTest() {
        System.out.println(adminMapper.queryByName("admin1"));
    }


    @Autowired
    private SongMapper songMapper;

    @Test
    void queryByIidTest() {
        System.out.println(songMapper.queryByIid("448317748"));
    }

    @Test
    void querySongsLimitTest() {
        for (SongDO song : songMapper.querySongsHot(0, 20)) {
            System.out.println(song);
        }
    }

    @Test
    void queryAllTest() {
        for (SongDO song : songMapper.queryAll()) {
            System.out.println(song);
        }
    }

    @Test
    void queryByNameOfSongMapperTest() {
        for (SongDO song : songMapper.queryByKeyword("嘿，抬头！")) {
            System.out.println(song);
        }
    }


    @Autowired
    private RecordMapper recordMapper;

    @Test
    void queryByUidAndIidTest() {
        System.out.println(recordMapper.queryByUidAndIid("100198046", "1333745434"));
    }

    @Test
    void queryByUidOfRecordTest() {
        for (RecordDO record : recordMapper.queryByUid("100198046")) {
            System.out.println(record);
        }
    }

    @Autowired
    private TopSongsMapper topSongsMapper;

    @Test
    void queryByIidOfTopSongsTest() {
        System.out.println(topSongsMapper.queryByIid("1003959"));
    }

    @Autowired
    private TopUsersMapper topUsersMapper;

    @Test
    void queryByUidOfTopUserTest() {
        System.out.println(topUsersMapper.queryByUid("100198046"));
    }

    @Autowired
    private IAdminService iAdminService;

    @Test
    void adminLoginTest() {
        AdministratorDO admin = new AdministratorDO();
        admin.setName("admin1");
        admin.setPassword("123");
        System.out.println(iAdminService.adminLogin(admin));
    }

    @Autowired
    private IUserService iUserService;

    @Test
    void getUserRecommendSongsTest(){
        System.out.println(iUserService.getRecommendSongs("100198046"));
    }

    @Test
    void getUserRecommendUsersTest(){
        System.out.println(iUserService.getRecommendUsers("100198046"));
    }
    @Autowired
    private ITopUserService iTopUserService;

    @Test
    void querySimilarUsersTest() {
        System.out.println(iTopUserService.querySimilarUsers("100198046"));
    }

    @Autowired
    private ITopSongsService iTopSongsService;

    @Test
    void querySimilarSongsTest() {
        System.out.println(iTopSongsService.querySimilarSongs("1003959"));
    }

    @Autowired
    private ISongService iSongService;

    @Test
    void queryByIidOfServiceTest() {
        System.out.println(iSongService.queryByIid("133998"));
    }

    @Test
    void queryAllOfServiceTest() {
        System.out.println(iSongService.queryAll());
    }

    @Test
    void getSongMP3UrlTest() {
        System.out.println("测试获得歌曲下载链接：" + SongDetail.getSongMP3Url("133998"));
    }

    // 测试工具类
    @Test
    void millSeconds2timeTest() {
        System.out.println(MyTimeUtil.millSeconds2time("209789"));
    }

    @Test
    void getNBitRandomDigitTest() {
        System.out.println(RandomUtil.getNBitRandomDigit(10));
    }

    // 测试Redis工具类
    @Autowired
    private RedisUtils redisUtils;
    @Test
    public void setRedisUtils() {
        redisUtils.set("redis_key", "redis_vale");
    }

    @Test
    public void getRedisUtils() {
        String value = redisUtils.get("redis_key");
        System.out.println(value);
    }
}
