package com.zhang.musicrs.service.impl;

import com.zhang.musicrs.dao.*;
import com.zhang.musicrs.entity.*;
import com.zhang.musicrs.service.IUserService;
import com.zhang.musicrs.util.RandomUtil;
import com.zhang.musicrs.util.encrypUtil.PBKDF2;
import com.zhang.musicrs.util.redisUtil.RedisUtils;
import com.zhang.musicrs.util.result.Result;
import com.zhang.musicrs.util.result.ResultEnum;
import com.zhang.musicrs.util.result.ResultUtil;
import com.zhang.musicrs.util.timeUtil.MyTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RecordMapper recordMapper;

    @Resource
    private TopSongsMapper topSongsMapper;

    @Resource
    private SongMapper songMapper;

    @Resource
    private TopUsersMapper topUsersMapper;

    @Resource
    private RedisUtils redisUtils;

    // @Override
    // public Result userLogin(User user) {
    //     if (user != null) {
    //         User res = userMapper.queryById(user.getUid());
    //         // 判空并比较密码，这里密码实际上需要加密，类似使用PBKDF2WithHmacSHA1算法
    //         if (res != null && res.getPassword().equals(PBKDF2.getPBKDF2(user.getPassword()))){
    //             // 将密码隐藏，注册时间格式化
    //             return ResultUtil.success(res.setPassword("*********").setRegisterTime(MyTimeUtil.timeFormat(res.getRegisterTime())));
    //         } else {
    //             return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), user.getName() + "用户不存在");
    //         }
    //     }
    //     return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "用户不存在");
    // }

    // 使用了拦截器之后的登录处理
    @Override
    public Result userLogin(UserDO user) {
        // 判读那非空
        if (user != null) {
            UserDO res = userMapper.queryById(user.getUid());
            // 判空并比较密码，这里密码实际上需要加密，类似使用PBKDF2WithHmacSHA1算法
            // if (res != null && res.getPassword().equals(PBKDF2.getPBKDF2(user.getPassword()))) {
            if (res != null && res.getPassword().equals(user.getPassword())) {
                // 4.登录成功，那么就生成的令牌和登录信息存到redis，并返回给前台，前台进行存储（下次来访问的时候就就携带令牌，判断是否已经登录）
                // 使用uuid生成token，避免重复
                String token = UUID.randomUUID().toString();

                // 存到redis，并设置30分钟过期时间
                // redisUtils.set(token, JSON.toJSONString(user), 60 * 30);
                // 修改返回给前端的user信息,将密码隐藏，注册时间格式化
                res.setPassword("*********").setRegisterTime(MyTimeUtil.timeFormat(res.getRegisterTime()));
                // 封装返回信息
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("uToken", token);
                responseData.put("user", res);

                // 5、将随机数token和user都返回给前台
                return ResultUtil.success(responseData);
            } else {
                return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), user.getUid() + "用户不存在或密码错误");
            }
        }
        return ResultUtil.fail(ResultEnum.DATA_NOT_EXIST.getCode(), "用户不存在");
    }

    @Override
    public Result addUser(UserDO user) {
        if (user != null) {
            // 得到要给10位的随机数字串
            user.setUid(RandomUtil.getNBitRandomDigit(10));
            UserDO res = userMapper.queryById(user.getUid());
            if (res != null) {
                return ResultUtil.fail(410, "该用户已存在");
            }
            // 判断是否加密成功，为空则加密失败
            String cypherText = PBKDF2.getPBKDF2(user.getPassword());
            if ("".equals(cypherText)) {
                user.setPassword(cypherText);
            }
            // 这里的密码实际应当加密之后再添加到数据库中
            // 设置注册时间
            userMapper.addUser(user.setRegisterTime(System.currentTimeMillis() + ""));
            // 密码隐藏
            return ResultUtil.success(user.setPassword("********"));
        }
        return ResultUtil.fail(420, "注册失败，请稍后重试！");
    }

    @Override
    public Result updateUser(UserDO user) {
        // 判空
        if (user != null) {
            UserDO res = userMapper.queryById(user.getUid());
            if (res == null) {
                return ResultUtil.fail(430, "该用户不存在，更新失败，请稍后重试！");
            }
            userMapper.updateUser(user);
            // 更新成功则返回
            return ResultUtil.success(user.setPassword("********").setRegisterTime(MyTimeUtil.timeFormat(res.getRegisterTime())));
        } else {
            return ResultUtil.fail(430, "更新失败，请稍后再试！");
        }
    }

    @Override
    public Result updatePw(String uid, String oldPw, String newPw) {
        // 判空
        if (uid != null) {
            UserDO res = userMapper.queryById(uid);
            // 判断改用户是否存在
            if (res == null) {
                return ResultUtil.fail(420, "该用户不存在，更新失败，请稍后再试！");
            }
            // 判断密码原密码是否正确
            if (res.getPassword().equals(oldPw)) {
                userMapper.updatePs(uid, newPw);
                return ResultUtil.success();
            } else {
                return ResultUtil.fail(420, "原密码错误，修改失败！");
            }
        } else {
            return ResultUtil.fail(430, "更新失败，请稍后再试！");
        }
    }

    @Override
    public Result getUserRecord(String uid) {
        return ResultUtil.success(recordMapper.queryByUid(uid));
    }

    @Override
    public Result getRecommendSongs(String uid) {
        // 获得用户播放记录
        List<RecordDO> records = recordMapper.queryByUid(uid);
        if (records == null) {
            return ResultUtil.fail(500, "获取数据失败！");
        }
        // 根据播放记录的权重从大到小排序
        Collections.sort(records, new Comparator<RecordDO>() {
            @Override
            public int compare(RecordDO o1, RecordDO o2) {
                return Integer.parseInt(o2.getWeight()) - Integer.parseInt(o1.getWeight());
            }
        });
        // 相似歌曲列表
        List<SongDO> songList = new ArrayList<>();
        // 获得相似音乐
        TopSongsDO topSongs = topSongsMapper.queryByIid(records.get(0).getIid());
        if (topSongs == null) {
            return ResultUtil.fail(500, "获取相似音乐失败！");
        }
        // 相似歌曲id集合
        List<String> iidList = Arrays.asList(topSongs.getTopSongs().trim().split(","));
        for (String tempId : iidList) {
            SongDO song = songMapper.queryByIid(tempId);
            // 设置音乐时长
            song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
            // 设置音乐播放链接
            // song.setDownUrl(SongDetail.getSongMP3Url(song.getIid()));
            songList.add(song);
        }
        // 返回用户的推荐歌曲
        return ResultUtil.success(songList);
    }

    @Override
    public Result getRecommendUsers(String uid) {
        // 相似用户数组
        List<UserDO> userList = new ArrayList<>();
        // 获得相似音乐好友
        TopUsersDO topUsers = topUsersMapper.queryByUid(uid);
        if (topUsers == null) {
            return ResultUtil.fail(500, "获取相似好友失败！");
        }
        // 获得相似用户id
        List<String> uidList = Arrays.asList(topUsers.getTopUsers().trim().split(","));
        for (String s : uidList) {
            UserDO res = userMapper.queryById(s);
            if (res != null) {
                // 设置密码和注册时间
                userList.add(res.setPassword("********").setRegisterTime(MyTimeUtil.timeFormat(res.getRegisterTime())));
            }
        }
        // 返回相似用户数据
        return ResultUtil.success(userList);
    }
}
