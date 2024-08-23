package com.emazon.stock_service.configuration;

import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.adapter.BrandJpaAdapter;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper.IBrandEntityMapper;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.IBrandRepository;
import com.emazon.stock_service.domain.api.IBrandServicePort;
import com.emazon.stock_service.domain.api.ICategoryServicePort;
import com.emazon.stock_service.domain.spi.IBrandPersistencePort;
import com.emazon.stock_service.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_service.domain.useCase.BrandUseCase;
import com.emazon.stock_service.domain.useCase.CategoryUseCase;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.mapper.ICategoryEntityMapper;
import com.emazon.stock_service.adapters.driven.jpa.mysql.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final  ICategoryPersistencePort categoryPersistencePort;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IBrandPersistencePort brandPersistencePort;
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {

        return new CategoryUseCase(categoryPersistencePort);
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort() {
        return new BrandUseCase(brandPersistencePort);
    }
}
