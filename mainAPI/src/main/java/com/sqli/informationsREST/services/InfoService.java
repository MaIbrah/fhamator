package com.sqli.informationsREST.services;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sqli.informationsREST.models.Information;

@Service
public class InfoService {

    public Page<Information> findPaginated(List<Information> informations, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Information> list;

        if (informations.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, informations.size());
            list = informations.subList(startItem, toIndex);
        }

        Page<Information> informationPage
            = new PageImpl(list, PageRequest.of(currentPage, pageSize), informations.size());

        return informationPage;
    }
}
