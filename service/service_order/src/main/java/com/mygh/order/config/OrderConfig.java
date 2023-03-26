package com.mygh.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.mygh.order.mapper")
public class OrderConfig {

}
