package com.es.phoneshop.web.pagedata;

import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class PaginationData {

    private int pagesCount;
    private int currentPage;
    private List<Phone> phones;

    @Value("#{propertySource['numberOfPaginatingPages']}")
    private int numberOfPaginatingPages;

    public PaginationData() {
    }

    public PaginationData(int pagesCount, int currentPage, List<Phone> phones) {
        this.pagesCount = pagesCount;
        this.currentPage = currentPage;
        this.phones = phones;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public int getNumberOfPaginatingPages() {
        return numberOfPaginatingPages;
    }

    public void setNumberOfPaginatingPages(int numberOfPaginatingPages) {
        this.numberOfPaginatingPages = numberOfPaginatingPages;
    }
}
