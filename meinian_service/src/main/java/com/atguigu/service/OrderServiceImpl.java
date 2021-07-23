package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.atguigu.constant.MessageConstant.ADD_MEMBER_SUCCESS;
import static com.atguigu.constant.MessageConstant.HAS_ORDERED;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrdersettingDao ordersettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约

    //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约

    //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约

    //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约

    //5、预约成功，更新当日的已预约人数

    @Override
    public Result order(Map map) {

        try {
            //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约

            // 查询当天是否有旅游团，是否开团
            // 获取旅游日期 (Date orderDate;//预约设置日期)
            String orderDate = (String) map.get("orderDate");
            // 利用工具类把字符串类型转换成日期类型 [ parseString2Date() String -> Date ]
            Date date = DateUtils.parseString2Date(orderDate);

            //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约

            // 根据日期查询当天是否开团(这里会获取到开团的所有信息---去bean查看能获取的对象属性)
            // 这里需要新建一个SQL查询
            OrderSetting orderSetting = ordersettingDao.findByOrderDate(date);
            // 判断
            if (orderSetting == null) {
                // 当天没有旅游团，如果没有团，就不能进行下单
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            } else {
                // 说明当前有旅游团
                // 检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
                // 获取旅游团的大小(int number;//可预约人数)
                int number = orderSetting.getNumber();
                // 获取有多少人已经预约(int reservations ;//已预约人数)
                int reservations = orderSetting.getReservations();
                // 判断可预约的人数是否比团的大小要大
                if (reservations >= number) {
                    // 说明这个旅游团已经约满
                    return new Result(false,MessageConstant.ORDER_FULL);
                }
            }
            //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约

            // 获取用户输入的手机号码
            String telephone = (String) map.get("telephone");
            // 根据手机号码， 查询用户是否已经注册 (Member旅行团会员实体类)
            // 如果是会员，防止重复预约（一个会员、一个时间、一个套餐不能重复，否则是重复预约）
            // 这里需要新建一个SQL查询
            Member member = memberDao.findByTelephone(telephone);
            // 判断当前用户对象是否为null (如果没有注册这个会员类为空，不需要get.telephone() )
            if (member != null) {
                // 说明用户已经注册，已经注册可以直接下单
                // 检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约

                // 获取会员id private Integer memberId;//会员id
                Integer memberId = member.getId();
                //  获取套餐id private Integer setmealId;//旅行套餐id
                // 套餐ID 在map 集合里面  parseint --- 将字符串转换成整数
                Integer setmealId = Integer.parseInt((String)map.get("setmealId"));

                // 创建订单对象
                // 获取订单类型 private String orderType;//预约类型 电话预约/微信预约
                String orderType = (String) map.get("orderType");
                // 创建订单 一个会员ID、一个时间、一个套餐ID
                Order order = new Order(memberId,date,orderType,Order.ORDERSTATUS_NO,setmealId);
                // 查询订单，判断用户是否已经下单 --- 通过旅行团订单(预约)信息 （order对象）
                // 这里需要新建一个SQL查询
                List<Order> lists =  orderDao.findByCondition(order);
                // 判断订单是否有值
                // 判断是否已经预约list不等于null，说明已经预约，不能重复预约
                    if (lists != null && lists.size()>0) {
                        // 说明用户已经下单，如果已经下单，不能重复下单
                        return new Result(false,HAS_ORDERED);
                    }

            } else {
                //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约

                // 说明用户没有注册，没有注册，不能下单，必须进行注册，才能下单
                // 如果不是会员则自动完成注册并进行预约 (Member.java)
                member = new Member();
                member.setName((String) map.get("name"));
                member.setSex((String) map.get("sex"));
                member.setIdCard((String) map.get("idCard"));
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                // 这里需要新建一个SQL 添加信息
                memberDao.add(member);
                // 提示添加新用户成功
                return new Result(true,ADD_MEMBER_SUCCESS);
            }


            //5、预约成功，更新当日的已预约人数 预约人数 +1
            // 获取当前的预约人数 private int reservations ;//已预约人数
            orderSetting.setReservations(orderSetting.getReservations() + 1);
            // 这里需要新建一个SQL 更新 --- 根据 参团日期 OrderSetting --- 参团订单预约设置
            ordersettingDao.edittReservationsByOrderData(orderSetting);

            // 实现下订单 --- 重要 Order --- 旅行团订单(预约)信息
            // 这里注意 Order 里面的值是从哪里取的
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(date);
            order.setOrderType((String) map.get("orderType"));

            // 设置当前的预约状态
            order.setOrderStatus(Order.ORDERSTATUS_NO);
            // 设置旅行套餐id
            order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));

//            private Integer memberId;//会员id
//            private Date orderDate;//预约日期
//            private String orderType;//预约类型 电话预约/微信预约
//            private String orderStatus;//预约状态（是否出游）
//            private Integer setmealId;//旅行套餐id

            // 保存预约信息到预约表
            // 这里需要新建一个SQL语句
            orderDao.add(order);
            // 返回下单成功 信息
            return new Result(true,MessageConstant.ORDER_SUCCESS,order);

        } catch (Exception e) {
            e.printStackTrace();
            // 失败不要把 订单信息 order 传回到前端 传 false结果就可以
            return new Result(false,MessageConstant.ORDER_FAIL);
        }
    }

    // 根据预约id查询预约相关信息
    @Override
    public Map findById(Integer id) {
        Map map = orderDao.findById(id);
        return map;
    }

    /**
     <p>会员姓名：{{orderInfo.member}}</p>
     <p>旅游套餐：{{orderInfo.setmeal}}</p>
     <p>旅游日期：{{orderInfo.orderDate}}</p>
     <p>预约类型：{{orderInfo.orderType}}</p>
     */
}
