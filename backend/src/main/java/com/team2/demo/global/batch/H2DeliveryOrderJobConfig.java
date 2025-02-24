package com.team2.demo.global.batch;

import com.team2.demo.domain.order.entity.Order;
import com.team2.demo.domain.order.repository.OrderRepository;
import com.team2.demo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor
@Profile("dev")
@Slf4j
public class H2DeliveryOrderJobConfig {

    private final DataSource dataSource;
    private final OrderRepository orderRepository;

    @Bean
    public Job deliveryOrderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("deliveryOrderJob", jobRepository)
                .start(changeDeliveryStatusStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step changeDeliveryStatusStep(JobRepository jobRepository,
                                         PlatformTransactionManager transactionManager) {
        return new StepBuilder("changeDeliveryStatusStep", jobRepository)
                .<Order, Order>chunk(100, transactionManager)
                .reader(orderReader())
                .processor(item->{
                    log.info("item:{}",item);
                    item.updateDeliveryStatus(Order.DeliveryStatus.SHIPPED);
                    return item;
                })
                .writer(orderWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Order> orderReader() {
        JdbcCursorItemReader<Order> itemReader = new JdbcCursorItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setFetchSize(100);
        itemReader.setSql("""
                SELECT *
                FROM caffee.ORDERS
                WHERE MODIFIED_DATE BETWEEN 
                  DATEADD('DAY', -1, PARSEDATETIME(CONCAT(CURDATE(), ' 14:00:00'), 'yyyy-MM-dd HH:mm:ss'))
                  AND PARSEDATETIME(CONCAT(CURDATE(), ' 14:00:00'), 'yyyy-MM-dd HH:mm:ss')
                AND DELIVERY_STATUS = 'PENDING';
                """);
        itemReader.setRowMapper((rs, rowNum) -> new Order(
                rs.getString("ORDER_UUID"),
                User.builder().id(rs.getString("USER_UUID")).build(),
                new ArrayList<>(),
                LocalDateTime.parse(rs.getString("CREATE_DATE"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDateTime.parse(rs.getString("MODIFIED_DATE"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                rs.getInt("TOTAL_AMOUNT"),
                rs.getString("DELIVERY_ADDRESS"),
                rs.getInt("ZIP_CODE"),
                Order.DeliveryStatus.valueOf(rs.getString("DELIVERY_STATUS")))
                );
        return itemReader;
    }

    @Bean
    public ItemWriter<Order> orderWriter() {
        return new ItemWriter<Order>() {
            @Override
            public void write(Chunk<? extends Order> chunk) throws Exception {
                log.info("chunck:{}",chunk);
                orderRepository.saveAll(chunk.getItems());
            }
        };
    }
}
