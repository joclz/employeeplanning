package com.cegeka.employeeplanning.data.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public class FindAllBuilder<E, R extends PagingAndSortingRepository<E, ?> & JpaSpecificationExecutor<E>>
{
    private final R repository;
    private Specification<E> filters;
    private final Sort sort = Sort.unsorted();

    public static <E, R extends PagingAndSortingRepository<E, ?> & JpaSpecificationExecutor<E>> FindAllBuilder<E, R> usingRepository(R repository)
    {
        return new FindAllBuilder<>(repository);
    }

    private FindAllBuilder(R repository)
    {
        this.repository = repository;
    }

    public List<E> findAll(int page, int limit, Sort sort)
    {
        return new LinkedList<E>(repository.findAll(filters, PageRequest.of(page, limit, sort)).getContent());
    }

    public int count()
    {
        return repository.findAll(filters).size();
    }

    public FindAllBuilder<E, R> filterBy(List<String> listFilters)
    {
        Optional<Specification<E>> opFilters = EntitySpecificationBuilder.parse(listFilters);
        if (opFilters.isPresent())
        {
            if (filters == null)
            {
                filters = Specification.where(opFilters.get());
            }
            else
            {
                filters = filters.and(opFilters.get());
            }
        }

        return this;
    }
}
