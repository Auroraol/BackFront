package cn.itcast.hotel.constants;

public class HotelMqConstants {
	/**
	 * 交换机
	 */
	public final static String EXCHANGE_NAME = "hotel.topic";
	/**
	 * 监听新增和修改的队列
	 */
	public final static String INSERT_QUEUE_NAME = "hotel.insert.queue";
	/**
	 * 监听删除的队列
	 */
	public final static String DELETE_QUEUE_NAME = "hotel.delete.queue";
	/**
	 * 新增或修改的RoutingKey
	 */
	public final static String INSERT_KEY = "hotel.insert";
	/**
	 * 删除的RoutingKey
	 */
	public final static String DELETE_KEY = "hotel.delete";
}
