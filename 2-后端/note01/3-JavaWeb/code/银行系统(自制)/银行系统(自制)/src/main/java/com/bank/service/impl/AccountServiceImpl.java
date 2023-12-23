package com.bank.service.impl;

import com.bank.dao.AccountDao;
import com.bank.dto.AccountDto;
import com.bank.model.Account;
import com.bank.model.Admin;
import com.bank.service.AccountService;
import java.sql.SQLException;


/**
 * @description: 账户服务层(业务)
 * @date
 */
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao = new AccountDao();

    /**
     * 用户登录验证
     *
     * @param username 账号
     * @param password  密码
     * @return Account || null
     */
    public AccountDto userLogin(String username, String password) throws SQLException {
        //1、通过username 查询数据库
        // 2、查询结果为空，username 错误
        // 3、查询结果不为空，再判断password是否正确
        Account account = this.accountDao.findUserName(username);
        AccountDto accountDto = new AccountDto();

        if (account == null) {
            //账号不正确
            accountDto.setCode(-1);
        } else {
            if (!account.getPassword().equals(password)) {
                //密码不正确
                accountDto.setCode(-2);
            } else {
                    accountDto.setCode(0);
                    accountDto.setAccount(account);
            }
        }
        return accountDto;
    }


    @Override
    public AccountDto adminLogin(String username, String password) throws SQLException {
        //1、通过username 查询数据库
        // 2、查询结果为空，username 错误
        // 3、查询结果不为空，再判断password是否正确
        Admin account = this.accountDao.findAdminName(username);
        AccountDto accountDto = new AccountDto();

        if (account == null) {
            //账号不正确
            accountDto.setCode(-1);
        } else {
            if (!account.getPassword().equals(password)) {
                //密码不正确
                accountDto.setCode(-2);
            } else {
                accountDto.setCode(1);
                accountDto.setAccount(account);
            }
        }
        return accountDto;
    }

    @Override
    public Integer register(String username, String password) throws SQLException {
        return accountDao.userRegister(username, password);
    }
//
//    /**
//     * 查找账户是否存在
//     *
//     * @param account 账户
//     * @return boolean
//     */
//    public boolean hasAccount(String account) {
//        try {
//            return ad.hasAccount(account);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//
//    }
//
//    /**
//     * 进行业务的操作 取款 存款
//     *
//     * @param accountID 业务操作的账号
//     * @param password  密码
//     * @param trade     业务为类型对象
//     * @return boolean
//     */
//    public boolean depositAndWithd(String accountID, String password, Trade trade) {
//        if (trade == null) return false;
//        Conn conn = new Conn();
//        Connection cn = null;
//        //用户标记最终操作是否成功
//        boolean flag = false;
//        try {
//            //连接数据库
//            cn = conn.getConn();
//            //开启事务
//            cn.setAutoCommit(false);
//            //判断账号是否正确
//            Account account = ad.login(accountID, password);
//            if (account == null) {
//                cn.rollback();
//                Conn.ClossAll(cn, null, null);
//                return false;
//            }
//
//            //获取用户余额
//            double money = ad.getMoney(accountID);
//
//            //用于标记操作是否成功
//            boolean accountFlag = false;
//            boolean tradeFlag = false;
//
//            //交易类型不能超出指定范围 1 存款 2 取款 3转账
//            if (trade.getTradeType() < 1 || trade.getTradeType() > 2) {
//                cn.rollback();
//                Conn.ClossAll(cn, null, null);
//                return false;
//            }
//
//
//            //存款 存款金额必须大于0
//            if (trade.getTradeType() == 1 && trade.getTradeMoney() > 0) {
//                //增加金额
//                accountFlag = ad.addMoney(cn, accountID, trade.getTradeMoney());
//            } else if (trade.getTradeType() == 2 && money >= trade.getTradeMoney() && trade.getTradeMoney() > 0) {   //取款  余额必须大于交易金额 取款金额必须大于0
//                //减少金额
//                accountFlag = ad.subMoney(cn, accountID, trade.getTradeMoney());
//            }
//
//            if (accountFlag) {
//                //摘要模板
//                String transferTemplate;
//                DecimalFormat df = new DecimalFormat("#.00");
//                if (trade.getTradeType() == 1)
//                    transferTemplate = "你成功存款【" + trade.getTradeMoney() + "】元，当前可用余额为【" + df.format(money + trade.getTradeMoney()) + "】元";
//                else
//                    transferTemplate = "你成功取款【" + trade.getTradeMoney() + "】元，当前可用余额为【" + df.format(money - trade.getTradeMoney()) + "】元";
//                trade.setTradeDigest(transferTemplate);
//                tradeFlag = new TradeService().addTrade(cn, trade);
//            }
//
//            //操作是否成功
//            flag = accountFlag && tradeFlag;
//            //如果操作都成功得话就提交事务否则回滚
//            if (flag) {
//                //提交
//                cn.commit();
//            } else {
//                //回滚
//                cn.rollback();
//            }
//        } catch (SQLException e) {
//            if (cn != null) {
//                try {
//                    //回滚
//                    cn.rollback();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//        }
//        Conn.ClossAll(cn, null, null);
//        return flag;
//    }
//
//    /**
//     * 转账
//     *
//     * @param accountID   转出账户
//     * @param password    密码
//     * @param toAccountId 转入账hu
//     * @param trade       交易信息
//     * @return boolean
//     */
//    public boolean transfer(String accountID, String password, String toAccountId, Trade trade) {
//        if (trade == null) return false;
//        Conn conn = new Conn();
//        Connection cn = null;
//        //用户标记最终操作是否成功
//        boolean flag = false;
//        try {
//            //连接数据库
//            cn = conn.getConn();
//            //开启事务
//            cn.setAutoCommit(false);
//            //判断账号是否正确
//            Account account = ad.login(accountID, password);
//            //判断转入账号是非存在
//            boolean hasAccount = this.hasAccount(toAccountId);
//            if (account == null || !hasAccount) {
//                cn.rollback();
//                Conn.ClossAll(cn, null, null);
//                return false;
//            }
//
//            //获取用户余额
//            double money = ad.getMoney(accountID);
//
//            //用于标记操作是否成功
//            boolean accountFlag = false;
//            boolean tradeFlag = false;
//
//            //交易类型不能超出指定范围
//            if (trade.getTradeType() != 3) {
//                cn.rollback();
//                Conn.ClossAll(cn, null, null);
//                return false;
//            }
//
//            if (money >= trade.getTradeMoney() && trade.getTradeMoney() > 0) { //转账 余额必须大于交易金额 转账金额大于0
//                //转账人减少金额
//                boolean ac1 = ad.subMoney(cn, accountID, trade.getTradeMoney());
//                boolean ac2 = false;
//                if (ac1) {
//                    //转入人增加金额
//                    ac2 = ad.addMoney(cn, toAccountId, trade.getTradeMoney());
//                }
//                accountFlag = ac1 && ac2;
//            }
//            /**
//             * @TODO 可以加一个转入的记录
//             */
//            if (accountFlag) {
//                //获取交易后的余额
//                double m = money - trade.getTradeMoney();
//                DecimalFormat df = new DecimalFormat("#.00");
//                //摘要模板
//                String transferTemplate = "你的账户向【" + toAccountId + "】成功转账【" + trade.getTradeMoney() + "】元；当前可用余额为【" + df.format(m) + "】元";
//                trade.setTradeDigest(transferTemplate);
//                tradeFlag = new TradeService().addTrade(cn, trade);
//            }
//            //操作是否成功
//            flag = accountFlag && tradeFlag;
//            //如果操作都成功得话就提交事务否则回滚
//            if (flag) {
//                //提交
//                cn.commit();
//            } else {
//                //回滚
//                cn.rollback();
//            }
//        } catch (SQLException e) {
//            if (cn != null) {
//                try {
//                    //回滚
//                    cn.rollback();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//        }
//        Conn.ClossAll(cn, null, null);
//        return flag;
//    }
//
//    /**
//     * 获取账户的余额
//     *
//     * @param account 账户
//     * @return double 余额
//     * @throws SQLException
//     */
//    public double getMoney(String account) throws SQLException {
//        return ad.getMoney(account);
//    }
}
