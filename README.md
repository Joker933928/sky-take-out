# sky-take-out



## 用户端-再来一单

![image-20241011110733324](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20241011110733324.png)

```java
/**
 * 再来一单
 * @param id
 */
@Override
public void repetitionId(Long id) {
    // 查询当前用户id
    Long userId = BaseContext.getCurrentId();

    // 根据订单id查询当前订单详情
    List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(id);

    // 将订单详情对象转换为购物车对象
    List<ShoppingCart> shoppingCartList = orderDetails.stream().map(x->{
        ShoppingCart shoppingCart = new ShoppingCart();

        // 将原订单详情里面的菜品信息重新复制到购物车对象中
        BeanUtils.copyProperties(x,shoppingCart,"id");
        shoppingCart.setUserId(userId);
        shoppingCart.setCreateTime(LocalDateTime.now());

        return shoppingCart;
    }).collect(Collectors.toList());
    
    // 将购物车对象批量添加到数据库
    shoppingCartMapper.insertBatch(shoppingCartList);
}
```

**将订单详情对象转换为购物车对象**：

```java
List<ShoppingCart> shoppingCartList = orderDetails.stream().map(x->{
        ShoppingCart shoppingCart = new ShoppingCart();

        // 将原订单详情里面的菜品信息重新复制到购物车对象中
        BeanUtils.copyProperties(x,shoppingCart,"id");
        shoppingCart.setUserId(userId);
        shoppingCart.setCreateTime(LocalDateTime.now());

        return shoppingCart;
    }).collect(Collectors.toList());
```

这一部分是核心，使用了 Java 的 `Stream API`。整个 `stream` 的流程如下：

- **`orderDetails.stream()`**：将 `orderDetails` 转换为一个流（stream），开始对每个订单详情进行处理。
- **`map(x -> {...})`**：`map` 是 `Stream API` 中的一个操作，它将每一个 `OrderDetail` 对象 `x` 映射到一个新的 `ShoppingCart` 对象。这个步骤就是所谓的“对象转对象”。
  - 在 map操作内部：
    - **创建购物车对象**：为每个订单详情对象创建一个新的 `ShoppingCart` 对象。
    - **`BeanUtils.copyProperties(x, shoppingCart, "id")`**：使用 `BeanUtils.copyProperties` 将订单详情中的属性值复制到购物车对象中，除了 `id` 字段（通过第三个参数排除掉）。这是“对象转对象”的核心，它将两个对象的相同字段进行映射和复制。
    - **设置用户 ID 和创建时间**：由于购物车需要关联到当前用户，所以我们显式设置了购物车的 `userId` 和 `createTime`。
  - `map` 操作将返回一个新的 `ShoppingCart` 对象。
- **`collect(Collectors.toList())`**：`collect` 将 `map` 操作生成的 `ShoppingCart` 对象收集到一个 `List` 中，最终形成 `shoppingCartList`。



### 为什么使用 `Stream` 和 `map`

- **简洁性**：`Stream API` 提供了一种简洁且函数式编程的方式来处理集合中的每个元素。通过 `map`，我们可以直接将订单详情对象映射到购物车对象，而无需写显式的 `for` 循环。
- **可读性**：通过 `stream().map()`，代码逻辑清晰，直接表达了“将 `OrderDetail` 转换为 `ShoppingCart`”的意图。相比传统的循环方式，这种写法更加清晰易读。



### 总结

- **`stream()`** 将订单详情列表转为流式处理。
- **`map()`** 将每个 `OrderDetail` 转换为一个 `ShoppingCart` 对象，执行对象到对象的映射操作。
- **`collect(Collectors.toList())`** 收集处理后的结果为 `List<ShoppingCart>`。