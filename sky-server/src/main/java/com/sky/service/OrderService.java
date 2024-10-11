package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);


    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 取消订单
     *
     * @param ordersdto
     */
    void cancel(OrdersCancelDTO ordersdto);

    /**
     * 各个状态的订单数量统计
     *
     * @return
     */
    OrderStatisticsVO statistics();

    /**
     * 完成订单
     *
     * @param id
     */
    void complete(Long id);

    /**
     *
     * 拒单
     * @param ordersRejectionDTO
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    /**
     * 接单
     * @param ordersConfirmDTO
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    OrderVO details(Long id);

    /**
     * 派送订单
     * @param id
     */
    void delivery(Long id);

    /**
     * 条件搜索订单
     * @param pageQueryDTO
     * @return
     */
    PageResult conditionSearch(OrdersPageQueryDTO pageQueryDTO);

    /**
     * 查询历史订单
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    PageResult historyOrders(int pageNum, int pageSize, Integer status);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    OrderVO orderDetailId(Long id);

    /**
     * 根据ID取消订单
     * @param id
     */
    void userCancelById(Long id);

    /**
     * 再来一单
     * @param id
     */
    void repetitionId(Long id);

    /**
     * 客户催单
     * @param id
     */
    void reminderId(Long id);
}
