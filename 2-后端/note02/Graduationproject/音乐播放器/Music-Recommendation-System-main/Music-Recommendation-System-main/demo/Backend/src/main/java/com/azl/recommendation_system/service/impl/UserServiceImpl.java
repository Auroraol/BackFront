package com.azl.recommendation_system.service.impl;

import com.azl.recommendation_system.dao.*;
import com.azl.recommendation_system.pojo.*;
import com.azl.recommendation_system.service.IUserService;
import com.azl.recommendation_system.util.RandomUtil;
import com.azl.recommendation_system.util.httpUtil.SongDetail;
import com.azl.recommendation_system.util.result.Result;
import com.azl.recommendation_system.util.result.ResultUtil;
import com.azl.recommendation_system.util.timeUtil.MyTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private TopSongsMapper topSongsMapper;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private TopUsersMapper topUsersMapper;

    @Override
    public Result userLogin(User user) {
        if (user != null) {
            User res = userMapper.queryById(user.getUid());
            // 判空并比较密码，这里密码实际上需要加密，类似使用MD5
            if (res != null && user.getPassword().equals(res.getPassword())) {
                // 将密码隐藏，注册时间格式化
                return ResultUtil.success(res.setPassword("*********").setRegisterTime(MyTimeUtil.timeFormat(res.getRegisterTime())));
            } else {
                return ResultUtil.fail(400, user.getName() + "用户不存在");
            }
        }
        return ResultUtil.fail(400, "用户不存在");
    }

    @Override
    public Result addUser(User user) {
        if (user != null) {
            // 得到要给10位的随机数字串
            user.setUid(RandomUtil.getNBitRandomDigit(10));
            User res = userMapper.queryById(user.getUid());
            if (res != null) {
                return ResultUtil.fail(410, "该用户已存在");
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
    public Result updateUser(User user) {
        // 判空
        if (user != null) {
            User res = userMapper.queryById(user.getUid());
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
            User res = userMapper.queryById(uid);
            // 判断改用户是否存在
            if (res == null) {
                return ResultUtil.fail(420, "该用户不存在，更新失败，请稍后再试！");
            }
            //判断密码原密码是否正确
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
        List<Record> records = recordMapper.queryByUid(uid);
        if (records == null) {
            return ResultUtil.fail(500, "获取数据失败！");
        }
        // 根据播放记录的权重从大到小排序
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return Integer.parseInt(o2.getWeight()) - Integer.parseInt(o1.getWeight());
            }
        });
        // 相似歌曲列表
        List<Song> songList = new ArrayList<>();
        // 获得相似音乐
        TopSongs topSongs = topSongsMapper.queryByIid(records.get(0).getIid());
        if (topSongs == null) {
            return ResultUtil.fail(500, "获取相似音乐失败！");
        }
        // 相似歌曲id集合
        //List<String> iidList = Arrays.asList(topSongs.getTopSongs().strip().split(","));
        List<String> iidList = Arrays.asList(topSongs.getTopSongs().trim().split(","));

        User user = userMapper.queryById(uid);

        for (String tempId : iidList) {
            Song song = songMapper.queryByIid(tempId);
            if(song.getsongtype().equals(user.getSongtype())) {
                // 设置音乐时长
                song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
                // 设置音乐播放链接
                song.setDownUrl(SongDetail.getSongMP3Url(song.getIid()));
                songList.add(song);
            }
        }
        for (String tempId : iidList) {
            Song song = songMapper.queryByIid(tempId);
            if(!song.getsongtype().equals(user.getSongtype())) {
                // 设置音乐时长
                song.setSongTime(MyTimeUtil.millSeconds2time(song.getSongTime()));
                // 设置音乐播放链接
                song.setDownUrl(SongDetail.getSongMP3Url(song.getIid()));
                songList.add(song);
            }
        }
        // 返回用户的推荐歌曲
        return ResultUtil.success(songList);
    }

    @Override
    public Result getRecommendUsers(String uid) {
        // 相似用户数组
        List<User> userList = new ArrayList<>();
        // 获得相似音乐好友
        TopUsers topUsers = topUsersMapper.queryByUid(uid);
        System.out.println("音乐推荐好友信息：" + topUsers);
        if (topUsers == null) {
            return ResultUtil.fail(500, "获取相似好友失败！");
        }
        // 获得相似用户id
        List<String> uidList = Arrays.asList(topUsers.getTopUsers().trim().split(","));
        System.out.println("音乐推荐好友id列表信息：" + uidList);
        for (String s : uidList) {
            User res = userMapper.queryById(s);
            if (res != null) {
                // 设置密码和注册时间
                userList.add(res.setPassword("********").setRegisterTime(MyTimeUtil.timeFormat(res.getRegisterTime())));
            }
        }
        // 返回相似用户数据
        return ResultUtil.success(userList);
    }

    @Override
    public Result queryUsersNew(int currIndex, int pageSize) {
        // 得到结果
        List<User> userList = userMapper.queryUsersNew(currIndex, pageSize);
        if (userList == null) {
            return ResultUtil.fail(500, "数据不存在！");
        }
        // 返回用户数据
        return ResultUtil.success(userList);
    }

    @Override
    public Result queryByKeyword(String keyWord) {
        if (keyWord == null || "".equals(keyWord)) {
            return ResultUtil.fail(400, "未搜索到数据！");
        }
        // 从数据库查询数据
        List<User> users = userMapper.queryByKeyword("%" + keyWord + "%");
        // 返回数据
        return ResultUtil.success(users);
    }


    @Override
    public Result deleteUserById(String uid) {
        if (uid != null) {
            User res = userMapper.queryById(uid);
            // 用户存在才能删除
            if (res == null) {
                return ResultUtil.fail(430, "该用户不存在，无法删除，请稍后重试！");
            }
            userMapper.deleteUser(uid);
            // 删除成功，返回提示信息
            return ResultUtil.success(uid);
        } else {
            return ResultUtil.fail(420, "用户id为空，删除失败，请稍后再试！");
        }
    }

    @Override
    public Result addVisitor(User user) {
        if (user != null) {
            String s = 0 + "";
            user.setUid(s);
            User res = userMapper.queryById(user.getUid());
            if(res != null){
                return ResultUtil.success(user.setPassword("********"));
            }
            // 设置注册时间
            userMapper.addUser(user.setRegisterTime(System.currentTimeMillis() + ""));
        }
        // 密码隐藏
        return ResultUtil.success(user.setPassword("********"));

    }
}
